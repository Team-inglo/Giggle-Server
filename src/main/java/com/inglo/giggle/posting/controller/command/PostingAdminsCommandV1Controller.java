package com.inglo.giggle.posting.controller.command;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.posting.application.dto.request.UpdateAdminUserOwnerJobPostingMemoRequestDto;
import com.inglo.giggle.posting.application.usecase.UpdateAdminUserOwnerJobPostingMemoUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins")
public class PostingAdminsCommandV1Controller {

    private final UpdateAdminUserOwnerJobPostingMemoUseCase updateAdminUserOwnerJobPostingMemoUseCase;

    /* -------------------------------------------- */
    /* API 6 -------------------------------------- */
    /* -------------------------------------------- */

    /**
     * 6.21 (어드민) 공고 메모 수정하기
     */
    @PatchMapping("/user-owner-job-postings/{user-owner-job-posting-id}/memo")
    public ResponseDto<Void> updateAdminUserOwnerJobPostingMemo(
            @PathVariable(name = "user-owner-job-posting-id") Long userOwnerJobPostingId,
            @RequestBody @Valid UpdateAdminUserOwnerJobPostingMemoRequestDto requestDto
    ) {
        updateAdminUserOwnerJobPostingMemoUseCase.execute(
                userOwnerJobPostingId,
                requestDto.memo()
        );
        return ResponseDto.ok(null);
    }
}
