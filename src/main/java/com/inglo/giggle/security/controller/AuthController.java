package com.inglo.giggle.security.controller;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.contants.Constants;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.CookieUtil;
import com.inglo.giggle.core.utility.HeaderUtil;
import com.inglo.giggle.core.utility.HttpServletUtil;
import com.inglo.giggle.security.dto.request.*;
import com.inglo.giggle.security.dto.response.*;
import com.inglo.giggle.security.usecase.*;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping("/reissue/token")
    public ResponseDto<DefaultJsonWebTokenDto> reissueDefaultJsonWebToken(
            HttpServletRequest request
    ) {
        String refreshToken = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER_ERROR));

        return ResponseDto.created(reissueJsonWebTokenUseCase.execute(refreshToken));
    }

    @GetMapping("/validations/id")
    public ResponseDto<ValidationResponseDto> validateId(
            @RequestParam(name = "id") String id
    ) {
        return ResponseDto.ok(validateIdUseCase.execute(id));
    }

    @GetMapping("/validations/email")
    public ResponseDto<ValidationResponseDto> validateEmail(
            @RequestParam(name = "email") String email
    ) {
        return ResponseDto.ok(validateEmailUseCase.execute(email));
    }

    @GetMapping("/briefs")
    public ResponseDto<AccountBriefInfoResponseDto> readUserBriefInfo(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readAccountBriefInfoUseCase.execute(accountId));
    }


    @PostMapping("/sign-up")
    public ResponseDto<IssueAuthenticationCodeResponseDto> signUpDefaultTemporary(
            @RequestBody @Valid SignUpDefaultTemporaryRequestDto requestDto
    ) {
        return ResponseDto.created(signUpDefaultTemporaryUseCase.execute(requestDto));
    }

    @PostMapping("/users")
    public ResponseDto<DefaultJsonWebTokenDto> signUpDefaultUser(
            @RequestBody @Valid SignUpDefaultUserRequestDto requestDto
    ) {;
        return ResponseDto.created(signUpDefaultUserUseCase.execute(requestDto));
    }

    @PostMapping(value = "/owners", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto<DefaultJsonWebTokenDto> signUpDefaultOwner(
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart("body") @Valid SignUpDefaultOwnerRequestDto requestDto
    ) {
        return ResponseDto.created(signUpDefaultOwnerUseCase.execute(requestDto, image));
    }

    @PatchMapping("/validations/authentication-code")
    public ResponseDto<TemporaryJsonWebTokenDto> validateAuthenticationCode(
            @RequestBody @Valid ValidateAuthenticationCodeRequestDto requestDto
    ) {
        return ResponseDto.created(validateAuthenticationCodeUseCase.execute(requestDto));
    }

    @PatchMapping("/reissue/authentication-code")
    public ResponseDto<IssueAuthenticationCodeResponseDto> reissueAuthenticationCode(
            @RequestBody @Valid IssueAuthenticationCodeRequestDto requestDto
    ) {
        return ResponseDto.created(reissueAuthenticationCodeUseCase.execute(requestDto));
    }

    @PostMapping("/reissue/password")
    public ResponseDto<?> reissuePassword(
            HttpServletRequest request
    ) {
        String temporaryToken = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER_ERROR));

        reissuePasswordUseCase.execute(temporaryToken);

        return ResponseDto.ok(null);
    }
}
