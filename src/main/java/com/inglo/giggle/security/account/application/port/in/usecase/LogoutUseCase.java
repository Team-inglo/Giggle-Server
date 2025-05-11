package com.inglo.giggle.security.account.application.port.in.usecase;

import com.inglo.giggle.security.info.CustomUserPrincipal;

public interface LogoutUseCase {

    /**
     * Security 단에서 사용되는 Logout 유스케이스
     * @param principal UserPrincipal
     */
    void execute(CustomUserPrincipal principal);
}

