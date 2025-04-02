package com.inglo.giggle.posting.presentation.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.posting.presentation.dto.response.ReadOwnerJobPostingOverviewsResponseDto;
import com.inglo.giggle.posting.presentation.dto.response.ReadOwnerUserOwnerJobPostingDetailResponseDto;
import com.inglo.giggle.posting.presentation.dto.response.ReadOwnerUserOwnerJobPostingUserBriefResponseDto;
import com.inglo.giggle.posting.presentation.dto.response.ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadOwnerJobPostingOverviewsUseCase;
import com.inglo.giggle.posting.application.usecase.ReadOwnerUserOwnerJobPostingCountUseCase;
import com.inglo.giggle.posting.application.usecase.ReadOwnerUserOwnerJobPostingDetailUseCase;
import com.inglo.giggle.posting.application.usecase.ReadOwnerUserOwnerJobPostingUserBriefUseCase;
import com.inglo.giggle.posting.application.usecase.ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class PostingOwnersQueryV1Controller {

    private final ReadOwnerJobPostingOverviewsUseCase readOwnerJobPostingOverviewsUseCase;
    private final ReadOwnerUserOwnerJobPostingDetailUseCase readOwnerUserOwnerJobPostingDetailUseCase;
    private final ReadOwnerUserOwnerJobPostingUserBriefUseCase readOwnerUserOwnerJobPostingUserBriefUseCase;
    private final ReadOwnerUserOwnerJobPostingCountUseCase readOwnerUserOwnerJobPostingCountUseCase;
    private final ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsUseCase readOwnersJobPostingUserOwnerJobPostingUserOverviewsUseCase;

    /**
     * 4.6 (고용주) 공고에 대한 지원자 리스트 조회
     */
    @GetMapping("/owners/job-postings/{job-posting-id}/user-owner-job-postings/users/overviews")
    public ResponseDto<ReadOwnersJobPostingUserOwnerJobPostingUserOverviewsResponseDto> readUserOwnerJobPostingList(
            @AccountID UUID accountId,
            @PathVariable(name = "job-posting-id") Long jobPostingId,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sorting", defaultValue = "DESCENDING") String sorting,
            @RequestParam(name = "status", required = false) String status

    ) {
        return ResponseDto.ok(readOwnersJobPostingUserOwnerJobPostingUserOverviewsUseCase.execute(
                accountId,
                jobPostingId,
                page,
                size,
                sorting,
                status
        ));
    }

    /* -------------------------------------------- */
    /* API 6 -------------------------------------- */
    /* -------------------------------------------- */

    /**
     * 6.6 (고용주) 등록한 공고 리스트 조회하기
     */
    @GetMapping("/owners/job-postings/overviews")
    public ResponseDto<ReadOwnerJobPostingOverviewsResponseDto> readOwnerJobPostingList(
            @AccountID UUID accountId,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "sorting", defaultValue = "DESCENDING") String sorting


    ) {
        return ResponseDto.ok(readOwnerJobPostingOverviewsUseCase.execute(
                accountId,
                page,
                size,
                sorting
        ));
    }

    /**
     * 6.7 (고용주) 지원자 지원 상태 상세 조회
     */
    @GetMapping("/owners/user-owner-job-postings/{user-owner-job-postings-id}/details")
    public ResponseDto<ReadOwnerUserOwnerJobPostingDetailResponseDto> readUserOwnerJobPostingDetails(
            @AccountID UUID accountId,
            @PathVariable(name = "user-owner-job-postings-id") Long userOwnerJobPostingsId
    ) {
        return ResponseDto.ok(readOwnerUserOwnerJobPostingDetailUseCase.execute(
                accountId,
                userOwnerJobPostingsId
        ));
    }

    /**
     * 6.8 (고용주) 지원자 간단 정보 조회하기
     */
    @GetMapping("/owners/user-owner-job-postings/{user-owner-job-postings-id}/users/briefs")
    public ResponseDto<ReadOwnerUserOwnerJobPostingUserBriefResponseDto> readUserOwnerJobPostingBrief(
            @AccountID UUID accountId,
            @PathVariable(name = "user-owner-job-postings-id") Long userOwnerJobPostingsId
    ) {
        return ResponseDto.ok(readOwnerUserOwnerJobPostingUserBriefUseCase.execute(
                accountId,
                userOwnerJobPostingsId
        ));
    }

    /**
     * 6.9 (고용주) 지원 현황(개수) 확인하기
     */
    @GetMapping("/owners/user-owner-job-postings/counts")
    public ResponseDto<?> readUserOwnerJobPostingCounts(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readOwnerUserOwnerJobPostingCountUseCase.execute(
                accountId
        ));
    }
}
