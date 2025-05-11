package com.inglo.giggle.banner.application.port.in.usecase;

import com.inglo.giggle.banner.application.port.in.command.DeleteAdminBannerCommand;
import com.inglo.giggle.core.annotation.bean.UseCase;

@UseCase
public interface DeleteAdminBannerUseCase {

    /**
     * 12.9 (어드민) 배너 삭제하기 UseCase
     */
    void execute(DeleteAdminBannerCommand command);
}
