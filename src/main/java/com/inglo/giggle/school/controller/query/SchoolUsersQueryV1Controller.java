package com.inglo.giggle.school.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.school.application.dto.response.ReadUserSchoolBriefResponseDto;
import com.inglo.giggle.school.application.dto.response.ReadUserSchoolDetailResponseDto;
import com.inglo.giggle.school.application.usecase.ReadUserSchoolBriefUseCase;
import com.inglo.giggle.school.application.usecase.ReadUserSchoolDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class SchoolUsersQueryV1Controller {
    private final ReadUserSchoolBriefUseCase readUserSchoolBriefUseCase;
    private final ReadUserSchoolDetailUseCase readUserSchoolDetailUseCase;

    /**
     * 9.1 (유학생) 학교 검색하기
     */
    @GetMapping("/schools/briefs")
    public ResponseDto<ReadUserSchoolBriefResponseDto> readSchoolBreief(
            String search, Integer page, Integer size
    ) {
        return ResponseDto.ok(readUserSchoolBriefUseCase.execute(search, page, size));
    }

    /**
     * 9.2 (유학생) 학교 정보 상세조회하기
     */
    @GetMapping("/resumes/schools/details")
    public ResponseDto<ReadUserSchoolDetailResponseDto> readSchoolDetail(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserSchoolDetailUseCase.execute(accountId));
    }

}
