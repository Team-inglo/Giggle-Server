package com.inglo.giggle.term.application.service;

import com.inglo.giggle.term.application.port.in.query.ReadTermsByTypesQuery;
import com.inglo.giggle.term.application.port.in.result.ReadTermsByTypesResult;
import com.inglo.giggle.term.application.port.out.LoadTermPort;
import com.inglo.giggle.term.domain.type.ETermType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadTermByTypesService implements ReadTermsByTypesQuery {

    private final LoadTermPort loadTermPort;

    @Override
    public ReadTermsByTypesResult execute(List<ETermType> termTypes) {
        List<ReadTermsByTypesResult.TermInfo> terms = loadTermPort.loadTerms(termTypes)
                .stream()
                .map(term -> ReadTermsByTypesResult.TermInfo.of(
                        term.getId(),
                        term.getContent(),
                        term.getTermType(),
                        term.getVersion()
                ))
                .toList();

        return ReadTermsByTypesResult.of(terms);
    }
}
