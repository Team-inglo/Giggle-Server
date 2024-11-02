package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.dto.response.ReadStandardLaborContractDetailResponseDto;
import com.inglo.giggle.document.application.usecase.ReadStandardLaborContractDetailUseCase;
import com.inglo.giggle.document.domain.ContractWorkDayTime;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.repository.mysql.ContractWorkDayTimeRepository;
import com.inglo.giggle.document.repository.mysql.DocumentRepository;
import com.inglo.giggle.document.repository.mysql.StandardLaborContractRepository;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import jakarta.persistence.DiscriminatorValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadStandardLaborContractService implements ReadStandardLaborContractDetailUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final DocumentRepository documentRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final ContractWorkDayTimeRepository contractWorkDayTimeRepository;

    @Override
    public ReadStandardLaborContractDetailResponseDto execute(UUID accountId, Long documentId) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // Document 정보 조회
        Document document = documentRepository.findWithUserOwnerJobPostingById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 계정 타입에 따라 유효성 체크
        String accountDiscriminatorValue = account.getClass().getAnnotation(DiscriminatorValue.class).value();

        switch (accountDiscriminatorValue) {
            case "USER":

                // 계정 타입 유효성 체크
                accountService.checkUserValidation(account);

                // UserOwnerJobPosting 유저 유효성 체크
                userOwnerJobPostingService.checkUserUserOwnerJobPostingValidation(document.getUserOwnerJobPosting(), accountId);

                break;

            case "OWNER":

                // 계정 타입 유효성 체크
                accountService.checkOwnerValidation(account);

                // UserOwnerJobPosting 고용주 유효성 체크
                userOwnerJobPostingService.checkOwnerUserOwnerJobPostingValidation(document.getUserOwnerJobPosting(), accountId);

                break;

            default:
                throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }

        // StandardLaborContract 조회 -> null이면 유학생만 작성한 경우 or 아예 없는 경우
        StandardLaborContract standardLaborContract = standardLaborContractRepository.findWithWeeklyRestDaysAndInsurancesById(documentId)
                .orElse(null);

        // StandardLaborContract가 null이면 다시 조회. 예외가 발생하면 아예 없는경우, 아니면 유학생만 작성한 경우
        if (standardLaborContract == null) {
            standardLaborContract = standardLaborContractRepository.findById(documentId)
                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
        }

        // ContractWorkDayTime 조회
        List<ContractWorkDayTime> contractWorkDayTimes = contractWorkDayTimeRepository.findByStandardLaborContractId(documentId);

        return ReadStandardLaborContractDetailResponseDto.of(
                standardLaborContract,
                contractWorkDayTimes
        );
    }

}
