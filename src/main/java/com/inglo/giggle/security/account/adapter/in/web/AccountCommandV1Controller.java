package com.inglo.giggle.security.account.adapter.in.web;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.constant.Constants;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.core.utility.HeaderUtil;
import com.inglo.giggle.security.account.adapter.in.web.dto.ChangePasswordRequestDto;
import com.inglo.giggle.security.account.adapter.in.web.dto.SignUpDefaultOwnerRequestDto;
import com.inglo.giggle.security.account.adapter.in.web.dto.SignUpDefaultUserRequestDto;
import com.inglo.giggle.security.account.adapter.in.web.dto.UpdateDeviceTokenRequestDto;
import com.inglo.giggle.security.account.application.port.in.command.ChangePasswordCommand;
import com.inglo.giggle.security.account.application.port.in.command.DeleteAccountCommand;
import com.inglo.giggle.security.account.application.port.in.command.ReissueJsonWebTokenCommand;
import com.inglo.giggle.security.account.application.port.in.command.ReissuePasswordCommand;
import com.inglo.giggle.security.account.application.port.in.command.SignUpDefaultOwnerCommand;
import com.inglo.giggle.security.account.application.port.in.command.SignUpDefaultUserCommand;
import com.inglo.giggle.security.account.application.port.in.command.UpdateDeviceTokenCommand;
import com.inglo.giggle.security.account.application.port.in.result.ReissueJsonWebTokenResult;
import com.inglo.giggle.security.account.application.port.in.usecase.ChangePasswordUseCase;
import com.inglo.giggle.security.account.application.port.in.usecase.DeleteAccountUseCase;
import com.inglo.giggle.security.account.application.port.in.usecase.ReissueJsonWebTokenUseCase;
import com.inglo.giggle.security.account.application.port.in.usecase.ReissuePasswordUseCase;
import com.inglo.giggle.security.account.application.port.in.usecase.SignUpDefaultOwnerUseCase;
import com.inglo.giggle.security.account.application.port.in.usecase.SignUpDefaultUserUseCase;
import com.inglo.giggle.security.account.application.port.in.usecase.UpdateDeviceTokenUseCase;
import com.inglo.giggle.user.domain.type.ELanguage;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@Hidden
public class AccountCommandV1Controller {
    private final ReissueJsonWebTokenUseCase reissueJsonWebTokenUseCase;
    private final UpdateDeviceTokenUseCase updateDeviceTokenUseCase;
    private final SignUpDefaultUserUseCase signUpDefaultUserUseCase;
    private final SignUpDefaultOwnerUseCase signUpDefaultOwnerUseCase;
    private final ReissuePasswordUseCase reissuePasswordUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;

    /**
     * 1.3 JWT 재발급
     */
    @PostMapping("/reissue/token")
    public ResponseDto<ReissueJsonWebTokenResult> reissueDefaultJsonWebToken(
            HttpServletRequest request
    ) {
        String refreshToken = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER_ERROR));

        ReissueJsonWebTokenCommand command = new ReissueJsonWebTokenCommand(
                refreshToken
        );

        return ResponseDto.created(reissueJsonWebTokenUseCase.execute(command));
    }

    /**
     * 1.4 디바이스 토큰 갱신
     */
    @PatchMapping("/device-token")
    public ResponseDto<Void> updateDeviceToken(
            @RequestBody @Valid UpdateDeviceTokenRequestDto requestDto,
            @AccountID UUID accountId
    ) {

        UpdateDeviceTokenCommand command = new UpdateDeviceTokenCommand(
                accountId,
                requestDto.deviceToken(),
                requestDto.deviceId()
        );
        updateDeviceTokenUseCase.execute(command);
        return ResponseDto.ok(null);
    }

    /**
     * 2.5 기본 회원가입(유학생)
     */
    @PostMapping("/users")
    public ResponseDto<?> signUpDefaultUser(
            @RequestBody @Valid SignUpDefaultUserRequestDto requestDto
    ) {
        SignUpDefaultUserCommand command = new SignUpDefaultUserCommand(
                requestDto.temporaryToken(),
                requestDto.address(),
                requestDto.marketingAllowed(),
                requestDto.notificationAllowed(),
                ELanguage.fromString(requestDto.language()),
                requestDto.termTypes(),
                requestDto.signUpDefaultUserUserInfo().firstName(),
                requestDto.signUpDefaultUserUserInfo().lastName(),
                EGender.fromString(requestDto.signUpDefaultUserUserInfo().gender()),
                requestDto.signUpDefaultUserUserInfo().birth(),
                requestDto.signUpDefaultUserUserInfo().nationality(),
                EVisa.fromString(requestDto.signUpDefaultUserUserInfo().visa()),
                requestDto.signUpDefaultUserUserInfo().phoneNumber()
        );
        return ResponseDto.created(
                signUpDefaultUserUseCase.execute(command)
        );
    }

    /**
     * 2.6 기본 회원가입(고용주)
     */
    @PostMapping(value = "/owners", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto<?> signUpDefaultOwner(
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart("body") @Valid SignUpDefaultOwnerRequestDto requestDto
    ) {
        SignUpDefaultOwnerCommand command = new SignUpDefaultOwnerCommand(
                image,
                requestDto.temporaryToken(),
                requestDto.address(),
                requestDto.marketingAllowed(),
                requestDto.notificationAllowed(),
                requestDto.termTypes(),
                requestDto.signUpDefaultOwnerOwnerInfo().companyName(),
                requestDto.signUpDefaultOwnerOwnerInfo().ownerName(),
                requestDto.signUpDefaultOwnerOwnerInfo().companyRegistrationNumber(),
                requestDto.signUpDefaultOwnerOwnerInfo().phoneNumber()
        );
        return ResponseDto.created(
                signUpDefaultOwnerUseCase.execute(command)
        );
    }

    /**
     * 2.9 회원 탈퇴
     */
    @DeleteMapping("")
    public ResponseDto<?> deleteAccount(
            @AccountID UUID accountId
    ) {
        DeleteAccountCommand command = new DeleteAccountCommand(accountId);
        deleteAccountUseCase.execute(command);
        return ResponseDto.ok(null);
    }

    /**
     * 2.10 임시 비밀번호 발급 및 메일 전송
     */
    @PostMapping("/reissue/password")
    public ResponseDto<?> reissuePassword(
            HttpServletRequest request
    ) {
        String temporaryToken = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER_ERROR));

        ReissuePasswordCommand command = new ReissuePasswordCommand(
                temporaryToken
        );
        reissuePasswordUseCase.execute(command);

        return ResponseDto.ok(null);
    }

    /**
     * 2.11 비밀번호 변경
     */
    @PatchMapping("/password")
    public ResponseDto<?> changePassword(
            @RequestBody @Valid ChangePasswordRequestDto requestDto,
            @AccountID UUID accountId
    ) {
        ChangePasswordCommand command = new ChangePasswordCommand(
                accountId,
                requestDto.currentPassword(),
                requestDto.newPassword()
        );
        changePasswordUseCase.execute(command);
        return ResponseDto.ok(null);
    }
}
