package com.inglo.giggle.security.account.application.port.in.usecase;

import com.inglo.giggle.security.account.application.port.in.command.UpdateDeviceTokenCommand;

public interface UpdateDeviceTokenUseCase {

    void execute(UpdateDeviceTokenCommand command);
}
