package com.inglo.giggle.banner.application.usecase;

import com.inglo.giggle.banner.application.dto.response.ReadBannerDetailResponseDto;

public interface ReadBannerDetailUseCase {
    ReadBannerDetailResponseDto execute(Long bannerId);
}
