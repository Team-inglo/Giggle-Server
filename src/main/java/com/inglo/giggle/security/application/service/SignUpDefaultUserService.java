package com.inglo.giggle.security.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.domain.service.UserService;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.JsonWebTokenUtil;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.service.LanguageSkillService;
import com.inglo.giggle.resume.domain.service.ResumeService;
import com.inglo.giggle.resume.repository.mysql.LanguageSkillRepository;
import com.inglo.giggle.security.application.dto.request.SignUpDefaultUserRequestDto;
import com.inglo.giggle.security.application.dto.response.DefaultJsonWebTokenDto;
import com.inglo.giggle.security.application.usecase.SignUpDefaultUserUseCase;
import com.inglo.giggle.security.domain.redis.TemporaryAccount;
import com.inglo.giggle.security.domain.redis.TemporaryToken;
import com.inglo.giggle.security.domain.service.TemporaryAccountService;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import com.inglo.giggle.security.repository.redis.TemporaryAccountRepository;
import com.inglo.giggle.security.repository.redis.TemporaryTokenRepository;
import com.inglo.giggle.term.domain.Term;
import com.inglo.giggle.term.domain.TermAccount;
import com.inglo.giggle.term.domain.service.TermAccountService;
import com.inglo.giggle.term.domain.type.ETermType;
import com.inglo.giggle.term.repository.mysql.TermAccountRepository;
import com.inglo.giggle.term.repository.mysql.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SignUpDefaultUserService implements SignUpDefaultUserUseCase {

    private final AccountRepository accountRepository;
    private final TemporaryTokenRepository temporaryTokenRepository;
    private final TemporaryAccountRepository temporaryAccountRepository;
    private final TermRepository termRepository;
    private final TermAccountRepository termAccountRepository;

    private final AddressService addressService;
    private final UserService userService;
    private final TemporaryAccountService temporaryAccountService;
    private final TermAccountService termAccountService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final S3Util s3Util;
    private final JsonWebTokenUtil jsonWebTokenUtil;
    private final ResumeService resumeService;
    private final LanguageSkillService languageSkillService;
    private final LanguageSkillRepository languageSkillRepository;

    @Override
    @Transactional
    public DefaultJsonWebTokenDto execute(SignUpDefaultUserRequestDto requestDto) {
        // temporary Token 검증. Redis에 있는 토큰인지 확인
        TemporaryToken temporaryToken = temporaryTokenRepository.findByValue(requestDto.temporaryToken())
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR));

        // Redis에서 임시 사용자 정보 가져오기
        TemporaryAccount tempUserInfo = temporaryAccountRepository.findById(temporaryToken.getEmail())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TEMPORARY_ACCOUNT));

        // AccountType 검증
        temporaryAccountService.validateAccountTypeUser(tempUserInfo);

        // Address 생성
        Address address = addressService.createAddress(
                requestDto.address() != null ? requestDto.address().addressName() : null,
                requestDto.address() != null ? requestDto.address().region1DepthName() : null,
                requestDto.address() != null ? requestDto.address().region2DepthName() : null,
                requestDto.address() != null ? requestDto.address().region3DepthName() : null,
                requestDto.address() != null ? requestDto.address().region4DepthName() : null,
                requestDto.address() != null ? requestDto.address().addressDetail() : null,
                requestDto.address() != null ? requestDto.address().latitude() : null,
                requestDto.address() != null ? requestDto.address().longitude() : null
        );

        // User 생성 및 저장
        User user = userService.createUser(
                ESecurityProvider.DEFAULT,
                tempUserInfo,
                bCryptPasswordEncoder.encode(tempUserInfo.getPassword()),
                s3Util.getUserDefaultImgUrl(), requestDto, address
        );
        User savedUser = accountRepository.save(user);

        // Resume, LanguageSkill 생성 및 저장
        Resume savedResume = resumeService.createResume(savedUser);
        LanguageSkill savedLanguageSkill = languageSkillService.createLanguageSkill(savedResume);
        languageSkillRepository.save(savedLanguageSkill);

        // temporary Token 삭제
        temporaryTokenRepository.deleteById(temporaryToken.getEmail());

        // temporary User Info 삭제
        temporaryAccountRepository.deleteById(tempUserInfo.getEmail());

        // 약관 타입 파싱
        List<ETermType> termTypes = requestDto.termTypes().stream()
                .map(ETermType::fromString)
                .toList();

        // Account TermType 검증
        termAccountService.validateAccountTermType(savedUser, termTypes);

        // Term 조회
        List<Term> terms = termRepository.findLatestTermsByTermType(termTypes);

        // 약관 동의 생성
        List<TermAccount> termAccounts = termAccountService.createTermAccount(savedUser, terms);

        // 약관 동의 저장
        termAccountRepository.saveAll(termAccounts);

        // JWT 발급
        return jsonWebTokenUtil.generateDefaultJsonWebTokens(savedUser.getId(), savedUser.getRole());
    }
}
