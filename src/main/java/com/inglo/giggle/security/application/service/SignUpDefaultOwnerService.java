package com.inglo.giggle.security.application.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.JsonWebTokenUtil;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.security.application.usecase.SignUpDefaultOwnerUseCase;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.domain.service.TemporaryAccountService;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.persistence.entity.redis.TemporaryAccountEntity;
import com.inglo.giggle.security.persistence.entity.redis.TemporaryTokenEntity;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import com.inglo.giggle.security.persistence.repository.TemporaryAccountRepository;
import com.inglo.giggle.security.persistence.repository.TemporaryTokenRepository;
import com.inglo.giggle.security.presentation.dto.request.SignUpDefaultOwnerRequestDto;
import com.inglo.giggle.security.presentation.dto.response.DefaultJsonWebTokenDto;
import com.inglo.giggle.term.domain.Term;
import com.inglo.giggle.term.domain.TermAccount;
import com.inglo.giggle.term.domain.service.TermAccountService;
import com.inglo.giggle.term.domain.type.ETermType;
import com.inglo.giggle.term.persistence.repository.TermAccountRepository;
import com.inglo.giggle.term.persistence.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SignUpDefaultOwnerService implements SignUpDefaultOwnerUseCase {

    private final AccountRepository accountRepository;
    private final TemporaryTokenRepository temporaryTokenRepository;
    private final TemporaryAccountRepository temporaryAccountRepository;
    private final TermRepository termRepository;
    private final TermAccountRepository termAccountRepository;

    private final TemporaryAccountService temporaryAccountService;
    private final TermAccountService termAccountService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final S3Util s3Util;
    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Override
    @Transactional
    public DefaultJsonWebTokenDto execute(SignUpDefaultOwnerRequestDto requestDto, MultipartFile file) {

        // temporary Token 검증. Redis에 있는 토큰인지 확인
        TemporaryTokenEntity temporaryTokenEntity = temporaryTokenRepository.findByValueOrElseThrow(requestDto.temporaryToken());

        // Redis에서 임시 사용자 정보 가져오기
        TemporaryAccountEntity tempUserInfo = temporaryAccountRepository.findByIdOrElseThrow(temporaryTokenEntity.getEmail());

        // AccountType 검증
        temporaryAccountService.validateAccountTypeOwner(tempUserInfo);

        // 아이콘 이미지 저장
        String iconUrl = s3Util.getOwnerDefaultImgUrl();
        if (file != null) {
            iconUrl = s3Util.uploadImageFile(file, tempUserInfo.getEmail(), EImageType.OWNER_PROFILE_IMG);
        }

        // Address 생성
        Address address;

        if (requestDto.address().addressName() == null || requestDto.address().addressName().isEmpty()) {
            address = null;
        } else {
            address = Address.builder()
                    .addressName(requestDto.address().addressName())
                    .addressDetail(requestDto.address().addressDetail())
                    .region1DepthName(requestDto.address().region1DepthName())
                    .region2DepthName(requestDto.address().region2DepthName())
                    .region3DepthName(requestDto.address().region3DepthName())
                    .region4DepthName(requestDto.address().region4DepthName())
                    .latitude(requestDto.address().latitude())
                    .longitude(requestDto.address().longitude())
                    .build();
        }

        // Owner 생성 및 저장
        Account account = Owner.builder()
                .provider(ESecurityProvider.DEFAULT)
                .serialId(tempUserInfo.getEmail())
                .password(bCryptPasswordEncoder.encode(tempUserInfo.getPassword()))
                .email(tempUserInfo.getEmail())
                .profileImgUrl(iconUrl)
                .phoneNumber(requestDto.signUpDefaultOwnerOwnerInfo().phoneNumber())
                .companyName(requestDto.signUpDefaultOwnerOwnerInfo().companyName())
                .ownerName(requestDto.signUpDefaultOwnerOwnerInfo().ownerName())
                .companyRegistrationNumber(requestDto.signUpDefaultOwnerOwnerInfo().companyRegistrationNumber())
                .marketingAllowed(requestDto.marketingAllowed())
                .notificationAllowed(requestDto.notificationAllowed())
                .address(address)
                .build();

        account = accountRepository.save(account);

        // temporary Token 삭제
        temporaryTokenRepository.deleteById(temporaryTokenEntity.getEmail());

        // temporary User Info 삭제
        temporaryAccountRepository.deleteById(tempUserInfo.getEmail());

        // 약관 타입 파싱
        List<ETermType> termTypes = requestDto.termTypes().stream()
                .map(ETermType::fromString)
                .toList();

        // Account TermType 검증
        termAccountService.validateAccountTermType(account, termTypes);

        // Term 조회
        List<Term> terms = termRepository.findLatestTermsByTermType(termTypes);

        // 약관 동의 생성
        List<TermAccount> termAccounts = termAccountService.createTermAccount(account, terms);

        // 약관 동의 저장
        termAccountRepository.saveAll(termAccounts);

        return jsonWebTokenUtil.generateDefaultJsonWebTokens(account.getId(), account.getRole());
    }
}
