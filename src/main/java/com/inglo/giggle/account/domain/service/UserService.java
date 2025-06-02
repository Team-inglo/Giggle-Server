package com.inglo.giggle.account.domain.service;

import com.inglo.giggle.account.application.dto.request.UpdateUserRequestDto;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.domain.type.ELanguage;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.ENationality;
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
                .serialId(temporaryAccount.getEmail())
                .password(password)
                .email(temporaryAccount.getEmail())
                .profileImgUrl(profileImgUrl)
                .phoneNumber(signUpDefaultUserRequestDto.signUpDefaultUserUserInfo().phoneNumber())
                .firstName(signUpDefaultUserRequestDto.signUpDefaultUserUserInfo().firstName())
                .lastName(signUpDefaultUserRequestDto.signUpDefaultUserUserInfo().lastName())
                .gender(EGender.fromString(signUpDefaultUserRequestDto.signUpDefaultUserUserInfo().gender()))
                .nationality(ENationality.fromString(signUpDefaultUserRequestDto.signUpDefaultUserUserInfo().nationality()))
                .language(ELanguage.fromString(signUpDefaultUserRequestDto.language()))
                .birth(signUpDefaultUserRequestDto.signUpDefaultUserUserInfo().birth())
                .visa(EVisa.fromString(signUpDefaultUserRequestDto.signUpDefaultUserUserInfo().visa()))
                .marketingAllowed(signUpDefaultUserRequestDto.marketingAllowed())
                .notificationAllowed(signUpDefaultUserRequestDto.notificationAllowed())
                .address(address)
                .build();
    }

    public User updateUser(User user, UpdateUserRequestDto requestDto) {
        user.updateFirstName(requestDto.firstName());
        user.updateLastName(requestDto.lastName());
        user.updateGender(EGender.fromString(requestDto.gender()));
        user.updateNationality(ENationality.fromString(requestDto.nationality()));
        user.updateVisa(EVisa.fromString(requestDto.visa()));
        user.updateBirth(requestDto.birth());
        user.updateAddress(requestDto.address() != null ? requestDto.address().toEntity() : null);

        return user;
    }

    public User updateLanguage(User user, String language) {
        user.updateLanguage(ELanguage.fromString(language));
        return user;
    }
}
