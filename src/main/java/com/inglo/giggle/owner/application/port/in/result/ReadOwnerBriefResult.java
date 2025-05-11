package com.inglo.giggle.owner.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadOwnerBriefResult extends SelfValidating<ReadOwnerBriefResult> {

    @NotBlank(message = "icon_img_url은 null일 수 없습니다.")
    @JsonProperty("icon_img_url")
    private final String iconImgUrl;

    @NotBlank(message = "company_name은 null일 수 없습니다.")
    @JsonProperty("company_name")
    private final String companyName;

    @NotNull(message = "is_notification_allowed는 null일 수 없습니다.")
    @JsonProperty("is_notification_allowed")
    private final Boolean isNotificationAllowed;

    @Builder
    public ReadOwnerBriefResult(
            String iconImgUrl,
            String companyName,
            Boolean isNotificationAllowed
    ) {
        this.iconImgUrl = iconImgUrl;
        this.companyName = companyName;
        this.isNotificationAllowed = isNotificationAllowed;

        this.validateSelf();
    }

    public static ReadOwnerBriefResult of(
            String iconImgUrl,
            String companyName,
            Boolean isNotificationAllowed
    ) {
        return ReadOwnerBriefResult.builder()
                .iconImgUrl(iconImgUrl)
                .companyName(companyName)
                .isNotificationAllowed(isNotificationAllowed)
                .build();
    }
}
