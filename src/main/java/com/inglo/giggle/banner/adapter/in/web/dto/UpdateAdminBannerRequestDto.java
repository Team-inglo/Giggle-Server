package com.inglo.giggle.banner.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;

public record UpdateAdminBannerRequestDto (

        @JsonProperty("title")
        String title,

        @JsonProperty("content")
        String content,

        @JsonProperty("role")
        ESecurityRole role
) {
}
