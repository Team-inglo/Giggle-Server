package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.application.port.in.query.ReadGuestBannerOverviewQuery;
import com.inglo.giggle.banner.application.port.in.result.ReadGuestBannerOverviewResult;
import com.inglo.giggle.banner.application.port.out.LoadBannerPort;
import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadGuestBannerOverviewService implements ReadGuestBannerOverviewQuery {

    private final LoadBannerPort loadBannerPort;

    @Override
    @Transactional(readOnly = true)
    public ReadGuestBannerOverviewResult execute() {

        List<Banner> banners = loadBannerPort.loadBanners(ESecurityRole.USER);

        return ReadGuestBannerOverviewResult.from(banners);
    }
}
