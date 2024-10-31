package com.inglo.giggle.posting.application.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.posting.application.dto.response.ReadJobPostingDetailResponseDto;
import com.inglo.giggle.posting.application.dto.response.ReadJobPostingOverviewResponseDto;
import com.inglo.giggle.posting.application.dto.response.ReadJobPostingSummariesResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadJobPostingDetailUseCase;
import com.inglo.giggle.posting.application.usecase.ReadJobPostingOverviewUseCase;
import com.inglo.giggle.posting.application.usecase.ReadJobPostingSummariesUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Slf4j
public class PostingQueryV1Controller {

    private final ReadJobPostingSummariesUseCase readJobPostingSummariesUseCase;
    private final ReadJobPostingDetailUseCase readJobPostingDetailUseCase;
    private final ReadJobPostingOverviewUseCase readJobPostingOverviewUseCase;

    /**
     * 4.3 (유학생/고용주) 공고 리스트 조회하기
     */
    @GetMapping("/job-postings/overviews")
    public ResponseDto<ReadJobPostingOverviewResponseDto> readJobPostingList(
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
        if(type == null) {
            log.info("type is null");
            return ResponseDto.ok(
                    readJobPostingOverviewUseCase.execute(
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
                    )
            );
        }

        return ResponseDto.ok(
                readJobPostingOverviewUseCase.execute(
                        page,
                        size,
                        type
                )
        );
    }

    /**
     * 4.4 (유학생/고용주) 공고 상세 조회하기
     */
    @GetMapping("/job-postings/{job-posting-id}/details")
    public ResponseDto<ReadJobPostingDetailResponseDto> readJobPostingDetail(
            @AccountID UUID accountId,
            @PathVariable(name = "job-posting-id") Long jobPostingId
    ) {
        return ResponseDto.ok(readJobPostingDetailUseCase.execute(
                accountId,
                jobPostingId
        ));
    }

    /**
     * 4.7 (유학생/고용주) 공고 요약 정보 조회하기
     */
    @GetMapping("/job-postings/{job-posting-id}/summaries")
    public ResponseDto<ReadJobPostingSummariesResponseDto> readJobPostingSummaries(
            @AccountID UUID accountId,
            @PathVariable(name = "job-posting-id") Long jobPostingId
    ) {
        return ResponseDto.ok(readJobPostingSummariesUseCase.execute(
                accountId,
                jobPostingId
        ));
    }

}
