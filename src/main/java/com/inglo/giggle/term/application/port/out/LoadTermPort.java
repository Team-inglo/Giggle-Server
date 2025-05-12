package com.inglo.giggle.term.application.port.out;

import com.inglo.giggle.term.domain.Term;
import com.inglo.giggle.term.domain.type.ETermType;

import java.util.List;

public interface LoadTermPort {

    Term loadTermOrElseThrow(ETermType termType);

    List<Term> loadTerms(List<ETermType> termTypes);
}
