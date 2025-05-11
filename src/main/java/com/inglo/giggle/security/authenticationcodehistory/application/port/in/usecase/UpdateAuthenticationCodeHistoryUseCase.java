package com.inglo.giggle.security.authenticationcodehistory.application.port.in.usecase;

import com.inglo.giggle.security.authenticationcodehistory.application.port.in.command.UpdateAuthenticationCodeHistoryCommand;

public interface UpdateAuthenticationCodeHistoryUseCase {

    void execute(UpdateAuthenticationCodeHistoryCommand command);
}
