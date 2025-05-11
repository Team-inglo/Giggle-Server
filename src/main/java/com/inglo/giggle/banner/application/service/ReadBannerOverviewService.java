package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.application.port.in.query.ReadBannerOverviewQuery;
import com.inglo.giggle.banner.application.port.in.result.ReadBannerOverviewResult;
import com.inglo.giggle.banner.application.port.out.LoadBannerPort;
import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadBannerOverviewService implements ReadBannerOverviewQuery {

    private final LoadBannerPort loadBannerPort;

    @Override
    @Transactional(readOnly = true)
    public ReadBannerOverviewResult execute(ESecurityRole role) {

        if (role == ESecurityRole.ADMIN) {
            List<Banner> banners = loadBannerPort.loadBanners();
            return ReadBannerOverviewResult.from(banners);
        }

        List<Banner> banners = loadBannerPort.loadBanners(role);

        return ReadBannerOverviewResult.from(banners);
    }
}
