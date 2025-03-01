package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.application.dto.response.ReadBannerOverviewResponseDto;
import com.inglo.giggle.banner.application.usecase.ReadBannerOverviewUseCase;
import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.banner.repository.mysql.BannerRepository;
import com.inglo.giggle.core.constant.Constants;
import com.inglo.giggle.core.utility.JsonWebTokenUtil;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadBannerOverviewService implements ReadBannerOverviewUseCase {

    private final BannerRepository bannerRepository;

    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Override
    @Transactional(readOnly = true)
    public ReadBannerOverviewResponseDto execute(String accessToken) {

        // Role에 따른 배너 조회
        ESecurityRole role = accessToken == null ?
                ESecurityRole.USER : ESecurityRole.fromString(jsonWebTokenUtil.validateToken(accessToken).get(Constants.ACCOUNT_ROLE_CLAIM_NAME, String.class));

        if (role == ESecurityRole.ADMIN) {
            List<Banner> banners = bannerRepository.find();
            return ReadBannerOverviewResponseDto.fromEntities(banners);
        }

        List<Banner> banners = bannerRepository.findByRole(role);

        return ReadBannerOverviewResponseDto.fromEntities(banners);
    }
}
