package com.inglo.giggle.security.account.application.port.in.result;

import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.security.account.domain.type.ESecurityProvider;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class ReadAccountDetailResult extends SelfValidating<ReadAccountDetailResult> {

    @NotNull(message = "account_id는 null이 될 수 없습니다.")
    private final UUID accountId;

    @NotNull(message = "provider는 null이 될 수 없습니다.")
    private final ESecurityProvider provider;

    @NotNull(message = "role은 null이 될 수 없습니다.")
    private final ESecurityRole role;

    @NotNull(message = "serial_id는 null이 될 수 없습니다.")
    private final String serialId;

    private final List<String> deviceTokens;

    public ReadAccountDetailResult(
            UUID accountId,
            ESecurityProvider provider,
            ESecurityRole role,
            String serialId,
            List<String> deviceTokens
    ) {
        this.accountId = accountId;
        this.provider = provider;
        this.role = role;
        this.serialId = serialId;
        this.deviceTokens = deviceTokens;
        this.validateSelf();
    }
}
