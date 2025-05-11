package com.inglo.giggle.document.application.service;

import com.inglo.giggle.document.application.usecase.UpdateUserPartTimeEmploymentPermitUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.persistence.repository.DocumentRepository;
import com.inglo.giggle.document.persistence.repository.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.document.presentation.dto.request.UpdateUserPartTimeEmploymentPermitRequestDto;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.persistence.repository.UserOwnerJobPostingRepository;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserPartTimeEmploymentPermitService implements UpdateUserPartTimeEmploymentPermitUseCase {

    private final LoadAccountPort loadAccountPort;
    private final DocumentRepository documentRepository;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final UserOwnerJobPostingRepository userOwnerJobPostingRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId, UpdateUserPartTimeEmploymentPermitRequestDto requestDto) {

        // Account 조회
        Account account = loadAccountPort.loadAccount(accountId);

        // 계정 타입 유효성 체크
        account.checkUserValidation();

        // Document 조회
        Document document = documentRepository.findByIdOrElseThrow(documentId);

        // UserOwnerJobPosting 정보 조회
        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

        // PartTimeEmploymentPermit 조회
        PartTimeEmploymentPermit partTimeEmploymentPermit = partTimeEmploymentPermitRepository.findByIdOrElseThrow(documentId);

        // PartTimeEmploymentPermit 수정 유효성 체크
        partTimeEmploymentPermit.checkUpdateOrSubmitUserPartTimeEmploymentPermitValidation();

        // PartTimeEmploymentPermit 수정
        partTimeEmploymentPermit.updateByUser(
                requestDto.firstName(),
                requestDto.lastName(),
                requestDto.major(),
                requestDto.termOfCompletion(),
                requestDto.phoneNumber(),
                requestDto.email()
        );
        partTimeEmploymentPermitRepository.save(partTimeEmploymentPermit);
    }

}
