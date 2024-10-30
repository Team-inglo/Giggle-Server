package com.inglo.giggle.school.application.controller.query;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.school.application.dto.response.ReadSchoolBriefResponseDto;
import com.inglo.giggle.school.application.usecase.ReadSchoolBriefUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class SchoolUsersQueryV1Controller {
    private final ReadSchoolBriefUseCase readSchoolBriefUseCase;

    /**
     * 9.1 (유학생) 학교 검색하기
     */
    @GetMapping("/schools/briefs")
    public ResponseDto<ReadSchoolBriefResponseDto> readSchoolBreief(
            String search, Integer page, Integer size
    ) {
        return ResponseDto.ok(readSchoolBriefUseCase.execute(search, page, size));
    }
}
