package com.inglo.giggle.security.application.service;

import com.inglo.giggle.account.domain.service.OwnerService;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.JsonWebTokenUtil;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.security.application.dto.response.DefaultJsonWebTokenDto;
import com.inglo.giggle.security.application.usecase.SignUpDefaultOwnerUseCase;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.redis.TemporaryToken;
import com.inglo.giggle.security.domain.redis.TemporaryAccount;
import com.inglo.giggle.security.domain.service.TemporaryAccountService;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.application.dto.request.SignUpDefaultOwnerRequestDto;
import com.inglo.giggle.security.repository.AccountRepository;
import com.inglo.giggle.security.repository.TemporaryTokenRepository;
import com.inglo.giggle.security.repository.TemporaryAccountRepository;
import com.inglo.giggle.term.domain.Term;
import com.inglo.giggle.term.domain.TermAccount;
import com.inglo.giggle.term.domain.service.TermAccountService;
import com.inglo.giggle.term.domain.type.ETermType;
import com.inglo.giggle.term.repository.TermAccountRepository;
import com.inglo.giggle.term.repository.TermRepository;
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
    private final AddressService addressService;
    private final OwnerService ownerService;
    private final TermAccountService termAccountService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final S3Util s3Util;
    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Override
    @Transactional
    public DefaultJsonWebTokenDto execute(SignUpDefaultOwnerRequestDto requestDto, MultipartFile file) {

        // temporary Token 검증. Redis에 있는 토큰인지 확인
        TemporaryToken temporaryToken = temporaryTokenRepository.findByValueOrElseThrow(requestDto.temporaryToken());

        // Redis에서 임시 사용자 정보 가져오기
        TemporaryAccount tempUserInfo = temporaryAccountRepository.findByIdOrElseThrow(temporaryToken.getEmail());

        // AccountType 검증
        temporaryAccountService.validateAccountTypeOwner(tempUserInfo);

        // 아이콘 이미지 저장
        String iconUrl = s3Util.getOwnerDefaultImgUrl();
        if (file != null) {
            iconUrl = s3Util.uploadImageFile(file, tempUserInfo.getEmail(), EImageType.OWNER_PROFILE_IMG);
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
        account = accountRepository.saveAndReturn(account);

        // temporary Token 삭제
        temporaryTokenRepository.deleteById(temporaryToken.getEmail());

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
