package com.inglo.giggle.school.adapter.in.web;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.school.application.port.in.query.ReadAdminSchoolDetailQuery;
import com.inglo.giggle.school.application.port.in.result.ReadAdminSchoolDetailResult;
import com.inglo.giggle.school.application.port.in.result.ReadAdminSchoolOverviewResult;
import com.inglo.giggle.school.application.port.in.query.ReadAdminSchoolOverviewQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins/schools")
public class SchoolAdminQueryV1Controller {

    private final ReadAdminSchoolOverviewQuery readAdminSchoolOverviewQuery;
    private final ReadAdminSchoolDetailQuery readAdminSchoolDetailQuery;

    /**
     * 9.3 (어드민) 학교 목록 조회하기
     */
    @GetMapping("/overviews")
    public ResponseDto<ReadAdminSchoolOverviewResult> readAdminSchoolOverview(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "search", required = false) String search
    ) {
        return ResponseDto.ok(readAdminSchoolOverviewQuery.execute(page, size, search));
    }

    /**
     * 9.4 (어드민) 학교 상세 조회하기
     */
    @GetMapping("/{id}/details")
    public ResponseDto<ReadAdminSchoolDetailResult> readAdminSchoolDetail(
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readAdminSchoolDetailQuery.execute(id));
    }

}
