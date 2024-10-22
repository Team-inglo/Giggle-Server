package com.inglo.giggle.security.event;

import lombok.Builder;

@Builder
public record ChangePasswordBySystemEvent(
        String receiverAddress,
        String temporaryPassword
) {
    public static ChangePasswordBySystemEvent of(String receiverAddress, String temporaryPassword) {
        return ChangePasswordBySystemEvent.builder()
                .receiverAddress(receiverAddress)
                .temporaryPassword(temporaryPassword)
                .build();
    }
}
