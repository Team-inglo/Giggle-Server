package com.inglo.giggle.security.authenticationcodehistory.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AuthenticationCodeHistory {

    private String email;

    private Integer count;

    private LocalDateTime lastSentAt;

    @Builder
    public AuthenticationCodeHistory(
            String email,
            Integer count,
            LocalDateTime lastSentAt
    ) {
        this.email = email;
        this.count = count;

        this.lastSentAt = lastSentAt;
    }

    public void incrementCount() {
        this.count++;
        this.lastSentAt = LocalDateTime.now();
    }
}
