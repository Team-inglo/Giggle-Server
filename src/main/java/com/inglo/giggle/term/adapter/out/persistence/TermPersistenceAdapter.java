package com.inglo.giggle.term.adapter.out.persistence;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.term.adapter.out.persistence.mapper.TermMapper;
import com.inglo.giggle.term.adapter.out.persistence.repository.mysql.TermJpaRepository;
import com.inglo.giggle.term.application.port.out.CreateTermPort;
import com.inglo.giggle.term.application.port.out.LoadTermPort;
import com.inglo.giggle.term.domain.Term;
import com.inglo.giggle.term.domain.type.ETermType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TermPersistenceAdapter implements LoadTermPort, CreateTermPort {

    private final TermJpaRepository termJpaRepository;
    private final TermMapper termMapper;

    @Override
    public Term loadTerm(ETermType termType) {
        return termMapper.toDomain(termJpaRepository.findTopByTermTypeOrderByCreatedAtDesc(termType)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TERM)));
    }


    @Override
    public List<Term> loadTerms(List<ETermType> termTypes) {
        return termJpaRepository.findLatestTermsByTermType(termTypes)
                .stream()
                .map(termMapper::toDomain)
                .toList();
    }

    @Override
    public void createTerm(Term term) {
        termJpaRepository.save(termMapper.toEntity(term));
    }

}
