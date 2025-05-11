package com.inglo.giggle.security.config;

import com.inglo.giggle.admin.application.port.in.command.CreateAdminCommand;
import com.inglo.giggle.admin.application.port.in.usecase.CreateAdminUseCase;
import com.inglo.giggle.security.account.application.port.in.command.CreateAccountCommand;
import com.inglo.giggle.security.account.application.port.in.query.ValidateEmailQuery;
import com.inglo.giggle.security.account.application.port.in.result.ValidationResult;
import com.inglo.giggle.security.account.application.port.in.usecase.CreateAccountUseCase;
import com.inglo.giggle.security.account.domain.type.ESecurityProvider;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AdminConfig {

    @Value("${admin.id}")
    private String superUserSerialId;

    @Value("${admin.password}")
    private String superUserPassword;

    private final CreateAccountUseCase createAccountUseCase;
    private final CreateAdminUseCase createAdminUseCase;
    private final ValidateEmailQuery validateEmailQuery;
    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner createSuperUser() {
        return args -> {

            ValidationResult validationResult = validateEmailQuery.execute(superUserSerialId);

            if (!validationResult.getIsValid()) {
                log.info("관리자가 이미 생성되어 있습니다.");
                return;
            }

            // 계정 생성
            CreateAccountCommand createAccountCommand = new CreateAccountCommand(
                    ESecurityProvider.DEFAULT,
                    ESecurityRole.ADMIN,
                    superUserSerialId,
                    passwordEncoder.encode(superUserPassword)
            );
            UUID accountId = createAccountUseCase.execute(createAccountCommand);

            // 관리자 생성
            CreateAdminCommand createAdminCommand = new CreateAdminCommand(accountId);
            createAdminUseCase.execute(createAdminCommand);

            log.info("관리자가 생성되었습니다.");
        };
    }
}

