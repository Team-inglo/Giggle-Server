package com.inglo.giggle.security.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.domain.service.UserService;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.utility.ImageUtil;
import com.inglo.giggle.resume.domain.LanguageSkill;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.domain.service.LanguageSkillService;
import com.inglo.giggle.resume.domain.service.ResumeService;
import com.inglo.giggle.resume.repository.mysql.LanguageSkillRepository;
import com.inglo.giggle.security.application.dto.request.SignUpDefaultUserRequestDto;
import com.inglo.giggle.security.application.usecase.SignUpDefaultUserUseCase;
import com.inglo.giggle.security.domain.redis.TemporaryAccount;
import com.inglo.giggle.security.domain.redis.TemporaryToken;
import com.inglo.giggle.security.domain.service.TemporaryAccountService;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import com.inglo.giggle.security.repository.redis.TemporaryAccountRepository;
import com.inglo.giggle.security.repository.redis.TemporaryTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpDefaultUserService implements SignUpDefaultUserUseCase {

    private final AccountRepository accountRepository;

    private final TemporaryTokenRepository temporaryTokenRepository;
    private final TemporaryAccountRepository temporaryAccountRepository;

    private final AddressService addressService;
    private final UserService userService;
    private final TemporaryAccountService temporaryAccountService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ImageUtil imageUtil;
    private final ResumeService resumeService;
    private final LanguageSkillService languageSkillService;
    private final LanguageSkillRepository languageSkillRepository;

    @Override
    @Transactional
    public void execute(SignUpDefaultUserRequestDto requestDto) {
        // temporary Token 검증. Redis에 있는 토큰인지 확인
        TemporaryToken temporaryToken = temporaryTokenRepository.findByValue(requestDto.temporaryToken())
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR));

        // Redis에서 임시 사용자 정보 가져오기
        TemporaryAccount tempUserInfo = temporaryAccountRepository.findById(temporaryToken.getCompositeKey())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TEMPORARY_ACCOUNT));

        // AccountType 검증
        temporaryAccountService.validateAccountTypeUser(tempUserInfo);

        // Address 생성
        Address address = addressService.createAddress(requestDto.address());

        // User 생성 및 저장
        User user = userService.createUser(
                ESecurityProvider.DEFAULT,
                tempUserInfo,
                bCryptPasswordEncoder.encode(tempUserInfo.getPassword()),
                imageUtil.getUserDefaultImgUrl(), requestDto, address
        );
        User savedUser = accountRepository.save(user);

        // Resume, LanguageSkill 생성 및 저장
        Resume savedResume = resumeService.createResume(savedUser);
        LanguageSkill savedLanguageSkill = languageSkillService.createLanguageSkill(savedResume);
        languageSkillRepository.save(savedLanguageSkill);


        // temporary Token 삭제
        temporaryTokenRepository.deleteById(temporaryToken.getCompositeKey());

        // temporary User Info 삭제
        temporaryAccountRepository.deleteById(tempUserInfo.getCompositeKey());

    }
}
