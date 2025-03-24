package com.inglo.giggle.banner.application.usecase;

import com.inglo.giggle.banner.application.dto.request.UpdateAdminBannerRequestDto;
import com.inglo.giggle.core.annotation.bean.UseCase;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@UseCase
public interface UpdateAdminBannerUseCase {

    /**
     * 12.8 (어드민) 배너 수정하기 UseCase
     */
    void execute(Long bannerId, UUID accountId, MultipartFile image, UpdateAdminBannerRequestDto requestDto);
}
