package com.inglo.giggle.security.account.application.port.in.usecase;

import com.inglo.giggle.security.account.application.port.in.command.CreateAccountCommand;

import java.util.UUID;

public interface CreateAccountUseCase {

    UUID execute(CreateAccountCommand command);
}
