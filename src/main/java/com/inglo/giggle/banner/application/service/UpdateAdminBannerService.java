package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.application.dto.request.UpdateAdminBannerRequestDto;
import com.inglo.giggle.banner.application.usecase.UpdateAdminBannerUseCase;
import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.banner.repository.mysql.BannerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateAdminBannerService implements UpdateAdminBannerUseCase {

    private final BannerRepository bannerRepository;
    private final AccountRepository accountRepository;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(Long bannerId, UUID accountId, MultipartFile image, UpdateAdminBannerRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // Banner 조회
        Banner banner = bannerRepository.findById(bannerId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        if (image != null) {
            String imgUrl = s3Util.uploadImageFile(image, account.getSerialId(), EImageType.BANNER_IMG);
            banner.updateWithImgUrl(requestDto.title(), imgUrl, requestDto.content(), requestDto.role());
        } else {
            banner.updateWithoutImgUrl(requestDto.title(), requestDto.content(), requestDto.role());
        }
        bannerRepository.save(banner);
    }
}
