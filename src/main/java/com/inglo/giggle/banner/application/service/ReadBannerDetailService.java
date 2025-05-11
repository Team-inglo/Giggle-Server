package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.banner.presentation.dto.response.ReadBannerDetailResponseDto;
import com.inglo.giggle.banner.application.usecase.ReadBannerDetailUseCase;
import com.inglo.giggle.banner.persistence.repository.BannerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadBannerDetailService implements ReadBannerDetailUseCase {

    private final BannerRepository bannerRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadBannerDetailResponseDto execute(ESecurityRole role, Long bannerId) {

        // Banner 조회
        Banner banner = bannerRepository.findByIdOrElseThrow(bannerId);

        // 관리자인지 확인
        if (role == ESecurityRole.ADMIN) {
            return ReadBannerDetailResponseDto.from(banner);
        }

        // Banner 권한 확인
        if (banner.getRole() != ESecurityRole.GUEST && !banner.getRole().equals(role)) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        return ReadBannerDetailResponseDto.from(banner);
    }


}
