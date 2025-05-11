package com.inglo.giggle.term.adapter.in.web;

import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.term.application.port.in.result.ReadTermDetailResult;
import com.inglo.giggle.term.application.port.in.query.ReadTermDetailQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class TermQueryV1Controller {

    private final ReadTermDetailQuery readTermDetailQuery;

    /**
     * 11.1 약관 종류별 상세조회하기
     */
    @GetMapping("/terms/{termType}/details")
    public ResponseDto<ReadTermDetailResult> readTermDetail(
            @PathVariable String termType
    ) {
        return ResponseDto.ok(readTermDetailQuery.execute(termType));
    }
}
