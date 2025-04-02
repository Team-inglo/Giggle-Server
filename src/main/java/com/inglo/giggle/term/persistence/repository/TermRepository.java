package com.inglo.giggle.term.persistence.repository;

import com.inglo.giggle.term.domain.Term;
import com.inglo.giggle.term.domain.type.ETermType;

import java.util.List;

public interface TermRepository  {

    Term findTopByTermTypeOrderByCreatedAtDescOrElseThrow(ETermType termType);

    List<Term> findLatestTermsByTermType(List<ETermType> termTypes);

    Term save(Term term);
}
