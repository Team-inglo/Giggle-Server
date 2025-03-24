package com.inglo.giggle.banner.application.usecase;

import com.inglo.giggle.banner.application.dto.response.ReadAdminBannerDetailResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;

@UseCase
public interface ReadAdminBannerDetailUseCase {

    /**
     * 12.6 (어드민) 배너 상세 조회하기 UseCase
     */
    ReadAdminBannerDetailResponseDto execute(Long bannerId);
}
