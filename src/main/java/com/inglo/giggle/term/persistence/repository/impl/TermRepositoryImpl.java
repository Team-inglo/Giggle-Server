package com.inglo.giggle.term.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.term.domain.Term;
import com.inglo.giggle.term.persistence.entity.TermEntity;
import com.inglo.giggle.term.domain.type.ETermType;
import com.inglo.giggle.term.persistence.mapper.TermMapper;
import com.inglo.giggle.term.persistence.repository.TermRepository;
import com.inglo.giggle.termEntity.persistence.repository.mysql.TermJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TermRepositoryImpl implements TermRepository {

    private final TermJpaRepository termJpaRepository;

    @Override
    public Term findTopByTermTypeOrderByCreatedAtDescOrElseThrow(ETermType termType) {
        return TermMapper.toDomain(termJpaRepository.findTopByTermTypeOrderByCreatedAtDesc(termType)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TERM)));
    }


    @Override
    public List<Term> findLatestTermsByTermType(List<ETermType> termTypes) {
        return TermMapper.toDomains(termJpaRepository.findLatestTermsByTermType(termTypes));
    }

    @Override
    public Term save(Term term) {
        TermEntity entity = termJpaRepository.save(TermMapper.toEntity(term));
        return TermMapper.toDomain(entity);
    }

}
