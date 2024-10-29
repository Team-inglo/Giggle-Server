package com.inglo.giggle.document.application.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.document.application.dto.response.ReadUserDocumentSummaryResponseDto;
import com.inglo.giggle.document.application.usecase.ReadOwnerDocumentSummaryUseCase;
import com.inglo.giggle.document.application.usecase.ReadUserDocumentSummaryUseCase;
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
    private final ReadUserDocumentSummaryUseCase readUserDocumentSummaryUseCase;
    private final ReadOwnerDocumentSummaryUseCase readOwnerDocumentSummaryUseCase;

    /**
     * 8.1 (유학생) 서류 조회하기
     */
    @GetMapping("/user-owner-job-postings/{id}/documents/summaries")
    public ResponseDto<ReadUserDocumentSummaryResponseDto> readUserDocumentSummary(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readUserDocumentSummaryUseCase.readUserDocumentSummary(accountId, id));
    }

}
