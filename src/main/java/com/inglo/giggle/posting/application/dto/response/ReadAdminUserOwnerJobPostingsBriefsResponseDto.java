package com.inglo.giggle.posting.application.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReadAdminUserOwnerJobPostingsBriefsResponseDto extends SelfValidating<ReadAdminUserOwnerJobPostingsBriefsResponseDto> {

    @NotNull
    private final ApplyInfoDto applyInfo;

    @NotNull
    private final AcceptPerRejectRatioDto acceptPerRejectRatio;


    @Builder
    public ReadAdminUserOwnerJobPostingsBriefsResponseDto(
            ApplyInfoDto applyInfo,
            AcceptPerRejectRatioDto acceptPerRejectRatio
    ) {
        this.applyInfo = applyInfo;
        this.acceptPerRejectRatio = acceptPerRejectRatio;
        this.validateSelf();
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ApplyInfoDto extends SelfValidating<ApplyInfoDto> {

        @NotNull
        private final Long count;

        @Builder
        public ApplyInfoDto(
                Long count
        ) {
            this.count = count;
            this.validateSelf();
        }

        public static ApplyInfoDto of(Long count) {
            return ApplyInfoDto.builder()
                    .count(count)
                    .build();
        }
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class AcceptPerRejectRatioDto extends SelfValidating<AcceptPerRejectRatioDto> {

        @NotNull
        private final Double accept;

        @NotNull
        private final Double reject;

        @Builder
        public AcceptPerRejectRatioDto(
                Double accept,
                Double reject
        ) {
            this.accept = accept;
            this.reject = reject;
            this.validateSelf();
        }

        public static AcceptPerRejectRatioDto of(Double accept, Double reject) {
            return AcceptPerRejectRatioDto.builder()
                    .accept(accept)
                    .reject(reject)
                    .build();
        }
    }

    public static ReadAdminUserOwnerJobPostingsBriefsResponseDto of(
            Long count,
            Double accept,
            Double reject
    ) {
        return ReadAdminUserOwnerJobPostingsBriefsResponseDto.builder()
                .applyInfo(
                        ApplyInfoDto.of(
                                count
                        )
                )
                .acceptPerRejectRatio(
                        AcceptPerRejectRatioDto.of(
                                accept,
                                reject
                        )
                )
                .build();
    }
}
