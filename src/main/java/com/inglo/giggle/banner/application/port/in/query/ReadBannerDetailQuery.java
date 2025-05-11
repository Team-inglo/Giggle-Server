package com.inglo.giggle.banner.application.port.in.query;

import com.inglo.giggle.banner.application.port.in.result.ReadBannerDetailResult;
import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;

@UseCase
public interface ReadBannerDetailQuery {

    /**
     * 12.4 (유학생/고용주) 배너 상세 조회하기 UseCase
     */
    ReadBannerDetailResult execute(ESecurityRole role, Long bannerId);
}
