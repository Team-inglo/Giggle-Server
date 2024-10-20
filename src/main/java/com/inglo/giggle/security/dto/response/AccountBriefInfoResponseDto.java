package com.inglo.giggle.security.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;

@Builder
public record AccountBriefInfoResponseDto(
        @JsonProperty("account_type")
        @Enumerated(EnumType.STRING)
        ESecurityRole accountType,

        @JsonProperty("name")
        String name
) {

}
