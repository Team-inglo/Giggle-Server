package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.dto.request.UpdateUserPartTimeEmploymentPermitRequestDto;
import com.inglo.giggle.document.application.usecase.UpdateUserPartTimeEmploymentPermitUseCase;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.domain.service.PartTimeEmploymentPermitService;
import com.inglo.giggle.document.repository.DocumentRepository;
import com.inglo.giggle.document.repository.PartTimeEmploymentPermitRepository;
import com.inglo.giggle.posting.domain.service.UserOwnerJobPostingService;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserPartTimeEmploymentPermitService implements UpdateUserPartTimeEmploymentPermitUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final DocumentRepository documentRepository;
    private final UserOwnerJobPostingService userOwnerJobPostingService;
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;
    private final PartTimeEmploymentPermitService partTimeEmploymentPermitService;

    @Override
    @Transactional
    public void execute(UUID accountId, Long documentId, UpdateUserPartTimeEmploymentPermitRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 계정 타입 유효성 체크
        accountService.checkUserValidation(account);

        // Document 조회
        Document document = documentRepository.findWithUserOwnerJobPostingByIdOrElseThrow(documentId);

        // UserOwnerJobPosting 유저 유효성 체크
        userOwnerJobPostingService.checkUserUserOwnerJobPostingValidation(document.getUserOwnerJobPosting(), accountId);

        // PartTimeEmploymentPermit 조회
        PartTimeEmploymentPermit partTimeEmploymentPermit = partTimeEmploymentPermitRepository.findByIdOrElseThrow(documentId);

        // PartTimeEmploymentPermit 수정 유효성 체크
        partTimeEmploymentPermitService.checkUpdateOrSubmitUserPartTimeEmploymentPermitValidation(partTimeEmploymentPermit);

        // PartTimeEmploymentPermit 수정
        PartTimeEmploymentPermit updatedPartTimeEmploymentPermit = partTimeEmploymentPermitService.updateUserPartTimeEmploymentPermit(
                partTimeEmploymentPermit,
                requestDto.firstName(),
                requestDto.lastName(),
                requestDto.major(),
                requestDto.termOfCompletion(),
                requestDto.phoneNumber(),
                requestDto.email()
        );
        partTimeEmploymentPermitRepository.save(updatedPartTimeEmploymentPermit);
    }

}
