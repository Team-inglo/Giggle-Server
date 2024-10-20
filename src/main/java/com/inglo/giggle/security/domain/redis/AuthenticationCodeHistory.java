package com.inglo.giggle.security.domain.redis;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "authentication_code_history", timeToLive = 60 * 30) // 30분
public class AuthenticationCodeHistory {
    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "count")
    private Integer count;

    @Column(name = "last_sent_at")
    private LocalDateTime lastSentAt;

    @Builder
    public AuthenticationCodeHistory(
            String email,
            Integer count
    ) {
        this.email = email;
        this.count = count;

        this.lastSentAt = LocalDateTime.now();
    }

    public AuthenticationCodeHistory copyWith(Integer count) {
        return AuthenticationCodeHistory.builder()
                .email(this.email)
                .count(count)
                .build();
    }
}
