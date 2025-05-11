package com.inglo.giggle.banner.application.port.in.query;

import com.inglo.giggle.banner.application.port.in.result.ReadGuestBannerDetailResult;
import com.inglo.giggle.core.annotation.bean.UseCase;

@UseCase
public interface ReadGuestBannerDetailQuery {

    /**
     * 12.2 (게스트) 배너 상세 조회하기 UseCase
     */
    ReadGuestBannerDetailResult execute(Long bannerId);
}
