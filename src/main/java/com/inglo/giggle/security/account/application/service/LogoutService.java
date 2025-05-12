package com.inglo.giggle.security.account.application.service;

import com.inglo.giggle.security.account.application.port.in.usecase.LogoutUseCase;
import com.inglo.giggle.security.account.application.port.out.DeleteRefreshTokenPort;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import com.inglo.giggle.security.account.application.port.out.UpdateAccountDevicePort;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.info.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutUseCase {

    private final LoadAccountPort loadAccountPort;
    private final DeleteRefreshTokenPort deleteRefreshTokenPort;
    private final UpdateAccountDevicePort updateAccountDevicePort;

    @Override
    @Transactional
    public void execute(CustomUserPrincipal principal) {

        // Account 조회
        Account account = loadAccountPort.loadAllAccountOrElseThrow(principal.getId());

        // Refresh Token 삭제
        deleteRefreshTokenPort.deleteRefreshToken(account.getRefreshToken());

        // AccountDevice 삭제
        account.deleteAllAccountDevices();
        updateAccountDevicePort.updateAccountDevice(account);
    }
}
