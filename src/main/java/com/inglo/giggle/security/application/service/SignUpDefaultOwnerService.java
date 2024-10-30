package com.inglo.giggle.security.application.service;

import com.inglo.giggle.account.domain.service.OwnerService;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.security.application.usecase.SignUpDefaultOwnerUseCase;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.redis.TemporaryToken;
import com.inglo.giggle.security.domain.redis.TemporaryAccount;
import com.inglo.giggle.security.domain.service.TemporaryAccountService;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.application.dto.request.SignUpDefaultOwnerRequestDto;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
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

    private final TemporaryAccountService temporaryAccountService;
    private final AddressService addressService;
    private final OwnerService ownerService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final S3Util s3Util;
    @Override
    @Transactional
    public void execute(SignUpDefaultOwnerRequestDto requestDto, MultipartFile file) {
        // temporary Token 검증. Redis에 있는 토큰인지 확인
        TemporaryToken temporaryToken = temporaryTokenRepository.findByValue(requestDto.temporaryToken())
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR));

        // Redis에서 임시 사용자 정보 가져오기
        TemporaryAccount tempUserInfo = temporaryAccountRepository.findById(temporaryToken.getCompositeKey())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TEMPORARY_ACCOUNT));

        // AccountType 검증
        temporaryAccountService.validateAccountTypeOwner(tempUserInfo);

        // 아이콘 이미지 저장
        String iconUrl = s3Util.getOwnerDefaultImgUrl();
        if (file != null) {
            iconUrl = s3Util.uploadImageFile(file, tempUserInfo.getId(), EImageType.OWNER_PROFILE_IMG);
        }

        // Address 생성
        Address address = addressService.createAddress(
                requestDto.address().addressName(),
                requestDto.address().region1DepthName(),
                requestDto.address().region2DepthName(),
                requestDto.address().region3DepthName(),
                requestDto.address().region4DepthName(),
                requestDto.address().addressDetail(),
                requestDto.address().latitude(),
                requestDto.address().longitude()
        );

        // Owner 생성 및 저장
        Account account = ownerService.createOwner(
                ESecurityProvider.DEFAULT,
                tempUserInfo,
                bCryptPasswordEncoder.encode(tempUserInfo.getPassword()),
                iconUrl, requestDto, address
        );
        accountRepository.save(account);

        // temporary Token 삭제
        temporaryTokenRepository.deleteById(temporaryToken.getCompositeKey());

        // temporary User Info 삭제
        temporaryAccountRepository.deleteById(tempUserInfo.getCompositeKey());

    }
}
