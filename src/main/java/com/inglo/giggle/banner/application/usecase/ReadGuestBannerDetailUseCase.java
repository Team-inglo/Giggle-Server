package com.inglo.giggle.banner.application.usecase;

import com.inglo.giggle.banner.application.dto.response.ReadBannerDetailResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

@UseCase
public interface ReadGuestBannerDetailUseCase {

    /**
     * 12.2 (게스트) 배너 상세 조회하기 UseCase
     */
    ReadBannerDetailResponseDto execute(Long bannerId);
}
