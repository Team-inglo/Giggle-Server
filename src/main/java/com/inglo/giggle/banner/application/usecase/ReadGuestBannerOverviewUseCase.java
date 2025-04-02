package com.inglo.giggle.banner.application.usecase;

import com.inglo.giggle.banner.presentation.dto.response.ReadBannerOverviewResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

@UseCase
public interface ReadGuestBannerOverviewUseCase {

    /**
     * 12.1 (게스트) 배너 요약 조회하기 UseCase
     */
    ReadBannerOverviewResponseDto execute();
}
