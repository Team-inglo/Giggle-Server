package com.inglo.giggle.banner.application.port.in.query;

import com.inglo.giggle.banner.application.port.in.result.ReadGuestBannerOverviewResult;
import com.inglo.giggle.core.annotation.bean.UseCase;

@UseCase
public interface ReadGuestBannerOverviewQuery {

    /**
     * 12.1 (게스트) 배너 요약 조회하기 UseCase
     */
    ReadGuestBannerOverviewResult execute();
}
