package com.inglo.giggle.banner.application.controller.command;

import com.inglo.giggle.banner.application.dto.request.CreateAdminBannerRequestDto;
import com.inglo.giggle.banner.application.dto.request.UpdateAdminBannerRequestDto;
import com.inglo.giggle.banner.application.usecase.CreateAdminBannerUseCase;
import com.inglo.giggle.banner.application.usecase.DeleteAdminBannerUseCase;
import com.inglo.giggle.banner.application.usecase.UpdateAdminBannerUseCase;
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
@RequestMapping("/v1/admins/banners")
public class BannerAdminsCommandV1Controller {

    private final CreateAdminBannerUseCase createAdminBannerUseCase;
    private final DeleteAdminBannerUseCase deleteAdminBannerUseCase;
    private final UpdateAdminBannerUseCase updateAdminBannerUseCase;


    /**
     * 12.7 (어드민) 배너 생성하기
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
     * 12.8 (어드민) 배너 수정하기
     */
    @PutMapping("/{bannerId}")
    public ResponseDto<Void> updateBanner(
            @AccountID UUID accountId,
            @PathVariable Long bannerId,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "body") @Valid UpdateAdminBannerRequestDto requestDto
    ) {
        updateAdminBannerUseCase.execute(bannerId, accountId, image, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 12.9 (어드민) 배너 삭제하기
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
