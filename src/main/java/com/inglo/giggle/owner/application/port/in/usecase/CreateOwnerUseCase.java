package com.inglo.giggle.owner.application.port.in.usecase;

import com.inglo.giggle.owner.application.port.in.command.CreateOwnerCommand;

public interface CreateOwnerUseCase {
    void execute(CreateOwnerCommand command);
}
