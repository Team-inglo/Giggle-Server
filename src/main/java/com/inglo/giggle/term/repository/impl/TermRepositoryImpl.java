package com.inglo.giggle.term.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.term.domain.Term;
import com.inglo.giggle.term.domain.type.ETermType;
import com.inglo.giggle.term.repository.TermRepository;
import com.inglo.giggle.term.repository.mysql.TermJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TermRepositoryImpl implements TermRepository {

    private final TermJpaRepository termJpaRepository;

    @Override
    public Term findByIdOrElseThrow(Long id) {
        return termJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TERM));
    }

    @Override
    public Term findTopByTermTypeOrderByCreatedAtDescOrElseThrow(ETermType termType) {
        return termJpaRepository.findTopByTermTypeOrderByCreatedAtDesc(termType)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TERM));
    }


    @Override
    public List<Term> findLatestTermsByTermType(List<ETermType> termTypes) {
        return termJpaRepository.findLatestTermsByTermType(termTypes);
    }

    @Override
    public void save(Term term) {
        termJpaRepository.save(term);
    }

    @Override
    public void delete(Term term) {
        termJpaRepository.delete(term);
    }

}
