package com.inglo.giggle.security.persistence.entity.redis;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refresh_token", timeToLive = 60 * 60 * 24 * 14) // 14일
public class RefreshTokenEntity {

    @Id
    @Indexed
    private UUID accountId;

    @Indexed
    private String value;

    @Builder
    public RefreshTokenEntity(UUID accountId, String value) {
        this.accountId = accountId;
        this.value = value;
    }
}
