package com.inglo.giggle.account.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.CountInfoDto;
import com.inglo.giggle.core.dto.SelfValidating;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadAdminAccountSignUpOverviewResponseDto extends SelfValidating<ReadAdminAccountSignUpOverviewResponseDto> {

    @JsonProperty("user_sign_up_info")
    private final CountInfoDto userSignUpInfo;

    @JsonProperty("accumulated_user_sign_up_info")
    private final CountInfoDto accumulatedUserSignUpInfo;

    @JsonProperty("owner_sign_up_info")
    private final CountInfoDto ownerSignUpInfo;

    @JsonProperty("accumulated_owner_sign_up_info")
    private final CountInfoDto accumulatedOwnerSignUpInfo;

    @Builder
    public ReadAdminAccountSignUpOverviewResponseDto(CountInfoDto userSignUpInfo, CountInfoDto accumulatedUserSignUpInfo, CountInfoDto ownerSignUpInfo, CountInfoDto accumulatedOwnerSignUpInfo) {
        this.userSignUpInfo = userSignUpInfo;
        this.accumulatedUserSignUpInfo = accumulatedUserSignUpInfo;
        this.ownerSignUpInfo = ownerSignUpInfo;
        this.accumulatedOwnerSignUpInfo = accumulatedOwnerSignUpInfo;
        this.validateSelf();
    }

    public static ReadAdminAccountSignUpOverviewResponseDto of(CountInfoDto userSignUpInfo, CountInfoDto accumulatedUserSignUpInfo, CountInfoDto ownerSignUpInfo, CountInfoDto accumulatedOwnerSignUpInfo) {
        return ReadAdminAccountSignUpOverviewResponseDto.builder()
                .userSignUpInfo(userSignUpInfo)
                .accumulatedUserSignUpInfo(accumulatedUserSignUpInfo)
                .ownerSignUpInfo(ownerSignUpInfo)
                .accumulatedOwnerSignUpInfo(accumulatedOwnerSignUpInfo)
                .build();
    }
}
