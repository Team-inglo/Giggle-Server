package com.inglo.giggle.resume.controller.command;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.resume.application.dto.request.UpdateUserResumeRequestDtoV2;
import com.inglo.giggle.resume.application.usecase.UpdateUserResumeUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/users/resumes")
public class ResumeUsersCommandV2Controller {
    private final UpdateUserResumeUseCase updateUserResumeUseCase;

    /**
     * 7.8 (유학생) 자기소개 수정하기
     */
    @PatchMapping("/introduction")
    public ResponseDto<Void> updateUserIntroduction(
            @AccountID UUID accountId,
            @RequestBody @Valid UpdateUserResumeRequestDtoV2 requestDto
    ) {
        updateUserResumeUseCase.executeV2(accountId, requestDto);
        return ResponseDto.ok(null);
    }

}
