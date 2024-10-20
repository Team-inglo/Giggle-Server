package com.inglo.giggle.security.domain.redis;

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
public class TemporaryToken {
    @Id
    private String compositeKey;

    @Indexed
    private String value;

    @Builder
    public TemporaryToken(String id, String email, String value) {
        this.compositeKey = id + ":" + email;
        this.value = value;
    }
    public String getId() {
        return compositeKey.split(":")[0];
    }
    public String getEmail() {
        return compositeKey.split(":")[1];
    }
}