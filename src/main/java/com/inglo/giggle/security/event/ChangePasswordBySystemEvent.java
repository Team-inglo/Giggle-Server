package com.inglo.giggle.security.event;

import lombok.Builder;

@Builder
public record ChangePasswordBySystemEvent(
        String receiverAddress,
        String temporaryPassword
) {
}
