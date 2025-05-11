package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.application.port.in.command.UpdateAdminBannerCommand;
import com.inglo.giggle.banner.application.port.in.usecase.UpdateAdminBannerUseCase;
import com.inglo.giggle.banner.application.port.out.LoadBannerPort;
import com.inglo.giggle.banner.application.port.out.UpdateBannerPort;
import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccountDetailQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountDetailResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateAdminBannerService implements UpdateAdminBannerUseCase {

    private final LoadBannerPort loadBannerPort;
    private final UpdateBannerPort updateBannerPort;

    private final ReadAccountDetailQuery readAccountDetailQuery;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(UpdateAdminBannerCommand command) {

        // Account 조회
        ReadAccountDetailResult readAccountDetailResult = readAccountDetailQuery.execute(command.getAccountId());

        // Banner 조회
        Banner banner = loadBannerPort.loadBanner(command.getBannerId());

        if (command.getImage() != null) {
            String imgUrl = s3Util.uploadImageFile(command.getImage(), readAccountDetailResult.getSerialId(), EImageType.BANNER_IMG);
            banner.updateWithImgUrl(command.getTitle(), imgUrl, command.getContent(), command.getRole());
        } else {
            banner.updateWithoutImgUrl(command.getTitle(), command.getContent(), command.getRole());
        }
        updateBannerPort.updateBanner(banner);
    }
}
