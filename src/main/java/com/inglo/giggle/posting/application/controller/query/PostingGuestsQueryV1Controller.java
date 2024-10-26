package com.inglo.giggle.posting.application.controller.query;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.posting.application.dto.response.ReadGuestJobPostingOverviewsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class PostingGuestsQueryV1Controller {

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
            @RequestParam(value = "visa", required = false) String visa,
            @RequestParam(value = "type", required = false) String type
    ) {
        return null;
    }
}
