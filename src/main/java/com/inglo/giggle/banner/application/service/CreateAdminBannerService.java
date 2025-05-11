package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.application.port.in.command.CreateAdminBannerCommand;
import com.inglo.giggle.banner.application.port.in.usecase.CreateAdminBannerUseCase;
import com.inglo.giggle.banner.application.port.out.CreateBannerPort;
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
public class CreateAdminBannerService implements CreateAdminBannerUseCase {

    private final CreateBannerPort createBannerPort;

    private final ReadAccountDetailQuery readAccountDetailQuery;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(CreateAdminBannerCommand command) {

        // Account 조회
        ReadAccountDetailResult readAccountDetailResult = readAccountDetailQuery.execute(command.getAccountId());

        String imgUrl = s3Util.uploadImageFile(command.getImage(), readAccountDetailResult.getSerialId(), EImageType.BANNER_IMG);

        Banner banner = Banner.builder()
                .title(command.getTitle())
                .imgUrl(imgUrl)
                .content(command.getContent())
                .role(command.getRole())
                .build();

        createBannerPort.createBanner(banner);
    }
}
