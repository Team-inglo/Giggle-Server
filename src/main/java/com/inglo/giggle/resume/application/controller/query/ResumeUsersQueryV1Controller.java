package com.inglo.giggle.resume.application.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.resume.application.dto.response.ReadUserResumeDetailResponseDto;
import com.inglo.giggle.resume.application.usecase.ReadUserResumeDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users/resumes")
public class ResumeUsersQueryV1Controller {
    private final ReadUserResumeDetailUseCase readUserResumeDetailUseCase;

    @GetMapping("/details")
    public ResponseDto<ReadUserResumeDetailResponseDto> readUserResumeDetail(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserResumeDetailUseCase.execute(accountId));
    }
}
