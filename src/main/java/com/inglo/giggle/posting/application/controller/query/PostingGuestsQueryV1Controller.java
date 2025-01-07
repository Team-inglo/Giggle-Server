package com.inglo.giggle.posting.application.controller.query;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.posting.application.dto.request.ReadGuestJobPostingOverviewsRequestDto;
import com.inglo.giggle.posting.application.dto.response.ReadGuestJobPostingDetailResponseDto;
import com.inglo.giggle.posting.application.dto.response.ReadGuestJobPostingOverviewsResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadGuestJobPostingDetailUseCase;
import com.inglo.giggle.posting.application.usecase.ReadGuestJobPostingOverviewsUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
            @Valid @ModelAttribute ReadGuestJobPostingOverviewsRequestDto request
    ) {

        if (request.type() == null) {
            return ResponseDto.ok(readGuestJobPostingOverviewsUseCase.execute(
                    request.page(),
                    request.size(),
                    request.search(),
                    request.sorting(),
                    request.region1Depth(),
                    request.region2Depth(),
                    request.region3Depth(),
                    request.industry(),
                    request.workPeriod(),
                    request.workDaysPerWeek(),
                    request.workingDay(),
                    request.workingHours(),
                    request.recruitmentPeriod(),
                    request.employmentType(),
                    request.visa()
            ));
        }

        return ResponseDto.ok(
                readGuestJobPostingOverviewsUseCase.execute(
                        request.page(),
                        request.size(),
                        request.type()
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
