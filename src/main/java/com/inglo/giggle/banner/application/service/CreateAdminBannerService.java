package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.banner.presentation.dto.request.CreateAdminBannerRequestDto;
import com.inglo.giggle.banner.application.usecase.CreateAdminBannerUseCase;
import com.inglo.giggle.banner.persistence.repository.BannerRepository;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateAdminBannerService implements CreateAdminBannerUseCase {

    private final LoadAccountPort loadAccountPort;
    private final BannerRepository bannerRepository;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(UUID accountId, MultipartFile image, CreateAdminBannerRequestDto requestDto) {

        // Account 조회
        Account account = loadAccountPort.loadAccount(accountId);

        String imgUrl = s3Util.uploadImageFile(image, account.getSerialId(), EImageType.BANNER_IMG);

        Banner banner = Banner.builder()
                .title(requestDto.title())
                .imgUrl(imgUrl)
                .content(requestDto.content())
                .role(requestDto.role())
                .build();

        bannerRepository.save(banner);
    }
}
