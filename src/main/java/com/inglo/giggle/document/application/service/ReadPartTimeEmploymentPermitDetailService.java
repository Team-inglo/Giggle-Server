package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.usecase.ReadPartTimeEmploymentPermitDetailUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.persistence.repository.DocumentRepository;
import com.inglo.giggle.document.persistence.repository.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.presentation.dto.response.ReadPartTimeEmploymentPermitDetailResponseDto;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import jakarta.persistence.DiscriminatorValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadPartTimeEmploymentPermitDetailService implements ReadPartTimeEmploymentPermitDetailUseCase {

    private final LoadAccountPort loadAccountPort;
    private final DocumentRepository documentRepository;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    @Override
    public ReadPartTimeEmploymentPermitDetailResponseDto execute(UUID accountId, Long documentId) {

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

        // PartTimeEmploymentPermit 조회
        PartTimeEmploymentPermit partTimeEmploymentPermit = partTimeEmploymentPermitRepository.findByIdOrElseThrow(documentId);

        return ReadPartTimeEmploymentPermitDetailResponseDto.from(partTimeEmploymentPermit);
    }
}
