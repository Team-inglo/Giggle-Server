package com.inglo.giggle.term.repository;

import com.inglo.giggle.term.domain.Term;
import com.inglo.giggle.term.domain.type.ETermType;

import java.util.List;

public interface TermRepository  {

    Term findByIdOrElseThrow(Long id);

    Term findTopByTermTypeOrderByCreatedAtDescOrElseThrow(ETermType termType);

    List<Term> findLatestTermsByTermType(List<ETermType> termTypes);

    void save(Term term);

    void delete(Term term);
}
