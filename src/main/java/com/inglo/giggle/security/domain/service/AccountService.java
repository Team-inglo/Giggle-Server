package com.inglo.giggle.security.domain.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.presentation.dto.request.SignUpDefaultTemporaryRequestDto;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import com.inglo.giggle.security.info.CustomUserPrincipal;
import com.inglo.giggle.security.persistence.entity.mysql.AccountEntity;
import com.inglo.giggle.security.persistence.entity.redis.TemporaryAccountEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    public TemporaryAccountEntity createTemporaryAccount(SignUpDefaultTemporaryRequestDto requestDto) {
        return TemporaryAccountEntity.builder()
                .email(requestDto.email())
                .password(requestDto.password())
                .accountType(ESecurityRole.fromString(requestDto.accountType()))
                .build();
    }

    public void changePassword(Account account, String newPassword) {
        account.updatePassword(newPassword);
    }

    public CustomUserPrincipal createCustomUserPrincipalByAccount(Account account) {
        return CustomUserPrincipal.create(account);
    }

    public void checkUserValidation(AccountEntity accountEntity) {
        if (!accountEntity.getRole().equals(ESecurityRole.USER))
            throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
    }

    public void checkOwnerValidation(AccountEntity accountEntity) {
        if (!accountEntity.getRole().equals(ESecurityRole.OWNER))
            throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
    }
}
