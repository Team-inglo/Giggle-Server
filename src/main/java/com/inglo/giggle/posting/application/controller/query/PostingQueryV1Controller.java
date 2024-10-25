package com.inglo.giggle.posting.application.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.posting.application.dto.response.ReadUserOwnerJobPostingBriefListResponseDto;
import com.inglo.giggle.posting.application.dto.response.ReadUserOwnerJobPostingCountResponseDto;
import com.inglo.giggle.posting.application.dto.response.ReadUserOwnerJobPostingDetailResponseDto;
import com.inglo.giggle.posting.application.dto.response.ReadUserOwnerJobPostingListResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadUserOwnerJobPostingBriefListUseCase;
import com.inglo.giggle.posting.application.usecase.ReadUserOwnerJobPostingCountUseCase;
import com.inglo.giggle.posting.application.usecase.ReadUserOwnerJobPostingDetailUseCase;
import com.inglo.giggle.posting.application.usecase.ReadUserOwnerJobPostingListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class PostingQueryV1Controller {

    private final ReadUserOwnerJobPostingListUseCase readUserOwnerJobPostingListUseCase;
    private final ReadUserOwnerJobPostingDetailUseCase readUserOwnerJobPostingDetailUseCase;
    private final ReadUserOwnerJobPostingBriefListUseCase readUserOwnerJobPostingBriefListUseCase;
    private final ReadUserOwnerJobPostingCountUseCase readUserOwnerJobPostingCountUseCase;

    /**
     * 6.1 (유학생) 지원한 공고 리스트 조회하기
     */
    @GetMapping("/users/user-owner-job-postings/overviews")
    public ResponseDto<ReadUserOwnerJobPostingListResponseDto> readUserAppliedJobList(
            @AccountID UUID accountId,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name= "sorting", defaultValue = "ASCENDING") String sort,
            @RequestParam(name = "status", defaultValue = "ALL", required = false) String status
    ) {
        return ResponseDto.ok(readUserOwnerJobPostingListUseCase.execute(
                accountId,
                page,
                size,
                sort,
                status
        ));
    }

    /**
     * 6.2 (유학생) 지원한 공고 상태 상세 조회하기
     */
    @GetMapping("/users/user-owner-job-postings/{id}/details")
    public ResponseDto<ReadUserOwnerJobPostingDetailResponseDto> readUserAppliedJobDetail(
            @AccountID UUID accountId,
            @PathVariable(name = "id") Long userOwnerJobPostingsId
    ) {
        return ResponseDto.ok(readUserOwnerJobPostingDetailUseCase.execute(
                accountId,
                userOwnerJobPostingsId
        ));
    }

    /**
     * 6.3 (유학생) 현재 진행중인 인터뷰 리스트 조회하기
     */
    @GetMapping("/users/user-owner-job-postings/briefs")
    public ResponseDto<ReadUserOwnerJobPostingBriefListResponseDto> readUserOngoingJobList(
            @AccountID UUID accountId,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
       return ResponseDto.ok(readUserOwnerJobPostingBriefListUseCase.execute(
               accountId,
               page,
               size
       ));
    }

    /**
     * 6.4 (유학생) 지원 현황(개수) 확인하기
     */
    @GetMapping("/users/user-owner-job-postings/count")
    public ResponseDto<ReadUserOwnerJobPostingCountResponseDto> readUserAppliedJobCount(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserOwnerJobPostingCountUseCase.execute(
                accountId
        ));
    }



}
