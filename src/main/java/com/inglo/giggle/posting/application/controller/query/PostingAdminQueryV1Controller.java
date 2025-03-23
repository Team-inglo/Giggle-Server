package com.inglo.giggle.posting.application.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.posting.application.dto.response.ReadAdminJobPostingsOverviewsResponseDto;
import com.inglo.giggle.posting.application.dto.response.ReadAdminJobPostingsSummariesResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadAdminJobPostingsOverviewsUseCase;
import com.inglo.giggle.posting.application.usecase.ReadAdminJobPostingsSummariesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins/job-postings")
public class PostingAdminQueryV1Controller {

    private final ReadAdminJobPostingsSummariesUseCase readAdminJobPostingsSummariesUseCase;
    private final ReadAdminJobPostingsOverviewsUseCase readAdminJobPostingsOverviewsUseCase;

    /**
     * 4.14 (어드민) 공고 등록 수 조회하기
     */
    @GetMapping("/summaries")
    public ResponseDto<ReadAdminJobPostingsSummariesResponseDto> readAdminJobPostingsSummaries(
            @RequestParam("start_date") String stringStartDate,
            @RequestParam("end_date") String stringEndDate,
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(
                readAdminJobPostingsSummariesUseCase.execute(
                        accountId,
                        stringStartDate,
                        stringEndDate
                )
        );
    }

    /**
     * 4.15 (어드민) 공고 목록 조회하기
     */
    @GetMapping("/overviews")
    public ResponseDto<ReadAdminJobPostingsOverviewsResponseDto> readAdminJobPostingsOverviews(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "start_date") String startDate,
            @RequestParam(value = "end_date") String endDate,
            @RequestParam(value = "filter_type", required = false) String filterType,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort_type", required = false) String sortType,
            @RequestParam(value = "sort", required = false) String sort

    ) {
        return ResponseDto.ok(
                readAdminJobPostingsOverviewsUseCase.execute(
                        page,
                        size,
                        search,
                        startDate,
                        endDate,
                        filterType,
                        filter,
                        sortType,
                        sort
                )
        );
    }
}

