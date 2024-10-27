package com.inglo.giggle.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RouteResponseDto(
        @NotNull
        @JsonProperty("code")
        String code,

        @NotNull
        @JsonProperty("routes")
        List<RouteDto> routes
) {
    public record RouteDto(
            @NotNull
            @JsonProperty("geometry")
            String geometry,

            @NotNull
            @JsonProperty("legs")
            List<LegDto> legs,

            @NotNull
            @JsonProperty("distance")
            Double distance,

            @NotNull
            @JsonProperty("duration")
            Double duration,

            @NotNull
            @JsonProperty("weight_name")
            String weightName,

            @NotNull
            @JsonProperty("weight")
            Double weight
    ) {
    }

    public record LegDto(
            @NotNull
            @JsonProperty("distance")
            Double distance,

            @NotNull
            @JsonProperty("duration")
            Double duration,

            @NotNull
            @JsonProperty("summary")
            String summary,

            @NotNull
            @JsonProperty("weight")
            Double weight
    ) {
    }
}
