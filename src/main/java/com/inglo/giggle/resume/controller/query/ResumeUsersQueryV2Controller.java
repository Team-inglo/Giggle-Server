package com.inglo.giggle.resume.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.resume.application.dto.response.ReadUserResumeDetailResponseDtoV2;
import com.inglo.giggle.resume.application.usecase.ReadUserResumeDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/users/resumes")
public class ResumeUsersQueryV2Controller {
    private final ReadUserResumeDetailUseCase readUserResumeDetailUseCase;

    /** 
     * 7.1 (유학생) 이력서 조회하기
     */
    @GetMapping("/details")
    public ResponseDto<ReadUserResumeDetailResponseDtoV2> readUserResumeDetail(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserResumeDetailUseCase.executeV2(accountId));
    }

}
