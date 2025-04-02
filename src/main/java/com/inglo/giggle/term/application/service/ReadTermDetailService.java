package com.inglo.giggle.term.application.service;

import com.inglo.giggle.term.presentation.dto.response.ReadTermDetailResponseDto;
import com.inglo.giggle.term.application.usecase.ReadTermDetailUseCase;
import com.inglo.giggle.term.domain.Term;
import com.inglo.giggle.term.domain.type.ETermType;
import com.inglo.giggle.term.persistence.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadTermDetailService implements ReadTermDetailUseCase {

    private final TermRepository termRepository;

    @Override
    public ReadTermDetailResponseDto execute(String termType) {

        // 약관 타입 파싱
        ETermType eTermType = ETermType.fromString(termType);

        // 약관 상세정보 조회
        Term term = termRepository.findTopByTermTypeOrderByCreatedAtDescOrElseThrow(eTermType);

        return ReadTermDetailResponseDto.fromEntity(term);
    }
}
