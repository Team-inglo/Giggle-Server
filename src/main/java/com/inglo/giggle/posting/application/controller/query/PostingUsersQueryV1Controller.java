package com.inglo.giggle.posting.application.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.posting.application.dto.response.*;
import com.inglo.giggle.posting.application.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class PostingUsersQueryV1Controller {

    private final ReadUserUserOwnerJobPostingListUseCase readUserUserOwnerJobPostingListUseCase;
    private final ReadUserUserOwnerJobPostingDetailUseCase readUserUserOwnerJobPostingDetailUseCase;
    private final ReadUserUserOwnerJobPostingBriefListUseCase readUserUserOwnerJobPostingBriefListUseCase;
    private final ReadUserUserOwnerJobPostingCountUseCase readUserUserOwnerJobPostingCountUseCase;
    private final ReadUserUserOwnerJobPostingJobPostingRecruiterUserCase readUserUserOwnerJobPostingJobPostingRecruiterUserCase;
    private final ReadUserJobPostingValidationUseCase readUserJobPostingValidationUseCase;
    private final ReadUserBookMarkOverviewUseCase readUserBookMarkOverviewUseCase;
    private final ReadUserBookMarkCountUseCase readUserBookMarkCountUseCase;
    private final ReadUserJobPostingBriefUseCase readUserJobPostingBriefUseCase;


    /* -------------------------------------------- */
    /* API 4 -------------------------------------- */
    /* -------------------------------------------- */

    /**
     * 4.5 (유학생) 추천 공고 리스트 조회하기
     */
    @GetMapping("/users/job-postings/briefs")
    public ResponseDto<ReadUserJobPostingBriefResponseDto> readUserJobPostingList(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserJobPostingBriefUseCase.execute(
                accountId
        ));
    }

    /**
     * 4.8 (유학생) 공고 지원 자격 확인하기
     */
    @GetMapping("/users/job-postings/{job-posting-id}/validation")
    public ResponseDto<ReadUserJobPostingValidationResponseDto> readUserOwnerJobPostingValidation(
            @AccountID UUID accountId,
            @PathVariable(name = "job-posting-id") Long jobPostingId
    ) throws Exception {
        return ResponseDto.ok(readUserJobPostingValidationUseCase.execute(
                accountId,
                jobPostingId
        ));
    }

    /* -------------------------------------------- */
    /* API 5 -------------------------------------- */
    /* -------------------------------------------- */

    /**
     * 5.1 (유학생) 북마크한 공고 리스트 조회하기
     */
    @GetMapping("/users/book-marks/overviews")
    public ResponseDto<ReadUserBookMarkOverviewResponseDto> readUserBookMarkList(
            @AccountID UUID accountId,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        return ResponseDto.ok(readUserBookMarkOverviewUseCase.execute(
                accountId,
                page,
                size
        ));
    }

    /**
     * 5.2 (유학생) 북마크 현황(개수) 확인하기
     */
    @GetMapping("/users/book-marks/counts")
    public ResponseDto<ReadUserBookMarkCountResponseDto> readUserBookMarkCount(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserBookMarkCountUseCase.execute(
                accountId
        ));
    }


    /* -------------------------------------------- */
    /* API 6 -------------------------------------- */
    /* -------------------------------------------- */

    /**
     * 6.1 (유학생) 지원한 공고 리스트 조회하기
     */
    @GetMapping("/users/user-owner-job-postings/overviews")
    public ResponseDto<ReadUserUserOwnerJobPostingListResponseDto> readUserAppliedJobList(
            @AccountID UUID accountId,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name= "sorting", defaultValue = "ASCENDING") String sort,
            @RequestParam(name = "status", defaultValue = "ALL", required = false) String status
    ) {
        return ResponseDto.ok(readUserUserOwnerJobPostingListUseCase.execute(
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
    public ResponseDto<ReadUserUserOwnerJobPostingDetailResponseDto> readUserAppliedJobDetail(
            @AccountID UUID accountId,
            @PathVariable(name = "id") Long userOwnerJobPostingsId
    ) {
        return ResponseDto.ok(readUserUserOwnerJobPostingDetailUseCase.execute(
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
       return ResponseDto.ok(readUserUserOwnerJobPostingBriefListUseCase.execute(
               accountId,
               page,
               size
       ));
    }

    /**
     * 6.4 (유학생) 지원 현황(개수) 확인하기
     */
    @GetMapping("/users/user-owner-job-postings/counts")
    public ResponseDto<ReadUserOwnerJobPostingCountResponseDto> readUserAppliedJobCount(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserUserOwnerJobPostingCountUseCase.execute(
                accountId
        ));
    }

    /**
     * 6.5 (유학생) 공고 담당자 정보 조회하기
     */
    @GetMapping("/users/user-owner-job-postings/{user-owner-job-postings-id}/job-postings/recruiters")
    public ResponseDto<ReadUserOwnerJobPostingJobPostingRecruiterResponseDto> readRecruiterInfo(
            @AccountID UUID accountId,
            @PathVariable(name = "user-owner-job-postings-id") Long userOwnerJobPostingsId
    ) {
        return ResponseDto.ok(readUserUserOwnerJobPostingJobPostingRecruiterUserCase.execute(
                accountId,
                userOwnerJobPostingsId
        ));
    }


}
