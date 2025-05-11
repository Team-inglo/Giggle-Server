package com.inglo.giggle.banner.adapter.in.web;

import com.inglo.giggle.banner.adapter.in.web.dto.CreateAdminBannerRequestDto;
import com.inglo.giggle.banner.adapter.in.web.dto.UpdateAdminBannerRequestDto;
import com.inglo.giggle.banner.application.port.in.command.CreateAdminBannerCommand;
import com.inglo.giggle.banner.application.port.in.command.DeleteAdminBannerCommand;
import com.inglo.giggle.banner.application.port.in.command.UpdateAdminBannerCommand;
import com.inglo.giggle.banner.application.port.in.usecase.CreateAdminBannerUseCase;
import com.inglo.giggle.banner.application.port.in.usecase.DeleteAdminBannerUseCase;
import com.inglo.giggle.banner.application.port.in.usecase.UpdateAdminBannerUseCase;
import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
        CreateAdminBannerCommand command = new CreateAdminBannerCommand(
                accountId,
                image,
                requestDto.title(),
                requestDto.content(),
                requestDto.role()
        );
        createAdminBannerUseCase.execute(command);
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

        UpdateAdminBannerCommand command = new UpdateAdminBannerCommand(
                accountId,
                bannerId,
                image,
                requestDto.title(),
                requestDto.content(),
                requestDto.role()
        );
        updateAdminBannerUseCase.execute(command);
        return ResponseDto.ok(null);
    }

    /**
     * 12.9 (어드민) 배너 삭제하기
     */
    @DeleteMapping("/{bannerId}")
    public ResponseDto<Void> deleteBanner(
            @PathVariable Long bannerId
    ) {
        DeleteAdminBannerCommand command = new DeleteAdminBannerCommand(
                bannerId
        );
        deleteAdminBannerUseCase.execute(command);
        return ResponseDto.ok(null);
    }

}
