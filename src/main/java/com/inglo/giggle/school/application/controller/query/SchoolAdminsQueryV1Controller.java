package com.inglo.giggle.school.application.controller.query;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.school.application.dto.response.ReadAdminSchoolOverviewResponseDto;
import com.inglo.giggle.school.application.usecase.ReadAdminSchoolOverviewUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins/schools")
public class SchoolAdminsQueryV1Controller {

    private final ReadAdminSchoolOverviewUseCase readAdminSchoolOverviewUseCase;

    @GetMapping("/overviews")
    public ResponseDto<ReadAdminSchoolOverviewResponseDto> readAdminSchoolOverview(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "search", required = false) String search
    ) {
        return ResponseDto.ok(readAdminSchoolOverviewUseCase.execute(page, size, search));
    }
}
