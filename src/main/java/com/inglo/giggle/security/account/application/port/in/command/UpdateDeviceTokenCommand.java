package com.inglo.giggle.security.account.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateDeviceTokenCommand extends SelfValidating<UpdateDeviceTokenCommand> {

    @NotNull(message = "accountId는 null일 수 없습니다.")
    private final UUID accountId;

    @NotBlank(message = "deviceToken은 null일 수 없습니다.")
    private final String deviceToken;

    @NotBlank(message = "deviceId는 null일 수 없습니다.")
    private final String deviceId;

    public UpdateDeviceTokenCommand(
            UUID accountId,
            String deviceToken,
            String deviceId
    ) {
        this.accountId = accountId;
        this.deviceToken = deviceToken;
        this.deviceId = deviceId;

        this.validateSelf();
    }
}
