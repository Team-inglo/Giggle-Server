package com.inglo.giggle.resume.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.resume.application.dto.response.ReadOwnerResumeDetailResponseDtoV2;
import com.inglo.giggle.resume.application.usecase.ReadOwnerResumeDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/owners")
public class ResumeOwnersQueryV2Controller {
    private final ReadOwnerResumeDetailUseCase readOwnerResumeDetailUseCase;

    /**
     * 7.19 (고용주) 이력서 조회하기
     */
    @GetMapping("/user-owner-job-postings/{id}/users/resumes/details")
    public ResponseDto<ReadOwnerResumeDetailResponseDtoV2> readOwnerResumeDetail(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readOwnerResumeDetailUseCase.executeV2(accountId, id));
    }
}
