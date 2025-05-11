package com.inglo.giggle.banner.application.port.in.usecase;

import com.inglo.giggle.banner.application.port.in.command.UpdateAdminBannerCommand;
import com.inglo.giggle.core.annotation.bean.UseCase;

@UseCase
public interface UpdateAdminBannerUseCase {

    /**
     * 12.8 (어드민) 배너 수정하기 UseCase
     */
    void execute(UpdateAdminBannerCommand command);
}
