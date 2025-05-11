package com.inglo.giggle.user.application.port.in.usecase;

import com.inglo.giggle.user.application.port.in.command.CreateUserCommand;

public interface CreateUserUseCase {

    void execute(CreateUserCommand command);
}
