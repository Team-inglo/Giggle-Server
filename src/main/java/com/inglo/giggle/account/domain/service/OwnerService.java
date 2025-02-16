package com.inglo.giggle.account.domain.service;

import com.inglo.giggle.account.application.dto.request.UpdateOwnerRequestDto;
import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.security.application.dto.request.SignUpDefaultOwnerRequestDto;
import com.inglo.giggle.security.domain.redis.TemporaryAccount;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {
    public Owner createOwner(ESecurityProvider securityProvider,
                             TemporaryAccount temporaryAccount,
                             String password,
                             String iconImgUrl,
                             SignUpDefaultOwnerRequestDto signUpDefaultOwnerRequestDto,
                             Address address)
    {
        return Owner.builder()
                .provider(securityProvider)
                .serialId(temporaryAccount.getEmail())
                .password(password)
                .email(temporaryAccount.getEmail())
                .profileImgUrl(iconImgUrl)
                .phoneNumber(signUpDefaultOwnerRequestDto.signUpDefaultOwnerOwnerInfo().phoneNumber())
                .companyName(signUpDefaultOwnerRequestDto.signUpDefaultOwnerOwnerInfo().companyName())
                .ownerName(signUpDefaultOwnerRequestDto.signUpDefaultOwnerOwnerInfo().ownerName())
                .companyRegistrationNumber(signUpDefaultOwnerRequestDto.signUpDefaultOwnerOwnerInfo().companyRegistrationNumber())
                .marketingAllowed(signUpDefaultOwnerRequestDto.marketingAllowed())
                .notificationAllowed(signUpDefaultOwnerRequestDto.notificationAllowed())
                .address(address)
                .build();
    }

    public Owner updateOwner(Owner owner, UpdateOwnerRequestDto requestDto, Address address) {
        owner.updateCompanyName(requestDto.ownerInfo().companyName());
        owner.updateOwnerName(requestDto.ownerInfo().ownerName());
        owner.updateCompanyRegistrationNumber(requestDto.ownerInfo().companyRegistrationNumber());
        owner.updateAddress(address);

        return owner;
    }
}
