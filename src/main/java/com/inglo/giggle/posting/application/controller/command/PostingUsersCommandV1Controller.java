package com.inglo.giggle.posting.application.controller.command;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.posting.application.dto.request.UpdateUserUserOwnerJobPostingStepRegisteringResultRequestDto;
import com.inglo.giggle.posting.application.usecase.UpdateUserUserOwnerJobPostingStepRegisteringResultUseCase;
import com.inglo.giggle.posting.application.usecase.UpdateUserUserOwnerJobPostingStepApplicationInProgressUseCase;
import com.inglo.giggle.posting.application.usecase.UpdateUserUserOwnerJobPostingStepDocumentUnderReviewUseCase;
import com.inglo.giggle.posting.application.usecase.UpdateUserUserOwnerJobPostingStepFillingOutDocumentsUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class PostingUsersCommandV1Controller {

    private final UpdateUserUserOwnerJobPostingStepFillingOutDocumentsUseCase updateUserUserOwnerJobPostingStepFillingOutDocumentsUseCase;
    private final UpdateUserUserOwnerJobPostingStepDocumentUnderReviewUseCase updateUserUserOwnerJobPostingStepDocumentUnderReviewUseCase;
    private final UpdateUserUserOwnerJobPostingStepApplicationInProgressUseCase updateUserUserOwnerJobPostingStepApplicationInProgressUseCase;
    private final UpdateUserUserOwnerJobPostingStepRegisteringResultUseCase updateUserUserOwnerJobPostingStepRegisteringResultUseCase;
    /**
     * 6.12 (유학생) 서류 작성 완료하기
     */
    @PatchMapping("/users/user-owner-job-postings/{user-owner-job-posting-id}/step-filling-out-documents")
    public ResponseDto<Void> completeDocumentWriting(
            @AccountID UUID accountId,
            @PathVariable(name = "user-owner-job-posting-id") Long userOwnerJobPostingId
    ) {
        updateUserUserOwnerJobPostingStepFillingOutDocumentsUseCase.execute(
                accountId,
                userOwnerJobPostingId
        );
        return ResponseDto.ok(null);
    }

    /**
     * 6.13 (유학생) 담당자 검토 완료
     */
    @PatchMapping("/users/user-owner-job-postings/{user-owner-job-posting-id}/step-document-under-review")
    public ResponseDto<Void> completeDocumentReview(
            @AccountID UUID accountId,
            @PathVariable(name = "user-owner-job-posting-id") Long userOwnerJobPostingId
    ) {
        updateUserUserOwnerJobPostingStepDocumentUnderReviewUseCase.execute(
                accountId,
                userOwnerJobPostingId
        );
        return ResponseDto.ok(null);
    }

    /**
     *  6.14 (유학생) 하이코리아 지원
     */
    @PatchMapping("/users/user-owner-job-postings/{user-owner-job-posting-id}/step-application-in-progress")
    public ResponseDto<Void> applyToHiKorea(
            @AccountID UUID accountId,
            @PathVariable(name = "user-owner-job-posting-id") Long userOwnerJobPostingId
    ) {
        updateUserUserOwnerJobPostingStepApplicationInProgressUseCase.execute(
                accountId,
                userOwnerJobPostingId
        );
        return ResponseDto.ok(null);
    }

    /**
     * 6.15 (유학생) 하이코리아 처리결과 등록하기
     */
    @PatchMapping("/users/user-owner-job-postings/{user-owner-job-posting-id}/step-registering-results")
    public ResponseDto<Void> registerHiKoreaResults(
            @AccountID UUID accountId,
            @PathVariable(name = "user-owner-job-posting-id") Long userOwnerJobPostingId,
            @Valid @RequestBody UpdateUserUserOwnerJobPostingStepRegisteringResultRequestDto requestDto
    ) {
        updateUserUserOwnerJobPostingStepRegisteringResultUseCase.execute(
                accountId,
                userOwnerJobPostingId,
                requestDto
        );
        return ResponseDto.ok(null);
    }
}
