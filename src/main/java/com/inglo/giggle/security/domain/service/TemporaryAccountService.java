package com.inglo.giggle.security.domain.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.persistence.entity.redis.TemporaryAccountEntity;
import org.springframework.stereotype.Service;

@Service
public class TemporaryAccountService {
    public void validateAccountTypeUser(TemporaryAccountEntity temporaryAccountEntity) {
        if (!temporaryAccountEntity.getAccountType().getSecurityName().equals("ROLE_USER"))
            throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
    }

    public void validateAccountTypeOwner(TemporaryAccountEntity temporaryAccountEntity) {
        if (!temporaryAccountEntity.getAccountType().getSecurityName().equals("ROLE_OWNER"))
            throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
    }
}
