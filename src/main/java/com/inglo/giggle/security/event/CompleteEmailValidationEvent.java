package com.inglo.giggle.security.event;

import lombok.Builder;

@Builder
public record CompleteEmailValidationEvent(
        String receiverAddress,
        String authenticationCode
) {
}
