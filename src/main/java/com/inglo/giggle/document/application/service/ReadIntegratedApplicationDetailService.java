package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.dto.response.ReadIntegratedApplicationDetailResponseDto;
import com.inglo.giggle.document.application.usecase.ReadIntegratedApplicationDetailUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.repository.mysql.DocumentRepository;
import com.inglo.giggle.document.repository.mysql.IntegratedApplicationRepository;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import jakarta.persistence.DiscriminatorValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadIntegratedApplicationDetailService implements ReadIntegratedApplicationDetailUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final DocumentRepository documentRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;
    private final IntegratedApplicationRepository integratedApplicationRepository;

    @Override
    public ReadIntegratedApplicationDetailResponseDto execute(UUID accountId, Long documentId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

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

        // IntegratedApplication 조회
        IntegratedApplication integratedApplication = integratedApplicationRepository.findWithSchoolById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        return ReadIntegratedApplicationDetailResponseDto.fromEntity(integratedApplication);
    }


}
