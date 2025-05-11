package com.inglo.giggle.document.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateDocumentStatusRequestRequestDto(

        @JsonProperty("reason")
        String reason
) {
}
