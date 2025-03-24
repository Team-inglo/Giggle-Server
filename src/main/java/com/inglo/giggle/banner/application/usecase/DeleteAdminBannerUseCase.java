package com.inglo.giggle.banner.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface DeleteAdminBannerUseCase {

    /**
     * 12.9 (어드민) 배너 삭제하기 UseCase
     */
    void execute(UUID accountId, Long bannerId);
}
