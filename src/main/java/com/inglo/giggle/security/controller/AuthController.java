package com.inglo.giggle.security.controller;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.constant.Constants;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.HeaderUtil;
import com.inglo.giggle.security.application.dto.request.*;
import com.inglo.giggle.security.application.dto.response.*;
import com.inglo.giggle.security.application.usecase.*;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@Hidden
public class AuthController {
    private final ReissueJsonWebTokenUseCase reissueJsonWebTokenUseCase;
    private final ValidateIdUseCase validateIdUseCase;
    private final ValidateEmailUseCase validateEmailUseCase;
    private final ReadAccountBriefInfoUseCase readAccountBriefInfoUseCase;
    private final SignUpDefaultTemporaryUseCase signUpDefaultTemporaryUseCase;
    private final SignUpDefaultUserUseCase signUpDefaultUserUseCase;
    private final SignUpDefaultOwnerUseCase signUpDefaultOwnerUseCase;
    private final ValidateAuthenticationCodeUseCase validateAuthenticationCodeUseCase;
    private final ReissueAuthenticationCodeUseCase reissueAuthenticationCodeUseCase;
    private final ReissuePasswordUseCase reissuePasswordUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;
    private final UpdateDeviceTokenUseCase updateDeviceTokenUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final ValidatePasswordUseCase validatePasswordUseCase;

    /**
     * 1.3 JWT 재발급
     */
    @PostMapping("/reissue/token")
    public ResponseDto<DefaultJsonWebTokenDto> reissueDefaultJsonWebToken(
            HttpServletRequest request
    ) {
        String refreshToken = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER_ERROR));

        return ResponseDto.created(reissueJsonWebTokenUseCase.execute(refreshToken));
    }

    /**
     * 1.4 디바이스 토큰 갱신
     */
    @PatchMapping("/device-token")
    public ResponseDto<Void> updateDeviceToken(
            @RequestBody @Valid UpdateDeviceTokenRequestDto requestDto,
            @AccountID UUID accountId
    ) {
        updateDeviceTokenUseCase.execute(accountId, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 2.1 아이디 중복 검사
     */
    @Deprecated
    @GetMapping("/validations/id")
    public ResponseDto<ValidationResponseDto> validateId(
            @RequestParam(name = "id") String id
    ) {
        return ResponseDto.ok(validateIdUseCase.execute(id));
    }

    /**
     * 2.2 이메일 중복 검사
     */
    @GetMapping("/validations/email")
    public ResponseDto<ValidationResponseDto> validateEmail(
            @RequestParam(name = "email") String email
    ) {
        return ResponseDto.ok(validateEmailUseCase.execute(email));
    }

    /**
     * 2.3 유학생 or 고용주 간단 정보 조회(판단)
     */
    @GetMapping("/briefs")
    public ResponseDto<AccountBriefInfoResponseDto> readUserBriefInfo(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readAccountBriefInfoUseCase.execute(accountId));
    }

    /**
     * 2.4 기본 임시 회원가입
     */
    @PostMapping("/sign-up")
    public ResponseDto<IssueAuthenticationCodeResponseDto> signUpDefaultTemporary(
            @RequestBody @Valid SignUpDefaultTemporaryRequestDto requestDto
    ) {
        return ResponseDto.created(signUpDefaultTemporaryUseCase.execute(requestDto));
    }

    /**
     * 2.5 기본 회원가입(유학생)
     */
    @PostMapping("/users")
    public ResponseDto<?> signUpDefaultUser(
            @RequestBody @Valid SignUpDefaultUserRequestDto requestDto
    ) {
        return ResponseDto.created(
                signUpDefaultUserUseCase.execute(requestDto)
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
        return ResponseDto.created(
                signUpDefaultOwnerUseCase.execute(requestDto, image)
        );
    }

    /**
     * 2.7 이메일 인증 코드 검증
     */
    @PatchMapping("/validations/authentication-code")
    public ResponseDto<TemporaryJsonWebTokenDto> validateAuthenticationCode(
            @RequestBody @Valid ValidateAuthenticationCodeRequestDto requestDto
    ) {
        return ResponseDto.created(validateAuthenticationCodeUseCase.execute(requestDto));
    }

    /**
     * 2.8 인증 코드 발급(재발급)
     */
    @PatchMapping("/reissue/authentication-code")
    public ResponseDto<IssueAuthenticationCodeResponseDto> reissueAuthenticationCode(
            @RequestBody @Valid IssueAuthenticationCodeRequestDto requestDto
    ) {
        return ResponseDto.created(reissueAuthenticationCodeUseCase.execute(requestDto));
    }

    /**
     * 2.9 회원 탈퇴
     */
    @DeleteMapping("")
    public ResponseDto<?> deleteAccount(
            @AccountID UUID accountId
    ) {
        deleteAccountUseCase.execute(accountId);
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

        reissuePasswordUseCase.execute(temporaryToken);

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
        changePasswordUseCase.execute(accountId, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 2.12 현재 비밀번호 확인
     */
    @PostMapping("/validations/password")
    public ResponseDto<?> validatePassword(
            @RequestBody @Valid ValidatePasswordRequestDto requestDto,
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(validatePasswordUseCase.execute(accountId, requestDto));
    }
}
