package com.inglo.giggle.account.domain.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.domain.type.ELanguage;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.security.application.dto.request.SignUpDefaultUserRequestDto;
import com.inglo.giggle.security.domain.redis.TemporaryAccount;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public User createUser(ESecurityProvider securityProvider,
                           TemporaryAccount temporaryAccount,
                           String password,
                           String profileImgUrl,
                           SignUpDefaultUserRequestDto signUpDefaultUserRequestDto,
                           Address address)
    {
        return User.builder()
                .provider(securityProvider)
                .serialId(temporaryAccount.getId())
                .password(password)
                .email(temporaryAccount.getEmail())
                .profileImgUrl(profileImgUrl)
                .phoneNumber(signUpDefaultUserRequestDto.signUpDefaultUserUserInfo().phoneNumber())
                .firstName(signUpDefaultUserRequestDto.signUpDefaultUserUserInfo().firstName())
                .lastName(signUpDefaultUserRequestDto.signUpDefaultUserUserInfo().lastName())
                .gender(EGender.fromString(signUpDefaultUserRequestDto.signUpDefaultUserUserInfo().gender()))
                .nationality(signUpDefaultUserRequestDto.signUpDefaultUserUserInfo().nationality())
                .language(ELanguage.fromString(signUpDefaultUserRequestDto.language()))
                .birth(signUpDefaultUserRequestDto.signUpDefaultUserUserInfo().birth())
                .visa(EVisa.fromString(signUpDefaultUserRequestDto.signUpDefaultUserUserInfo().visa()))
                .marketingAllowed(signUpDefaultUserRequestDto.marketingAllowed())
                .notificationAllowed(signUpDefaultUserRequestDto.notificationAllowed())
                .address(address)
                .build();
    }
}
