package com.inglo.giggle.document.adapter.in.web;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.document.application.port.in.query.ReadUserDocumentSummaryQuery;
import com.inglo.giggle.document.application.port.in.result.ReadUserDocumentSummaryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class DocumentUsersQueryV1Controller {
    private final ReadUserDocumentSummaryQuery readUserDocumentSummaryQuery;

    /**
     * 8.1 (유학생) 서류 조회하기
     */
    @GetMapping("/user-owner-job-postings/{id}/documents/summaries")
    public ResponseDto<ReadUserDocumentSummaryResult> readUserDocumentSummary(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readUserDocumentSummaryQuery.readUserDocumentSummary(accountId, id));
    }

}
