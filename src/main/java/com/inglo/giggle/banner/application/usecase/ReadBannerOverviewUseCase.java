package com.inglo.giggle.banner.application.usecase;

import com.inglo.giggle.banner.application.dto.response.ReadBannerOverviewResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.domain.type.ESecurityRole;

@UseCase
public interface ReadBannerOverviewUseCase {
    ReadBannerOverviewResponseDto execute(ESecurityRole role);
}
