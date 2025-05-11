package com.inglo.giggle.owner.adapter.in.web;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.owner.adapter.in.web.dto.UpdateOwnerNotificationAllowedRequestDto;
import com.inglo.giggle.owner.adapter.in.web.dto.UpdateOwnerRequestDto;
import com.inglo.giggle.owner.application.port.in.command.UpdateOwnerCommand;
import com.inglo.giggle.owner.application.port.in.command.UpdateOwnerNotificationAllowedCommand;
import com.inglo.giggle.owner.application.port.in.usecase.UpdateOwnerNotificationAllowedUseCase;
import com.inglo.giggle.owner.application.port.in.usecase.UpdateOwnerUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/owners")
public class OwnerCommandV1Controller {

    private final UpdateOwnerUseCase updateOwnerUseCase;
    private final UpdateOwnerNotificationAllowedUseCase updateOwnerNotificationAllowedUseCase;

    /**
     * 3.6 (고용주) 회사 정보 수정하기
     */
    @PatchMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto<Void> updateOwner(
            @AccountID UUID accountId,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "body") UpdateOwnerRequestDto requestDto
            ) {

        UpdateOwnerCommand command = new UpdateOwnerCommand(
                accountId,
                image,
                requestDto.ownerInfo().companyName(),
                requestDto.ownerInfo().ownerName(),
                requestDto.ownerInfo().companyRegistrationNumber(),
                requestDto.ownerInfo().phoneNumber(),
                requestDto.address(),
                requestDto.isIconImgChanged()
        );
        updateOwnerUseCase.execute(command);
        return ResponseDto.ok(null);
    }

    /**
     * 3.7 (유학생/고용주) 알람 설정 변경하기
     */
    @PatchMapping("/notification-allowed")
    public ResponseDto<Void> updateNotificationAllowed(
            @AccountID UUID accountId,
            @RequestBody @Valid UpdateOwnerNotificationAllowedRequestDto requestDto
    ) {
        UpdateOwnerNotificationAllowedCommand command = new UpdateOwnerNotificationAllowedCommand(accountId, requestDto.isNotificationAllowed());
        updateOwnerNotificationAllowedUseCase.execute(command);
        return ResponseDto.ok(null);
    }
}
