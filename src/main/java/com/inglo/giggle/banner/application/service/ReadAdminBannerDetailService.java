package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.application.dto.response.ReadAdminBannerDetailResponseDto;
import com.inglo.giggle.banner.application.usecase.ReadAdminBannerDetailUseCase;
import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.banner.repository.mysql.BannerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadAdminBannerDetailService implements ReadAdminBannerDetailUseCase {

    private final BannerRepository bannerRepository;

    @Override
    public ReadAdminBannerDetailResponseDto execute(Long bannerId) {

        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        return ReadAdminBannerDetailResponseDto.fromEntity(banner);
    }
}
