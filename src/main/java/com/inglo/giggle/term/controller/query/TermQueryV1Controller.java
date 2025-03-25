package com.inglo.giggle.term.controller.query;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.term.application.dto.response.ReadTermDetailResponseDto;
import com.inglo.giggle.term.application.usecase.ReadTermDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class TermQueryV1Controller {

    private final ReadTermDetailUseCase readTermDetailUseCase;

    /**
     * 11.1 약관 종류별 상세조회하기
     */
    @GetMapping("/terms/{termType}/details")
    public ResponseDto<ReadTermDetailResponseDto> readTermDetail(
            @PathVariable String termType
    ) {
        return ResponseDto.ok(readTermDetailUseCase.execute(termType));
    }
}
