package com.inglo.giggle.posting.application.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.posting.application.dto.response.ReadJobPostingDetailResponseDto;
import com.inglo.giggle.posting.application.dto.response.ReadJobPostingSummariesResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadJobPostingDetailUseCase;
import com.inglo.giggle.posting.application.usecase.ReadJobPostingSummariesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class PostingQueryV1Controller {

    private final ReadJobPostingSummariesUseCase readJobPostingSummariesUseCase;
    private final ReadJobPostingDetailUseCase readJobPostingDetailUseCase;

    /**
     * 4.3 (유학생/고용주) 공고 리스트 조회하기
     */
    @GetMapping("/job-postings/overviews")




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
