package com.inglo.giggle.document.appliation.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.document.appliation.dto.response.ReadUserDocumentSummaryResponseDto;
import com.inglo.giggle.document.appliation.usecase.ReadUserDocumentSummaryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DocumentUsersQueryV1Controller {
    private final ReadUserDocumentSummaryUseCase readUserDocumentSummaryUseCase;

    /**
     * 8.1 (유학생) 서류 조회하기
     */
    @GetMapping("/v1/users/user-owner-job-postings/{id}/documents/summaries")
    public ResponseDto<ReadUserDocumentSummaryResponseDto> readUserDocumentSummary(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readUserDocumentSummaryUseCase.readUserDocumentSummary(accountId, id));
    }

}
