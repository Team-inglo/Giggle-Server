package com.inglo.giggle.banner.application.usecase;

import com.inglo.giggle.banner.presentation.dto.response.ReadBannerDetailResponseDto;
import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;

@UseCase
public interface ReadBannerDetailUseCase {

    /**
     * 12.4 (유학생/고용주) 배너 상세 조회하기 UseCase
     */
    ReadBannerDetailResponseDto execute(ESecurityRole role, Long bannerId);
}
