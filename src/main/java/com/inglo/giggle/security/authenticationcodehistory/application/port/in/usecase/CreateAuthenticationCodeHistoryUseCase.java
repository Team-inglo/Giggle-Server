package com.inglo.giggle.security.authenticationcodehistory.application.port.in.usecase;

import com.inglo.giggle.security.authenticationcodehistory.application.port.in.command.CreateAuthenticationCodeHistoryCommand;

public interface CreateAuthenticationCodeHistoryUseCase {

    void execute(CreateAuthenticationCodeHistoryCommand command);
}
