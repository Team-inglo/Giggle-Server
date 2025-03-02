package com.inglo.giggle.security.config;

import com.inglo.giggle.account.domain.Admin;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AdminConfig {

    @Value("${admin.id}")
    private String superUserSerialId;

    @Value("${admin.password}")
    private String superUserPassword;

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner createSuperUser() {
        return args -> {

            accountRepository.findBySerialId(superUserSerialId).ifPresentOrElse(
                    user -> {
                        log.info("관리자가 이미 생성되어 있습니다.");
                    },
                    () -> {
                        Account superUser = Admin.builder()
                                .provider(ESecurityProvider.DEFAULT)
                                .serialId(superUserSerialId)
                                .password(passwordEncoder.encode(superUserPassword))
                                .email("")
                                .profileImgUrl("default.jpg")
                                .phoneNumber("010-0000-0000")
                                .marketingAllowed(false)
                                .notificationAllowed(false)
                                .build();
                        accountRepository.save(superUser);
                        log.info("관리자가 생성되었습니다.");
                    }
            );
        };
    }
}

