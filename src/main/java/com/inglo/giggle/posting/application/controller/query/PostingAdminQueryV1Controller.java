package com.inglo.giggle.posting.application.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.posting.application.dto.response.*;
import com.inglo.giggle.posting.application.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins")
public class PostingAdminQueryV1Controller {

    private final ReadAdminJobPostingsSummariesUseCase readAdminJobPostingsSummariesUseCase;
    private final ReadAdminJobPostingsOverviewsUseCase readAdminJobPostingsOverviewsUseCase;
    private final ReadAdminUserOwnerJobPostingsSummariesUseCase readAdminUserOwnerJobPostingsSummariesUseCase;
    private final ReadAdminUserOwnerJobPostingsApplicationSuccessSummariesUseCase readAdminUserOwnerJobPostingsApplicationSuccessSummariesUseCase;
    private final ReadAdminUserOwnerJobPostingsOverviewsUseCase readAdminUserOwnerJobPostingsOverviewsUseCase;

    /**
     * 4.14 (어드민) 공고 등록 수 조회하기
     */
    @GetMapping("/job-postings/summaries")
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
    @GetMapping("/job-postings/overviews")
    public ResponseDto<ReadAdminJobPostingsOverviewsResponseDto> readAdminJobPostingsOverviews(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "start_date", required = false) String startDate,
            @RequestParam(value = "end_date", required = false) String endDate,
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

    /* -------------------------------------------- */
    /* API 6 -------------------------------------- */
    /* -------------------------------------------- */

    /**
     * 6.16 (어드민) 공고 신청 수 조회하기
     */
    @GetMapping("/user-owner-job-postings/summaries")
    public ResponseDto<ReadAdminUserOwnerJobPostingsSummariesResponseDto> readAdminUserOwnerJobPostings(
            @RequestParam("start_date") String stringStartDate,
            @RequestParam("end_date") String stringEndDate
    ) {
        return ResponseDto.ok(
                readAdminUserOwnerJobPostingsSummariesUseCase.execute(
                        stringStartDate,
                        stringEndDate
                )
        );
    }

    /**
     * 6.17 (어드민) 매칭 성공 수 조회하기
     */
    @GetMapping("/user-owner-job-postings/application-success/summaries")
    public ResponseDto<ReadAdminUserOwnerJobPostingsApplicationSuccessSummariesResponseDto> readAdminUserOwnerJobPostingsApplicationSuccess(
            @RequestParam("start_date") String stringStartDate,
            @RequestParam("end_date") String stringEndDate
    ) {
        return ResponseDto.ok(
                readAdminUserOwnerJobPostingsApplicationSuccessSummariesUseCase.execute(
                        stringStartDate,
                        stringEndDate
                )
        );
    }

    /**
     * 6.18 (어드민) 지원 목록 조회하기
     */
    @GetMapping("/user-owner-job-postings/overviews")
    public ResponseDto<ReadAdminUserOwnerJobPostingsOverviewsResponseDto> readAdminUserOwnerJobPostingsOverviews(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "start_date", required = false) String startDate,
            @RequestParam(value = "end_date", required = false) String endDate,
            @RequestParam(value = "filter_type", required = false) String filterType,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort_type", required = false) String sortType,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        return ResponseDto.ok(
                readAdminUserOwnerJobPostingsOverviewsUseCase.execute(
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

