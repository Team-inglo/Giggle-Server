package com.inglo.giggle.term.application.service;

import com.inglo.giggle.term.application.port.in.command.CreateAdminTermCommand;
import com.inglo.giggle.term.application.port.in.usecase.CreateAdminTermUseCase;
import com.inglo.giggle.term.application.port.out.CreateTermPort;
import com.inglo.giggle.term.domain.Term;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateAdminTermService implements CreateAdminTermUseCase {

    private final CreateTermPort createTermPort;

    @Override
    @Transactional
    public void execute(CreateAdminTermCommand command) {
        Term term = Term.builder()
                .version(command.getVersion())
                .content(command.getContent())
                .termType(command.getTermType())
                .build();

        createTermPort.createTerm(term);
    }
}
