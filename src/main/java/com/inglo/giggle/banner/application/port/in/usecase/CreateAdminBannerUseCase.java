package com.inglo.giggle.banner.application.port.in.usecase;

import com.inglo.giggle.banner.application.port.in.command.CreateAdminBannerCommand;
import com.inglo.giggle.core.annotation.bean.UseCase;

@UseCase
public interface CreateAdminBannerUseCase {

    /**
     * 12.7 (어드민) 배너 생성하기 UseCase
     */
    void execute(CreateAdminBannerCommand command);
}
