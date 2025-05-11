package com.inglo.giggle.term.application.port.in.usecase;

import com.inglo.giggle.term.application.port.in.command.CreateAdminTermCommand;

public interface CreateAdminTermUseCase {

    /**
     * 11.3 (어드민) 약관 생성하기 UseCase
     */
    void execute(CreateAdminTermCommand command);
}
