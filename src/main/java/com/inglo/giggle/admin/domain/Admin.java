package com.inglo.giggle.admin.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Admin extends BaseDomain {
    private UUID id;

    @Builder
    public Admin(
            UUID id
    ) {
        this.id = id;
    }

    public ESecurityRole getRole() {
        return ESecurityRole.ADMIN;
    }

    public String getName() {
        return "관리자";
    }
}

