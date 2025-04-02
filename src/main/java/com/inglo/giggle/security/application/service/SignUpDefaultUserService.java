package com.inglo.giggle.security.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.domain.type.ELanguage;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.core.utility.JsonWebTokenUtil;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.persistence.repository.LanguageSkillRepository;
import com.inglo.giggle.resume.persistence.repository.ResumeRepository;
import com.inglo.giggle.security.application.usecase.SignUpDefaultUserUseCase;
import com.inglo.giggle.security.domain.service.TemporaryAccountService;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.persistence.entity.redis.TemporaryAccountEntity;
import com.inglo.giggle.security.persistence.entity.redis.TemporaryTokenEntity;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import com.inglo.giggle.security.persistence.repository.TemporaryAccountRepository;
import com.inglo.giggle.security.persistence.repository.TemporaryTokenRepository;
import com.inglo.giggle.security.presentation.dto.request.SignUpDefaultUserRequestDto;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class SignUpDefaultUserService implements SignUpDefaultUserUseCase {

    private final AccountRepository accountRepository;
    private final TemporaryTokenRepository temporaryTokenRepository;
    private final TemporaryAccountRepository temporaryAccountRepository;
    private final TermRepository termRepository;
    private final TermAccountRepository termAccountRepository;
    private final ResumeRepository resumeRepository;

    private final TemporaryAccountService temporaryAccountService;
    private final TermAccountService termAccountService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final S3Util s3Util;
    private final JsonWebTokenUtil jsonWebTokenUtil;
    private final LanguageSkillRepository languageSkillRepository;

    @Override
    @Transactional
    public DefaultJsonWebTokenDto execute(SignUpDefaultUserRequestDto requestDto) {
        // temporary Token 검증. Redis에 있는 토큰인지 확인
        TemporaryTokenEntity temporaryTokenEntity = temporaryTokenRepository.findByValueOrElseThrow(requestDto.temporaryToken());

        // Redis에서 임시 사용자 정보 가져오기
        TemporaryAccountEntity tempUserInfo = temporaryAccountRepository.findByIdOrElseThrow(temporaryTokenEntity.getEmail());

        // AccountType 검증
        temporaryAccountService.validateAccountTypeUser(tempUserInfo);

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

        // User 생성 및 저장
        User user = User.builder()
                .provider(ESecurityProvider.DEFAULT)
                .serialId(tempUserInfo.getEmail())
                .password(bCryptPasswordEncoder.encode(tempUserInfo.getPassword()))
                .email(tempUserInfo.getEmail())
                .profileImgUrl(s3Util.getUserDefaultImgUrl())
                .phoneNumber(requestDto.signUpDefaultUserUserInfo().phoneNumber())
                .firstName(requestDto.signUpDefaultUserUserInfo().firstName())
                .lastName(requestDto.signUpDefaultUserUserInfo().lastName())
                .gender(EGender.fromString(requestDto.signUpDefaultUserUserInfo().gender()))
                .nationality(requestDto.signUpDefaultUserUserInfo().nationality())
                .language(ELanguage.fromString(requestDto.language()))
                .birth(requestDto.signUpDefaultUserUserInfo().birth())
                .visa(EVisa.fromString(requestDto.signUpDefaultUserUserInfo().visa()))
                .marketingAllowed(requestDto.marketingAllowed())
                .notificationAllowed(requestDto.notificationAllowed())
                .address(address)
                .build();

        User savedUser = (User) accountRepository.save(user);

        // Resume, LanguageSkill 생성 및 저장
        Resume resume = Resume.builder()
                .accountId(savedUser.getId())
                .build();

        Resume savedResume = resumeRepository.save(resume);

        LanguageSkill savedLanguageSkill = LanguageSkill.builder()
                .resumeId(savedResume.getAccountId())
                .build();
        languageSkillRepository.save(savedLanguageSkill);

        // temporary Token 삭제
        temporaryTokenRepository.deleteById(temporaryTokenEntity.getEmail());

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
        List<TermAccount> termAccountEntities = termAccountService.createTermAccount(savedUser, terms);

        // 약관 동의 저장
        termAccountRepository.saveAll(termAccountEntities);

        // JWT 발급
        return jsonWebTokenUtil.generateDefaultJsonWebTokens(savedUser.getId(), savedUser.getRole());
    }
}
