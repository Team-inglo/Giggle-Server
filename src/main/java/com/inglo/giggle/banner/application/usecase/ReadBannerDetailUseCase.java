package com.inglo.giggle.banner.application.usecase;

import com.inglo.giggle.banner.application.dto.response.ReadBannerDetailResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.domain.type.ESecurityRole;

@UseCase
public interface ReadBannerDetailUseCase {
    ReadBannerDetailResponseDto execute(ESecurityRole role, Long bannerId);
}
