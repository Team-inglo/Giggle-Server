package com.inglo.giggle.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RouteResponseDto(

        @JsonProperty("code")
        String code,

        @JsonProperty("routes")
        List<RouteDto> routes
) {
    public record RouteDto(
            @JsonProperty("geometry")
            String geometry,

            @JsonProperty("legs")
            List<LegDto> legs,

            @JsonProperty("distance")
            Double distance,

            @JsonProperty("duration")
            Double duration,

            @JsonProperty("weight_name")
            String weightName,

            @JsonProperty("weight")
            Double weight
    ) {
    }

    public record LegDto(

            @JsonProperty("steps")
            List<String> steps,

            @JsonProperty("distance")
            Double distance,

            @JsonProperty("duration")
            Double duration,

            @JsonProperty("summary")
            String summary,

            @JsonProperty("weight")
            Double weight
    ) {
    }
}
