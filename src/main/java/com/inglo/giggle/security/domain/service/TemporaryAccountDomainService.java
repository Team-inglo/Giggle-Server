package com.inglo.giggle.security.domain.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.domain.redis.TemporaryAccount;
import org.springframework.stereotype.Service;

@Service
public class TemporaryAccountDomainService {
    public void validateAccountTypeUser(TemporaryAccount temporaryAccount) {
        if (!temporaryAccount.getAccountType().getSecurityName().equals("ROLE_USER"))
            throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
    }

    public void validateAccountTypeOwner(TemporaryAccount temporaryAccount) {
        if (!temporaryAccount.getAccountType().getSecurityName().equals("ROLE_OWNER"))
            throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
    }
}
