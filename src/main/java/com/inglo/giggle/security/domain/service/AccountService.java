package com.inglo.giggle.security.domain.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.application.dto.request.SignUpDefaultTemporaryRequestDto;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.redis.TemporaryAccount;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import com.inglo.giggle.security.info.CustomUserPrincipal;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    public TemporaryAccount createTemporaryAccount(SignUpDefaultTemporaryRequestDto requestDto) {
        return TemporaryAccount.builder()
                .id(requestDto.id())
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

    public void validateAccountIsOwner(Account account) {
        if(!account.getRole().equals(ESecurityRole.OWNER)){
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }
    }

}
