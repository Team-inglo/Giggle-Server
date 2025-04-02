package com.inglo.giggle.security.config;

import com.inglo.giggle.account.domain.Admin;
import com.inglo.giggle.account.persistence.entity.AdminEntity;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.entity.mysql.AccountEntity;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
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

            Account accountEntity = accountRepository.findBySerialIdOrElseNull(superUserSerialId);

            if (accountEntity != null) {
                log.info("관리자가 이미 생성되어 있습니다.");
                return;
            }
            Account superUser = Admin.builder()
                    .provider(ESecurityProvider.DEFAULT)
                    .serialId(superUserSerialId)
                    .password(passwordEncoder.encode(superUserPassword))
                    .email("")
                    .build();
            accountRepository.save(superUser);
            log.info("관리자가 생성되었습니다.");
        };
    }
}

