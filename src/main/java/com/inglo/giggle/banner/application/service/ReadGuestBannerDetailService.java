package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.banner.presentation.dto.response.ReadBannerDetailResponseDto;
import com.inglo.giggle.banner.application.usecase.ReadGuestBannerDetailUseCase;
import com.inglo.giggle.banner.persistence.repository.BannerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadGuestBannerDetailService implements ReadGuestBannerDetailUseCase {
    private final BannerRepository bannerRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadBannerDetailResponseDto execute(Long bannerId) {

        // Banner 조회
        Banner banner = bannerRepository.findByIdOrElseThrow(bannerId);

        // Banner 권한 확인
        if (!banner.getRole().equals(ESecurityRole.USER) && !banner.getRole().equals(ESecurityRole.GUEST)) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        return ReadBannerDetailResponseDto.from(banner);
    }


}
