package com.inglo.giggle.security.domain.redis;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "authentication_code", timeToLive = 60 * 5) // 5분
public class AuthenticationCode {
    @Id
    private String email;

    private String value;

    @Builder
    public AuthenticationCode(String email, String value) {
        this.email = email;
        this.value = value;
    }
}
