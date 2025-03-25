package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.application.dto.response.ReadAdminBannerDetailResponseDto;
import com.inglo.giggle.banner.application.usecase.ReadAdminBannerDetailUseCase;
import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.banner.repository.BannerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadAdminBannerDetailService implements ReadAdminBannerDetailUseCase {

    private final BannerRepository bannerRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadAdminBannerDetailResponseDto execute(Long bannerId) {

        Banner banner = bannerRepository.findByIdOrElseThrow(bannerId);

        return ReadAdminBannerDetailResponseDto.fromEntity(banner);
    }
}
