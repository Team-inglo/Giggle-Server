package com.inglo.giggle.security.service;

import com.inglo.giggle.account.domain.type.ELanguage;
import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.ImageUtil;
import com.inglo.giggle.core.utility.JsonWebTokenUtil;
import com.inglo.giggle.security.domain.redis.TemporaryAccount;
import com.inglo.giggle.security.dto.request.SignUpDefaultUserRequestDto;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import com.inglo.giggle.security.repository.redis.RefreshTokenRepository;
import com.inglo.giggle.security.repository.redis.TemporaryTokenRepository;
import com.inglo.giggle.security.repository.redis.TemporaryAccountRepository;
import com.inglo.giggle.security.usecase.SignUpDefaultUserUseCase;
import com.inglo.giggle.account.domain.User;
import lombok.RequiredArgsConstructor;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.redis.RefreshToken;
import com.inglo.giggle.security.domain.redis.TemporaryToken;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import com.inglo.giggle.security.dto.response.DefaultJsonWebTokenDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpDefaultUserService implements SignUpDefaultUserUseCase {

    private final AccountRepository accountRepository;

    private final TemporaryTokenRepository temporaryTokenRepository;
    private final TemporaryAccountRepository temporaryAccountRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JsonWebTokenUtil jsonWebTokenUtil;

    private final ImageUtil imageUtil;

    @Override
    public DefaultJsonWebTokenDto execute(SignUpDefaultUserRequestDto requestDto) {
        // temporary Token 검증. Redis에 있는 토큰인지 확인 -> id, email 추출
        TemporaryToken temporaryToken = temporaryTokenRepository.findByValue(requestDto.temporaryToken())
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR));
        String id = temporaryToken.getId();
        String email = temporaryToken.getEmail();

        // Redis에서 임시 사용자 정보 가져오기
        TemporaryAccount tempUserInfo = temporaryAccountRepository.findById(id + ":" + email)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TEMPORARY_ACCOUNT));

        // AccountType 검증
        if (!tempUserInfo.getAccountType().getSecurityName().equals("ROLE_USER")) {
            throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
        }

        // Address 생성
        Address address = Address.builder()
                .addressName(requestDto.address().addressName())
                .region1depthName(requestDto.address().region1DepthName())
                .region2depthName(requestDto.address().region2DepthName())
                .region3depthName(requestDto.address().region3DepthName())
                .region4depthName(requestDto.address().region4DepthName())
                .addressDetail(requestDto.address().addressDetail())
                .longitude(requestDto.address().longitude())
                .latitude(requestDto.address().latitude())
                .build();

        // User 생성 및 저장
        Account account = User.builder()
                .provider(ESecurityProvider.DEFAULT)
                .serialId(tempUserInfo.getId())
                .password(bCryptPasswordEncoder.encode(tempUserInfo.getPassword()))
                .email(tempUserInfo.getEmail())
                .profileImgUrl(imageUtil.getUserDefaultImgUrl())
                .countryCode(requestDto.signUpDefaultUserUserInfo().countryCode())
                .firstName(requestDto.signUpDefaultUserUserInfo().firstName())
                .lastName(requestDto.signUpDefaultUserUserInfo().lastName())
                .gender(requestDto.signUpDefaultUserUserInfo().gender())
                .nationality(requestDto.signUpDefaultUserUserInfo().nationality())
                .language(ELanguage.fromString(requestDto.language()))
                .birth(requestDto.signUpDefaultUserUserInfo().birth())
                .visa(requestDto.signUpDefaultUserUserInfo().visa())
                .marketingAllowed(requestDto.marketingAllowed())
                .notificationAllowed(requestDto.notificationAllowed())
                .address(address)
                .build();

        accountRepository.save(account);

        // Default Json Web Token 생성
        DefaultJsonWebTokenDto defaultJsonWebTokenDto = jsonWebTokenUtil.generateDefaultJsonWebTokens(
                account.getId(),
                ESecurityRole.USER
        );

        // Refresh Token 저장
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .accountId(account.getId())
                        .value(defaultJsonWebTokenDto.getRefreshToken())
                        .build()
        );

        // temporary Token 삭제
        temporaryTokenRepository.deleteById(id + ":" + email);

        // temporary User Info 삭제
        temporaryAccountRepository.deleteById(id + ":" + email);

        return defaultJsonWebTokenDto;
    }
}
