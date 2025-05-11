package com.inglo.giggle.admin.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.CountInfoDto;
import com.inglo.giggle.core.dto.SelfValidating;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadAdminAccountWithdrawalOverviewResponseDto extends SelfValidating<ReadAdminAccountWithdrawalOverviewResponseDto> {
    @JsonProperty("user_withdrawal_info")
    private final CountInfoDto userWithdrawalInfo;

    @JsonProperty("accumulated_user_withdrawal_info")
    private final CountInfoDto accumulatedUserWithdrawalInfo;

    @JsonProperty("owner_withdrawal_info")
    private final CountInfoDto ownerWithdrawalInfo;

    @JsonProperty("accumulated_owner_withdrawal_info")
    private final CountInfoDto accumulatedOwnerWithdrawalInfo;

    @Builder
    public ReadAdminAccountWithdrawalOverviewResponseDto(CountInfoDto userWithdrawalInfo, CountInfoDto accumulatedUserWithdrawalInfo, CountInfoDto ownerWithdrawalInfo, CountInfoDto accumulatedOwnerWithdrawalInfo) {
        this.userWithdrawalInfo = userWithdrawalInfo;
        this.accumulatedUserWithdrawalInfo = accumulatedUserWithdrawalInfo;
        this.ownerWithdrawalInfo = ownerWithdrawalInfo;
        this.accumulatedOwnerWithdrawalInfo = accumulatedOwnerWithdrawalInfo;
        this.validateSelf();
    }

    public static ReadAdminAccountWithdrawalOverviewResponseDto of(CountInfoDto userWithdrawalInfo, CountInfoDto accumulatedUserWithdrawalInfo, CountInfoDto ownerWithdrawalInfo, CountInfoDto accumulatedOwnerWithdrawalInfo) {
        return ReadAdminAccountWithdrawalOverviewResponseDto.builder()
                .userWithdrawalInfo(userWithdrawalInfo)
                .accumulatedUserWithdrawalInfo(accumulatedUserWithdrawalInfo)
                .ownerWithdrawalInfo(ownerWithdrawalInfo)
                .accumulatedOwnerWithdrawalInfo(accumulatedOwnerWithdrawalInfo)
                .build();
    }
}
