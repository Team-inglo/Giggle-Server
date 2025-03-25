package com.inglo.giggle.document.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.document.application.dto.response.ReadOwnerDocumentSummaryResponseDto;
import com.inglo.giggle.document.application.usecase.ReadOwnerDocumentSummaryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/owners")
public class DocumentOwnersQueryV1Controller {
    private final ReadOwnerDocumentSummaryUseCase readOwnerDocumentSummaryUseCase;

    /**
     * 8.2 (고용주) 서류 조회하기
     */
    @GetMapping("/user-owner-job-postings/{id}/documents/summaries")
    public ResponseDto<ReadOwnerDocumentSummaryResponseDto> readOwnerDocumentSummary(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readOwnerDocumentSummaryUseCase.execute(accountId, id));
    }

}
