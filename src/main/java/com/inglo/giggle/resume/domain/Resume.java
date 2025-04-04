package com.inglo.giggle.resume.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Resume extends BaseDomain {

    private UUID accountId;
    private String introduction;

    @Builder
    public Resume(UUID accountId, String introduction) {
        this.accountId = accountId;
        this.introduction = introduction;
    }

    public void checkValidation(UUID accountId) {
        if (!this.accountId.equals(accountId)) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    public void clearIntroduction() {
        this.introduction = null;
    }

    public void updateIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
