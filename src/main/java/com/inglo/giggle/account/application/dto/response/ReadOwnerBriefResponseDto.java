package com.inglo.giggle.account.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadOwnerBriefResponseDto extends SelfValidating<ReadOwnerBriefResponseDto> {

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
    public ReadOwnerBriefResponseDto(
            String iconImgUrl,
            String companyName,
            Boolean isNotificationAllowed
    ) {
        this.iconImgUrl = iconImgUrl;
        this.companyName = companyName;
        this.isNotificationAllowed = isNotificationAllowed;

        this.validateSelf();
    }

    public static ReadOwnerBriefResponseDto fromEntity(Owner owner) {
        return ReadOwnerBriefResponseDto.builder()
                .iconImgUrl(owner.getProfileImgUrl())
                .companyName(owner.getCompanyName())
                .isNotificationAllowed(owner.getNotificationAllowed())
                .build();
    }
}
