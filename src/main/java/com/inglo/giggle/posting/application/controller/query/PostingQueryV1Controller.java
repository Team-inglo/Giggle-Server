package com.inglo.giggle.posting.application.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.posting.application.usecase.ReadUserAppliedJobListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class PostingQueryV1Controller {

    private final ReadUserAppliedJobListUseCase readUserAppliedJobListUseCase;

    /**
     * 6.1 (유학생) 지원한 공고 리스트 조회하기
     */
    @GetMapping("/users/user-owner-job-postings/overviews")
    public ResponseDto<?> readUserAppliedJobList(
            @AccountID UUID accountId,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name= "sorting", defaultValue = "ASCENDING") String sort,
            @RequestParam(name = "status", defaultValue = "ALL", required = false) String status
    ) {
        return ResponseDto.ok(readUserAppliedJobListUseCase.execute(
                accountId,
                page,
                size,
                sort,
                status
        ));
    }
}
