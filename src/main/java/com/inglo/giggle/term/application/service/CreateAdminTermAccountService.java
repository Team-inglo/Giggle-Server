package com.inglo.giggle.term.application.service;

import com.inglo.giggle.term.application.dto.request.CreateAdminTermAccountRequestDto;
import com.inglo.giggle.term.application.usecase.CreateAdminTermAccountUseCase;
import com.inglo.giggle.term.domain.Term;
import com.inglo.giggle.term.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateAdminTermAccountService implements CreateAdminTermAccountUseCase {

    private final TermRepository termRepository;

    @Override
    @Transactional
    public void execute(CreateAdminTermAccountRequestDto requestDto) {
        Term term = Term.builder()
                .version(requestDto.version())
                .content(requestDto.content())
                .termType(requestDto.termType())
                .build();

        termRepository.save(term);
    }
}
