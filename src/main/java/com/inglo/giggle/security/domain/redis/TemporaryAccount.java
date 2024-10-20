package com.inglo.giggle.security.domain.redis;

import com.inglo.giggle.security.domain.type.ESecurityRole;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "temporary_account", timeToLive = 60 * 30) // 30분
public class TemporaryAccount {
    @Id
    private String compositeKey;

    private final String password;

    @Column(name = "account_type")
    private final ESecurityRole accountType;

    @Builder
    public TemporaryAccount(String id, String email, String password, ESecurityRole accountType) {
        this.compositeKey = id + ":" + email;
        this.password = password;
        this.accountType = accountType;
    }

    public String getId() {
        return compositeKey.split(":")[0];
    }

    public String getEmail() {
        return compositeKey.split(":")[1];
    }
}