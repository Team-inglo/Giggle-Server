package com.inglo.giggle.version.application.port.in.result;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.version.domain.Version;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReadRecentVersionResult extends SelfValidating<ReadRecentVersionResult> {

    @NotNull(message = "major는 null이 될 수 없습니다.")
    private final Integer major;

    @NotNull(message = "minor는 null이 될 수 없습니다.")
    private final Integer minor;

    @NotNull(message = "patch는 null이 될 수 없습니다.")
    private final Integer patch;

    @Builder
    public ReadRecentVersionResult(
            Integer major,
            Integer minor,
            Integer patch
    ) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;

        this.validateSelf();
    }

    public static ReadRecentVersionResult from(
            Version version
    ){
        return ReadRecentVersionResult.builder()
                .major(version.getMajor())
                .minor(version.getMinor())
                .patch(version.getPatch())
                .build();
    }

}
