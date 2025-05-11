package com.inglo.giggle.school.adapter.in.web;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.school.application.port.in.query.ReadUserSchoolDetailQuery;
import com.inglo.giggle.school.application.port.in.result.ReadUserSchoolBriefResult;
import com.inglo.giggle.school.application.port.in.query.ReadUserSchoolBriefQuery;
import com.inglo.giggle.school.application.port.in.result.ReadUserSchoolDetailResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class SchoolUserQueryV1Controller {

    private final ReadUserSchoolBriefQuery readUserSchoolBriefQuery;
    private final ReadUserSchoolDetailQuery readUserSchoolDetailQuery;

    /**
     * 9.1 (유학생) 학교 검색하기
     */
    @GetMapping("/schools/briefs")
    public ResponseDto<ReadUserSchoolBriefResult> readSchoolBrief(
            String search, Integer page, Integer size
    ) {
        return ResponseDto.ok(readUserSchoolBriefQuery.execute(search, page, size));
    }

    /**
     * 9.2 (유학생) 학교 정보 상세조회하기
     */
    @GetMapping("/resumes/schools/details")
    public ResponseDto<ReadUserSchoolDetailResult> readSchoolDetail(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserSchoolDetailQuery.execute(accountId));
    }
}
