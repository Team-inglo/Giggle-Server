package com.inglo.giggle.posting.application.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadOwnerJobPostingOverviewsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class PostingOwnersQueryV1Controller {

    private final ReadOwnerJobPostingOverviewsUseCase readOwnerJobPostingOverviewsUseCase;

    /**
     * 6.6 (고용주) 등록한 공고 리스트 조회하기
     */
    @GetMapping("/owners/job-postings/overviews")
    public ResponseDto<?> readOwnerJobPostingList(
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



}
