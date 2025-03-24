package com.inglo.giggle.banner.application.usecase;

import com.inglo.giggle.banner.application.dto.response.ReadBannerOverviewResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.domain.type.ESecurityRole;

@UseCase
public interface ReadBannerOverviewUseCase {

    /**
     * 12.3 (유학생/고용주) 배너 요약 조회하기 UseCase
     */
    ReadBannerOverviewResponseDto execute(ESecurityRole role);
}
