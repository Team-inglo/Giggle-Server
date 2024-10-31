package com.inglo.giggle.resume.application.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.resume.application.dto.response.ReadOwnerResumeDetailResponseDto;
import com.inglo.giggle.resume.application.usecase.ReadOwnerResumeDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/owners")
public class ResumeOwnersQueryV1Controller {
    private final ReadOwnerResumeDetailUseCase readOwnerResumeDetailUseCase;

    @GetMapping("/user-owner-job-postings/{id}/users/resumes/details")
    public ResponseDto<ReadOwnerResumeDetailResponseDto> readOwnerResumeDetail(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readOwnerResumeDetailUseCase.execute(accountId, id));
    }
}
