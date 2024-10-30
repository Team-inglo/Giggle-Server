package com.inglo.giggle.posting.application.controller.command;

import com.inglo.giggle.account.application.dto.request.UpdateOwnerRequestDto;
import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.posting.application.dto.request.CreateOwnerJobPostingRequestDto;
import com.inglo.giggle.posting.application.dto.request.UpdateOwnerJobPostingRequestDto;
import com.inglo.giggle.posting.application.dto.request.UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewRequestDto;
import com.inglo.giggle.posting.application.dto.response.CreateOwnerJobPostingResponseDto;
import com.inglo.giggle.posting.application.usecase.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class PostingOwnersCommandV1Controller {

    private final UpdateOwnerUserOwnerJobPostingStepResumeUnderReviewUseCase updateOwnerUserOwnerJobPostingStepResumeUnderReviewUseCase;
    private final UpdateOwnerUserOwnerJobPostingStepWaitingForInterviewUseCase updateOwnerUserOwnerJobPostingStepWaitingForInterviewUseCase;
    private final CreateOwnerJobPostingUseCase createOwnerJobPostingUseCase;
    private final UpdateOwnerJobPostingUseCase updateOwnerJobPostingUseCase;
    private final DeleteOwnerJobPostingUseCase deleteOwnerJobPostingUseCase;

    /**
     * 4.10 (고용주) 공고 등록하기
     */
    @PostMapping(value = "/owners/job-postings", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto<CreateOwnerJobPostingResponseDto> registerJobPosting(
            @AccountID UUID accountId,
            @RequestPart(value = "image", required = false) List<MultipartFile> image,
            @Valid @RequestPart(value = "body") CreateOwnerJobPostingRequestDto requestDto
    ) {
        return ResponseDto.created(createOwnerJobPostingUseCase.execute(
                accountId,
                image,
                requestDto
        ));
    }

    /**
     * 4.11 (고용주) 공고 수정하기
     */
    @PutMapping(value = "/owners/job-postings/{job-posting-id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto<Void> updateJobPosting(
            @AccountID UUID accountId,
            @PathVariable(name = "job-posting-id") Long jobPostingId,
            @RequestPart(value = "image", required = false) List<MultipartFile> image,
            @Valid @RequestPart(value = "body") UpdateOwnerJobPostingRequestDto requestDto
    ) {
        updateOwnerJobPostingUseCase.execute(
                image,
                accountId,
                jobPostingId,
                requestDto);

        return ResponseDto.ok(null);
    }

    /**
     * 4.12 (고용주) 공고 삭제하기
     */
    @DeleteMapping("/owners/job-postings/{job-posting-id}")
    public ResponseDto<Void> deleteJobPosting(
            @AccountID UUID accountId,
            @PathVariable(name = "job-posting-id") Long jobPostingId
    ) {
        deleteOwnerJobPostingUseCase.execute(
                accountId,
                jobPostingId
        );

        return ResponseDto.ok(null);
    }

    /* -------------------------------------------- */
    /* API 6 -------------------------------------- */
    /* -------------------------------------------- */

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
