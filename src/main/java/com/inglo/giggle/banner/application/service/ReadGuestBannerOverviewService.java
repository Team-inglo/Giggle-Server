package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.application.dto.response.ReadBannerOverviewResponseDto;
import com.inglo.giggle.banner.application.usecase.ReadGuestBannerOverviewUseCase;
import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.banner.repository.BannerRepository;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadGuestBannerOverviewService implements ReadGuestBannerOverviewUseCase {

    private final BannerRepository bannerRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadBannerOverviewResponseDto execute() {

        List<Banner> banners = bannerRepository.findByRole(ESecurityRole.USER);

        return ReadBannerOverviewResponseDto.fromEntities(banners);
    }
}
