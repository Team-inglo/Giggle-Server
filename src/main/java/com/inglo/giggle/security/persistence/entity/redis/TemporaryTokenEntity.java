package com.inglo.giggle.security.persistence.entity.redis;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "temporary_token", timeToLive = 60 * 30) // 30분
public class TemporaryTokenEntity {
    @Id
    private String email;

    @Indexed
    private String value;

    @Builder
    public TemporaryTokenEntity(String email, String value) {
        this.email = email;
        this.value = value;
    }
}