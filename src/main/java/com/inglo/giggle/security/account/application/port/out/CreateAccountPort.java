package com.inglo.giggle.security.account.application.port.out;

import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.domain.type.ESecurityProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CreateAccountPort {

    Account createAccount(Account account);
}
