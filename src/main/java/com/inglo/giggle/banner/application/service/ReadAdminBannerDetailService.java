package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.application.port.in.query.ReadAdminBannerDetailQuery;
import com.inglo.giggle.banner.application.port.in.result.ReadAdminBannerDetailResult;
import com.inglo.giggle.banner.application.port.out.LoadBannerPort;
import com.inglo.giggle.banner.domain.Banner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadAdminBannerDetailService implements ReadAdminBannerDetailQuery {

    private final LoadBannerPort loadBannerPort;

    @Override
    @Transactional(readOnly = true)
    public ReadAdminBannerDetailResult execute(Long bannerId) {

        Banner banner = loadBannerPort.loadBannerOrElseThrow(bannerId);

        return ReadAdminBannerDetailResult.from(banner);
    }
}
