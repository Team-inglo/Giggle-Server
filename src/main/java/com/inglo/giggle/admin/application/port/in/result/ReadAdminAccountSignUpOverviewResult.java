package com.inglo.giggle.admin.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.CountInfoDto;
import com.inglo.giggle.core.dto.SelfValidating;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadAdminAccountSignUpOverviewResult extends SelfValidating<ReadAdminAccountSignUpOverviewResult> {

    @JsonProperty("user_sign_up_info")
    private final CountInfoDto userSignUpInfo;

    @JsonProperty("accumulated_user_sign_up_info")
    private final CountInfoDto accumulatedUserSignUpInfo;

    @JsonProperty("owner_sign_up_info")
    private final CountInfoDto ownerSignUpInfo;

    @JsonProperty("accumulated_owner_sign_up_info")
    private final CountInfoDto accumulatedOwnerSignUpInfo;

    @Builder
    public ReadAdminAccountSignUpOverviewResult(CountInfoDto userSignUpInfo, CountInfoDto accumulatedUserSignUpInfo, CountInfoDto ownerSignUpInfo, CountInfoDto accumulatedOwnerSignUpInfo) {
        this.userSignUpInfo = userSignUpInfo;
        this.accumulatedUserSignUpInfo = accumulatedUserSignUpInfo;
        this.ownerSignUpInfo = ownerSignUpInfo;
        this.accumulatedOwnerSignUpInfo = accumulatedOwnerSignUpInfo;
        this.validateSelf();
    }

    public static ReadAdminAccountSignUpOverviewResult of(CountInfoDto userSignUpInfo, CountInfoDto accumulatedUserSignUpInfo, CountInfoDto ownerSignUpInfo, CountInfoDto accumulatedOwnerSignUpInfo) {
        return ReadAdminAccountSignUpOverviewResult.builder()
                .userSignUpInfo(userSignUpInfo)
                .accumulatedUserSignUpInfo(accumulatedUserSignUpInfo)
                .ownerSignUpInfo(ownerSignUpInfo)
                .accumulatedOwnerSignUpInfo(accumulatedOwnerSignUpInfo)
                .build();
    }
}
