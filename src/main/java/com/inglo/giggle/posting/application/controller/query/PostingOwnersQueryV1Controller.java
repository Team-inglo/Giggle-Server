package com.inglo.giggle.posting.application.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.posting.application.dto.response.ReadOwnerJobPostingOverviewsResponseDto;
import com.inglo.giggle.posting.application.dto.response.ReadOwnerUserOwnerJobPostingUserBriefResponseDto;
import com.inglo.giggle.posting.application.dto.response.ReadUserOwnerJobPostingDetailResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadOwnerJobPostingOverviewsUseCase;
import com.inglo.giggle.posting.application.usecase.ReadOwnerUserOwnerJobPostingCountUseCase;
import com.inglo.giggle.posting.application.usecase.ReadOwnerUserOwnerJobPostingUserBriefUseCase;
import com.inglo.giggle.posting.application.usecase.ReadUserOwnerJobPostingDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class PostingOwnersQueryV1Controller {

    private final ReadOwnerJobPostingOverviewsUseCase readOwnerJobPostingOverviewsUseCase;
    private final ReadUserOwnerJobPostingDetailUseCase readUserOwnerJobPostingDetailUseCase;
    private final ReadOwnerUserOwnerJobPostingUserBriefUseCase readOwnerUserOwnerJobPostingUserBriefUseCase;
    private final ReadOwnerUserOwnerJobPostingCountUseCase readOwnerUserOwnerJobPostingCountUseCase;

    /**
     * 6.6 (고용주) 등록한 공고 리스트 조회하기
     */
    @GetMapping("/owners/job-postings/overviews")
    public ResponseDto<ReadOwnerJobPostingOverviewsResponseDto> readOwnerJobPostingList(
            @AccountID UUID accountId,
            Integer page,
            Integer size
    ) {
        return ResponseDto.ok(readOwnerJobPostingOverviewsUseCase.execute(
                accountId,
                page,
                size
        ));
    }

    /**
     * 6.7 (고용주) 지원자 지원 상태 상세 조회
     */
    @GetMapping("/owners/user-owner-job-postings/{user-owner-job-postings-id}/details")
    public ResponseDto<ReadUserOwnerJobPostingDetailResponseDto> readUserOwnerJobPostingDetails(
            @AccountID UUID accountId,
            @PathVariable(name = "user-owner-job-postings-id") Long userOwnerJobPostingsId
    ) {
        return ResponseDto.ok(readUserOwnerJobPostingDetailUseCase.execute(
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
