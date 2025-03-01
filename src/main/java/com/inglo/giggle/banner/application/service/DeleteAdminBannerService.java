package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.application.usecase.DeleteAdminBannerUseCase;
import com.inglo.giggle.banner.repository.mysql.BannerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.service.AccountService;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteAdminBannerService implements DeleteAdminBannerUseCase {

    private final AccountRepository accountRepository;
    private final BannerRepository bannerRepository;

    private final AccountService accountService;

    @Override
    public void execute(UUID accountId, Long bannerId) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 계정 타입 유효성 검사
        accountService.checkAdminValidation(account);

        bannerRepository.deleteById(bannerId);
    }
}
