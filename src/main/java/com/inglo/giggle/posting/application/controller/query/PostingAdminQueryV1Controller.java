package com.inglo.giggle.posting.application.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.posting.application.dto.response.ReadAdminJobPostingsOverviewsResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadAdminJobPostingsOverviewsUseCase;
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

    private final ReadAdminJobPostingsOverviewsUseCase readAdminJobPostingsOverviewsUseCase;

    /**
     * 4.14 (어드민) 공고 등록 수 조회하기
     */
    @GetMapping("/summaries")
    public ResponseDto<ReadAdminJobPostingsOverviewsResponseDto> readAdminJobPostingsOverviews(
            @RequestParam("start_date") String stringStartDate,
            @RequestParam("end_date") String stringEndDate,
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(
                readAdminJobPostingsOverviewsUseCase.execute(
                        accountId,
                        stringStartDate,
                        stringEndDate
                )
        );
    }
}

