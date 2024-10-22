package com.inglo.giggle.security.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.info.CustomUserPrincipal;

@UseCase
public interface LogoutUseCase {

    /**
     * Security 단에서 사용되는 Logout 유스케이스
     * @param principal UserPrincipal
     */
    void execute(CustomUserPrincipal principal);
}

