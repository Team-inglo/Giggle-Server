package com.inglo.giggle.banner.application.usecase;

import com.inglo.giggle.banner.application.dto.request.CreateAdminBannerRequestDto;
import com.inglo.giggle.core.annotation.bean.UseCase;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@UseCase
public interface CreateAdminBannerUseCase {
    void execute(UUID accountId, MultipartFile image, CreateAdminBannerRequestDto requestDto);
}
