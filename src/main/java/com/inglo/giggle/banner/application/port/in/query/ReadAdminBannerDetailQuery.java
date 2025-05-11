package com.inglo.giggle.banner.application.port.in.query;

import com.inglo.giggle.banner.application.port.in.result.ReadAdminBannerDetailResult;
import com.inglo.giggle.core.annotation.bean.UseCase;

@UseCase
public interface ReadAdminBannerDetailQuery {

    /**
     * 12.6 (어드민) 배너 상세 조회하기 UseCase
     */
    ReadAdminBannerDetailResult execute(Long bannerId);
}
