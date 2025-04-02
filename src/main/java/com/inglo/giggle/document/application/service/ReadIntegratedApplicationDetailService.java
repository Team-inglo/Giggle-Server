package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.usecase.ReadIntegratedApplicationDetailUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.persistence.repository.DocumentRepository;
import com.inglo.giggle.document.persistence.repository.IntegratedApplicationRepository;
import com.inglo.giggle.document.presentation.dto.response.ReadIntegratedApplicationDetailResponseDto;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import jakarta.persistence.DiscriminatorValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadIntegratedApplicationDetailService implements ReadIntegratedApplicationDetailUseCase {

    private final AccountRepository accountRepository;
    private final DocumentRepository documentRepository;
    private final IntegratedApplicationRepository integratedApplicationRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    @Override
    public ReadIntegratedApplicationDetailResponseDto execute(UUID accountId, Long documentId) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

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
                account.checkUserValidation();

                // UserOwnerJobPosting 고용주 유효성 체크
                userOwnerJobPosting.checkOwnerUserOwnerJobPostingValidation(accountId);

                break;

            default:
                throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }

        // IntegratedApplication 조회
        IntegratedApplication integratedApplication = integratedApplicationRepository.findWithSchoolByIdOrElseThrow(documentId);

        return ReadIntegratedApplicationDetailResponseDto.from(integratedApplication);
    }


}
