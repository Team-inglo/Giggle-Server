package com.inglo.giggle.security.domain.redis;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refresh_token", timeToLive = 60 * 60 * 24 * 14) // 14일
public class RefreshToken {
    @Id
    @Column(name = "account_id")
    private UUID accountId;

    @Column(name = "value")
    private String value;

    @Builder
    public RefreshToken(UUID accountId, String value) {
        this.accountId = accountId;
        this.value = value;
    }
}
