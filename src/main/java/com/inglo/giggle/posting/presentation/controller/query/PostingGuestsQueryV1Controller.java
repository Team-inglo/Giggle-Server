package com.inglo.giggle.posting.presentation.controller.query;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.presentation.dto.response.ReadGuestJobPostingDetailResponseDto;
import com.inglo.giggle.posting.presentation.dto.response.ReadGuestJobPostingOverviewsResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadGuestJobPostingDetailUseCase;
import com.inglo.giggle.posting.application.usecase.ReadGuestJobPostingOverviewsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class PostingGuestsQueryV1Controller {

    private final ReadGuestJobPostingOverviewsUseCase readGuestJobPostingOverviewsUseCase;
    private final ReadGuestJobPostingDetailUseCase readGuestJobPostingDetailUseCase;

    /**
     * 4.1 (게스트) 공고 리스트 조회하기
     */
    @GetMapping("/guests/job-postings/overviews")
    public ResponseDto<ReadGuestJobPostingOverviewsResponseDto> readGuestJobPostingList(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "search", required = false) String jobTitle,
            @RequestParam(value = "sorting", required = false) String sorting,
            @RequestParam(value = "region_1depth", required = false) String region1Depth,
            @RequestParam(value = "region_2depth", required = false) String region2Depth,
            @RequestParam(value = "region_3depth", required = false) String region3Depth,
            @RequestParam(value = "industry", required = false) String industry,
            @RequestParam(value = "work_period", required = false) String workPeriod,
            @RequestParam(value = "work_days_per_week", required = false) String workDaysPerWeek,
            @RequestParam(value = "working_day", required = false) String workingDay,
            @RequestParam(value = "working_hours", required = false) String workingHours,
            @RequestParam(value = "recruitment_period", required = false) String recruitmentPeriod,
            @RequestParam(value = "employment_type", required = false) String employmentType,
            @RequestParam(value = "visa", required = false) Set<EVisa> visa,
            @RequestParam(value = "type", required = false) String type
    ) {

        if (type == null) {
            return ResponseDto.ok(readGuestJobPostingOverviewsUseCase.execute(
                    page,
                    size,
                    jobTitle,
                    sorting,
                    region1Depth,
                    region2Depth,
                    region3Depth,
                    industry,
                    workPeriod,
                    workDaysPerWeek,
                    workingDay,
                    workingHours,
                    recruitmentPeriod,
                    employmentType,
                    visa
            ));
        }

        return ResponseDto.ok(
                readGuestJobPostingOverviewsUseCase.execute(
                        page,
                        size,
                        type
                )
        );
    }

    /**
     * 4.2 (게스트) 공고 상세 조회하기
     */
    @GetMapping("/guests/job-postings/{job-posting-id}/details")
    public ResponseDto<ReadGuestJobPostingDetailResponseDto> readGuestJobPostingDetail(
            @PathVariable(value = "job-posting-id") Long jobPostingId
    ) {
        return ResponseDto.ok(readGuestJobPostingDetailUseCase.execute(
                jobPostingId
        ));
    }


}
