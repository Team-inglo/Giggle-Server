package com.inglo.giggle.term.controller.query;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.term.application.dto.response.ReadTermDetailResponseDto;
import com.inglo.giggle.term.application.usecase.ReadTermDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
