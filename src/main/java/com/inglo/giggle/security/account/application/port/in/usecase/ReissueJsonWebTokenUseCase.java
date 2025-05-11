package com.inglo.giggle.security.account.application.port.in.usecase;

import com.inglo.giggle.security.account.application.port.in.command.ReissueJsonWebTokenCommand;
import com.inglo.giggle.security.account.application.port.in.result.ReissueJsonWebTokenResult;

public interface ReissueJsonWebTokenUseCase {

    /**
     * Refresh Token을 이용하여 새로운 JsonWebToken을 발급하는 유스케이스
     */
    ReissueJsonWebTokenResult execute(ReissueJsonWebTokenCommand command);
}
