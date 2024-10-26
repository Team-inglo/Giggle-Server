package com.inglo.giggle.posting.application.controller.command;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.posting.application.usecase.UpdateUserUserOwnerJobPostingStepFillingOutDocumentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class PostingUsersCommandV1Controller {

    private final UpdateUserUserOwnerJobPostingStepFillingOutDocumentUseCase updateUserUserOwnerJobPostingStepFillingOutDocumentUseCase;

    /**
     * 6.12 (유학생) 서류 작성 완료하기
     */
    @PatchMapping("/users/user-owner-job-postings/{user-owner-job-posting-id}/step-filling-out-documents")
    public ResponseDto<Void> completeDocumentWriting(
            @AccountID UUID accountId,
            @PathVariable(name = "user-owner-job-posting-id") Long userOwnerJobPostingId
    ) {
        updateUserUserOwnerJobPostingStepFillingOutDocumentUseCase.execute(
                accountId,
                userOwnerJobPostingId
        );
        return ResponseDto.ok(null);
    }

}
