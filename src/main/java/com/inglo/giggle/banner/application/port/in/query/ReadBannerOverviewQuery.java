package com.inglo.giggle.banner.application.port.in.query;

import com.inglo.giggle.banner.application.port.in.result.ReadBannerOverviewResult;
import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;

@UseCase
public interface ReadBannerOverviewQuery {

    /**
     * 12.3 (유학생/고용주) 배너 요약 조회하기 UseCase
     */
    ReadBannerOverviewResult execute(ESecurityRole role);
}
