package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.application.dto.response.ReadBannerOverviewResponseDto;
import com.inglo.giggle.banner.application.usecase.ReadBannerOverviewUseCase;
import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.banner.repository.mysql.BannerRepository;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadBannerOverviewService implements ReadBannerOverviewUseCase {

    private final BannerRepository bannerRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadBannerOverviewResponseDto execute(ESecurityRole role) {

        if (role == ESecurityRole.ADMIN) {
            List<Banner> banners = bannerRepository.findAll();
            return ReadBannerOverviewResponseDto.fromEntities(banners);
        }

        List<Banner> banners = bannerRepository.findByRole(role);

        return ReadBannerOverviewResponseDto.fromEntities(banners);
    }
}
