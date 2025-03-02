package com.inglo.giggle.banner.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface DeleteAdminBannerUseCase {
    void execute(UUID accountId, Long bannerId);
}
