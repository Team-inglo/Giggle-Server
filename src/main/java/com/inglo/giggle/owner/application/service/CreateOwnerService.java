package com.inglo.giggle.owner.application.service;

import com.inglo.giggle.owner.application.port.in.command.CreateOwnerCommand;
import com.inglo.giggle.owner.application.port.in.usecase.CreateOwnerUseCase;
import com.inglo.giggle.owner.application.port.out.CreateOwnerPort;
import com.inglo.giggle.owner.domain.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateOwnerService implements CreateOwnerUseCase {

    private final CreateOwnerPort createOwnerPort;

    @Override
    public void execute(CreateOwnerCommand command) {
        Owner owner = Owner.builder()
                .email(command.getEmail())
                .profileImgUrl(command.getProfileImgUrl())
                .phoneNumber(command.getPhoneNumber())
                .companyName(command.getCompanyName())
                .ownerName(command.getOwnerName())
                .companyRegistrationNumber(command.getCompanyRegistrationNumber())
                .marketingAllowed(command.getMarketingAllowed())
                .notificationAllowed(command.getNotificationAllowed())
                .address(command.getAddress())
                .createdAt(LocalDateTime.now())
                .build();
        createOwnerPort.createOwner(owner);
    }
}
