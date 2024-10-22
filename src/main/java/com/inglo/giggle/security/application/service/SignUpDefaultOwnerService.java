package com.inglo.giggle.security.application.service;

import com.inglo.giggle.account.domain.service.OwnerDomainService;
import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.domain.service.AddressDomainService;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.ImageUtil;
import com.inglo.giggle.core.utility.JsonWebTokenUtil;
import com.inglo.giggle.security.application.usecase.SignUpDefaultOwnerUseCase;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.redis.TemporaryToken;
import com.inglo.giggle.security.domain.redis.TemporaryAccount;
import com.inglo.giggle.security.domain.service.RefreshTokenDomainService;
import com.inglo.giggle.security.domain.service.TemporaryAccountDomainService;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import com.inglo.giggle.security.application.dto.request.SignUpDefaultOwnerRequestDto;
import com.inglo.giggle.security.application.dto.response.DefaultJsonWebTokenDto;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import com.inglo.giggle.security.repository.redis.RefreshTokenRepository;
import com.inglo.giggle.security.repository.redis.TemporaryTokenRepository;
import com.inglo.giggle.security.repository.redis.TemporaryAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class SignUpDefaultOwnerService implements SignUpDefaultOwnerUseCase {

    private final AccountRepository accountRepository;

    private final TemporaryTokenRepository temporaryTokenRepository;
    private final TemporaryAccountRepository temporaryAccountRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final TemporaryAccountDomainService temporaryAccountDomainService;
    private final AddressDomainService addressDomainService;
    private final OwnerDomainService ownerDomainService;
    private final RefreshTokenDomainService refreshTokenDomainService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JsonWebTokenUtil jsonWebTokenUtil;

    private final ImageUtil imageUtil;
    @Override
    @Transactional
    public DefaultJsonWebTokenDto execute(SignUpDefaultOwnerRequestDto requestDto, MultipartFile file) {
        // temporary Token 검증. Redis에 있는 토큰인지 확인
        TemporaryToken temporaryToken = temporaryTokenRepository.findByValue(requestDto.temporaryToken())
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR));

        // Redis에서 임시 사용자 정보 가져오기
        TemporaryAccount tempUserInfo = temporaryAccountRepository.findById(temporaryToken.getCompositeKey())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TEMPORARY_ACCOUNT));

        // AccountType 검증
        temporaryAccountDomainService.validateAccountTypeOwner(tempUserInfo);

        // 아이콘 이미지 저장
        String iconUrl = imageUtil.getOwnerDefaultImgUrl();
        if (file != null) {
            iconUrl = imageUtil.uploadOwnerIconImageFile(file, tempUserInfo.getId());
        }

        // Address 생성
        Address address = addressDomainService.createAddress(requestDto.address());

        // Owner 생성 및 저장
        Account account = ownerDomainService.createOwner(
                ESecurityProvider.DEFAULT,
                tempUserInfo,
                bCryptPasswordEncoder.encode(tempUserInfo.getPassword()),
                iconUrl, requestDto, address
        );
        accountRepository.save(account);

        // Default Json Web Token 생성
        DefaultJsonWebTokenDto defaultJsonWebTokenDto = jsonWebTokenUtil.generateDefaultJsonWebTokens(
                account.getId(),
                ESecurityRole.OWNER
        );

        // Refresh Token 저장
        refreshTokenRepository.save(refreshTokenDomainService.createRefreshToken(account.getId(), defaultJsonWebTokenDto.getRefreshToken()));

        // temporary Token 삭제
        temporaryTokenRepository.deleteById(temporaryToken.getCompositeKey());

        // temporary User Info 삭제
        temporaryAccountRepository.deleteById(tempUserInfo.getCompositeKey());

        return defaultJsonWebTokenDto;
    }
}
