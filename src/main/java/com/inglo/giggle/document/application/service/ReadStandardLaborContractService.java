package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.usecase.ReadStandardLaborContractDetailUseCase;
import com.inglo.giggle.document.domain.ContractWorkDayTime;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.persistence.repository.ContractWorkDayTimeRepository;
import com.inglo.giggle.document.persistence.repository.DocumentRepository;
import com.inglo.giggle.document.persistence.repository.StandardLaborContractRepository;
import com.inglo.giggle.document.presentation.dto.response.ReadStandardLaborContractDetailResponseDto;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import jakarta.persistence.DiscriminatorValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadStandardLaborContractService implements ReadStandardLaborContractDetailUseCase {

    private final LoadAccountPort loadAccountPort;
    private final DocumentRepository documentRepository;
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final ContractWorkDayTimeRepository contractWorkDayTimeRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    @Override
    public ReadStandardLaborContractDetailResponseDto execute(UUID accountId, Long documentId) {

        // Account 조회
        Account account = loadAccountPort.loadAccount(accountId);

        // Document 정보 조회
        Document document = documentRepository.findByIdOrElseThrow(documentId);

        // UserOwnerJobPosting 정보 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);

        // 계정 타입에 따라 유효성 체크
        String accountDiscriminatorValue = account.getClass().getAnnotation(DiscriminatorValue.class).value();

        switch (accountDiscriminatorValue) {
            case "USER":

                // 계정 타입 유효성 체크
                account.checkUserValidation();

                // UserOwnerJobPosting 유저 유효성 체크
                userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

                break;

            case "OWNER":

                // 계정 타입 유효성 체크
                account.checkOwnerValidation();

                // UserOwnerJobPosting 고용주 유효성 체크
                userOwnerJobPosting.checkOwnerUserOwnerJobPostingValidation(accountId);

                break;

            default:
                throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }

        // StandardLaborContract 조회 -> null이면 유학생만 작성한 경우 or 아예 없는 경우
        StandardLaborContract standardLaborContract = standardLaborContractRepository.findWithWeeklyRestDaysAndInsurancesByIdOrElseNull(documentId);

        // StandardLaborContract가 null이면 다시 조회. 예외가 발생하면 아예 없는경우, 아니면 유학생만 작성한 경우
        if (standardLaborContract == null) {
            standardLaborContract = standardLaborContractRepository.findByIdOrElseThrow(documentId);
        }

        // ContractWorkDayTime 조회
        List<ContractWorkDayTime> contractWorkDayTimes = contractWorkDayTimeRepository.findByStandardLaborContractId(documentId);

        return ReadStandardLaborContractDetailResponseDto.of(
                standardLaborContract,
                contractWorkDayTimes
        );
    }

}
