package com.inglo.giggle.term.application.port.in.query;

import com.inglo.giggle.term.application.port.in.result.ReadTermsByTypesResult;
import com.inglo.giggle.term.domain.type.ETermType;

import java.util.List;

public interface ReadTermsByTypesQuery {

    ReadTermsByTypesResult execute(List<ETermType> termTypes);
}
