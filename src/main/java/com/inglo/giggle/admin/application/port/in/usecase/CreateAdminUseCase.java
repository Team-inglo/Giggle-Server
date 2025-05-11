package com.inglo.giggle.admin.application.port.in.usecase;

import com.inglo.giggle.admin.application.port.in.command.CreateAdminCommand;

public interface CreateAdminUseCase {

    void execute(CreateAdminCommand command);
}
