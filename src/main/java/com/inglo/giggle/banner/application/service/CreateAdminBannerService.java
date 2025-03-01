package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.application.dto.request.CreateAdminBannerRequestDto;
import com.inglo.giggle.banner.application.usecase.CreateAdminBannerUseCase;
import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.banner.repository.mysql.BannerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateAdminBannerService implements CreateAdminBannerUseCase {

    private final AccountRepository accountRepository;
    private final BannerRepository bannerRepository;

    private final AccountService accountService;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(UUID accountId, MultipartFile image, CreateAdminBannerRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 계정 타입 유효성 검사
        accountService.checkAdminValidation(account);

        String imgUrl = s3Util.uploadImageFile(image, account.getSerialId(), EImageType.Banner_IMG);

        Banner banner = Banner.builder()
                .imgUrl(imgUrl)
                .content(requestDto.content())
                .role(requestDto.role())
                .build();

        bannerRepository.save(banner);
    }
}
