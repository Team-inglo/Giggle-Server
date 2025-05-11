package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.application.port.in.command.DeleteAdminBannerCommand;
import com.inglo.giggle.banner.application.port.in.usecase.DeleteAdminBannerUseCase;
import com.inglo.giggle.banner.application.port.out.DeleteBannerPort;
import com.inglo.giggle.banner.application.port.out.LoadBannerPort;
import com.inglo.giggle.banner.domain.Banner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteAdminBannerService implements DeleteAdminBannerUseCase {

    private final LoadBannerPort loadBannerPort;
    private final DeleteBannerPort deleteBannerPort;

    @Override
    @Transactional
    public void execute(DeleteAdminBannerCommand command) {

        Banner banner = loadBannerPort.loadBanner(command.getBannerId());
        deleteBannerPort.deleteBanner(banner);
    }
}
