package com.inglo.giggle.term.application.service;

import com.inglo.giggle.term.application.port.in.query.ReadTermDetailQuery;
import com.inglo.giggle.term.application.port.in.result.ReadTermDetailResult;
import com.inglo.giggle.term.application.port.out.LoadTermPort;
import com.inglo.giggle.term.domain.Term;
import com.inglo.giggle.term.domain.type.ETermType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadTermDetailService implements ReadTermDetailQuery {

    private final LoadTermPort loadTermPort;

    @Override
    public ReadTermDetailResult execute(String termType) {

        // 약관 타입 파싱
        ETermType eTermType = ETermType.fromString(termType);

        // 약관 상세정보 조회
        Term term = loadTermPort.loadTermOrElseThrow(eTermType);

        return ReadTermDetailResult.of(term.getContent());
    }
}
