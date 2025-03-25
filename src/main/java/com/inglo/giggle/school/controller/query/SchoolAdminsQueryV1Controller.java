package com.inglo.giggle.school.controller.query;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.school.application.dto.response.ReadAdminSchoolDetailResponseDto;
import com.inglo.giggle.school.application.dto.response.ReadAdminSchoolOverviewResponseDto;
import com.inglo.giggle.school.application.usecase.ReadAdminSchoolDetailUseCase;
import com.inglo.giggle.school.application.usecase.ReadAdminSchoolOverviewUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins/schools")
public class SchoolAdminsQueryV1Controller {

    private final ReadAdminSchoolOverviewUseCase readAdminSchoolOverviewUseCase;
    private final ReadAdminSchoolDetailUseCase readAdminSchoolDetailUseCase;

    /**
     * 9.3 (어드민) 학교 목록 조회하기
     */
    @GetMapping("/overviews")
    public ResponseDto<ReadAdminSchoolOverviewResponseDto> readAdminSchoolOverview(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "search", required = false) String search
    ) {
        return ResponseDto.ok(readAdminSchoolOverviewUseCase.execute(page, size, search));
    }

    /**
     * 9.4 (어드민) 학교 상세 조회하기
     */
    @GetMapping("/{id}/details")
    public ResponseDto<ReadAdminSchoolDetailResponseDto> readAdminSchoolDetail(
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readAdminSchoolDetailUseCase.execute(id));
    }

}
