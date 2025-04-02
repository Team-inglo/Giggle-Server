package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.application.usecase.DeleteAdminBannerUseCase;
import com.inglo.giggle.banner.persistence.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteAdminBannerService implements DeleteAdminBannerUseCase {

    private final BannerRepository bannerRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long bannerId) {

        bannerRepository.deleteById(bannerId);
    }
}
