package com.inglo.giggle.banner.application.controller.command;

import com.inglo.giggle.banner.application.dto.request.CreateAdminBannerRequestDto;
import com.inglo.giggle.banner.application.usecase.CreateAdminBannerUseCase;
import com.inglo.giggle.banner.application.usecase.DeleteAdminBannerUseCase;
import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/banners")
public class BannerAdminCommandV1Controller {

    private final CreateAdminBannerUseCase createAdminBannerUseCase;
    private final DeleteAdminBannerUseCase deleteAdminBannerUseCase;


    /**
     * 12.5 (관리자) 배너 생성하기
     */
    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto<Void> createBanner(
            @AccountID UUID accountId,
            @RequestPart(value = "image") MultipartFile image,
            @RequestPart(value = "body") @Valid CreateAdminBannerRequestDto requestDto
    ) {
        createAdminBannerUseCase.execute(accountId, image, requestDto);
        return ResponseDto.created(null);
    }

    /**
     * 12.6 (관리자) 배너 삭제하기
     */
    @DeleteMapping("/{bannerId}")
    public ResponseDto<Void> deleteBanner(
            @AccountID UUID accountId,
            @PathVariable Long bannerId
    ) {
        deleteAdminBannerUseCase.execute(accountId, bannerId);
        return ResponseDto.ok(null);
    }

}
