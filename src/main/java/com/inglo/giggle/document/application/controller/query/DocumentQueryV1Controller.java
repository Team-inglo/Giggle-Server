package com.inglo.giggle.document.application.controller.query;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.document.application.dto.response.ReadDocumentPartTimeEmploymentPermitDetailResponseDto;
import com.inglo.giggle.document.application.usecase.ReadDocumentPartTimeEmploymentPermitDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/documents")
public class DocumentQueryV1Controller {
    private final ReadDocumentPartTimeEmploymentPermitDetailUseCase readDocumentPartTimeEmploymentPermitDetailUseCase;

    @GetMapping("{id}/part-time-employment-permit/details")
    public ResponseDto<ReadDocumentPartTimeEmploymentPermitDetailResponseDto> readDocumentPartTimeEmploymentPermitDetail(@PathVariable Long id) {
        return ResponseDto.ok(readDocumentPartTimeEmploymentPermitDetailUseCase.execute(id));
    }
}
