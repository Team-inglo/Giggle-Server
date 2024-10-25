package com.inglo.giggle.posting.application.controller.command;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.posting.application.dto.request.UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewRequestDto;
import com.inglo.giggle.posting.application.usecase.UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewUseCase;
import com.inglo.giggle.posting.application.usecase.UpdateOwnerUserOwnerJobPostingStepWaitingForInterviewUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class PostingOwnersCommandV1Controller {

    private final UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewUseCase updateOwnerUserOwnerJobPostingStepResumeUnderReviewUseCase;
    private final UpdateOwnerUserOwnerJobPostingStepWaitingForInterviewUseCase updateOwnerUserOwnerJobPostingStepWaitingForInterviewUseCase;

    /**
     * 6.10 (고용주) 이력서 수락/거절하기
     */
    @PatchMapping("/owners/user-owner-job-postings/{user-owner-job-posting-id}/step-resume-under-review")
    public ResponseDto<Void> acceptOrRejectResume(
            @AccountID UUID accountId,
            @PathVariable(name = "user-owner-job-posting-id") Long userOwnerJobPostingId,
            @Valid @RequestBody UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewRequestDto requestDto
    ) {
        updateOwnerUserOwnerJobPostingStepResumeUnderReviewUseCase.execute(
                accountId,
                userOwnerJobPostingId,
                requestDto
        );
        return ResponseDto.ok(null);
    }

    /**
     * 6.11 (고용주) 인터뷰 완료하기
     */
    @PatchMapping("/owners/user-owner-job-postings/{user-owner-job-posting-id}/step-waiting-for-review")
    public ResponseDto<Void> completeInterview(
            @AccountID UUID accountId,
            @PathVariable(name = "user-owner-job-posting-id") Long userOwnerJobPostingId
    ) {
        updateOwnerUserOwnerJobPostingStepWaitingForInterviewUseCase.execute(
                accountId,
                userOwnerJobPostingId
        );
        return ResponseDto.ok(null);
    }

}
