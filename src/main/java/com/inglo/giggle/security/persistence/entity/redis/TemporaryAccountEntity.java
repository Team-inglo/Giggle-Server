package com.inglo.giggle.security.persistence.entity.redis;

import com.inglo.giggle.security.domain.type.ESecurityRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "temporary_account", timeToLive = 60 * 30) // 30분
public class TemporaryAccountEntity {
    @Id
    private String email;

    private String password;

    private ESecurityRole accountType;

    @Builder
    public TemporaryAccountEntity(String email, String password, ESecurityRole accountType) {
        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }
}