package com.inglo.giggle.user.application.service;

import com.inglo.giggle.user.application.port.in.command.CreateUserCommand;
import com.inglo.giggle.user.application.port.in.usecase.CreateUserUseCase;
import com.inglo.giggle.user.application.port.out.CreateUserPort;
import com.inglo.giggle.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

    private final CreateUserPort createUserPort;

    @Override
    public void execute(CreateUserCommand command) {
        User user = User.builder()
                .email(command.getEmail())
                .profileImgUrl(command.getProfileImgUrl())
                .phoneNumber(command.getPhoneNumber())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .gender(command.getGender())
                .nationality(command.getNationality())
                .language(command.getLanguage())
                .birth(command.getBirth())
                .visa(command.getVisa())
                .marketingAllowed(command.getMarketingAllowed())
                .notificationAllowed(command.getNotificationAllowed())
                .address(command.getAddress())
                .build();
        createUserPort.createUser(user);
    }
}
