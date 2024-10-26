package com.inglo.giggle.posting.application.controller.command;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.posting.application.dto.request.UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewRequestDto;
import com.inglo.giggle.posting.application.usecase.UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class PostingOwnersCommandV1Controller {

    private final UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewUseCase updateOwnerUserOwnerJobPostingStepResumeUnderReviewUseCase;

    /**
     * 6.10 (고용주) 이력서 수락/거절하기
     */
    @PatchMapping("/owners/user-owner-job-postings/{user-owner-job-posting-id}/step-resume-under-review")
    public ResponseDto<Void> acceptOrRejectResume(
            @AccountID UUID accountId,
            @PathVariable(name = "user-owner-job-posting-id") Long userOwnerJobPostingId,
            @RequestBody UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewRequestDto requestDto
    ) {
        updateOwnerUserOwnerJobPostingStepResumeUnderReviewUseCase.execute(
                accountId,
                userOwnerJobPostingId,
                requestDto
        );
        return ResponseDto.ok(null);
    }

}
