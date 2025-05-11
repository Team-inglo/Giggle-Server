package com.inglo.giggle.security.account.application.port.out;

import com.inglo.giggle.security.account.domain.Account;

import java.util.UUID;

public interface UpdateAccountPort {

    Account updateAccount(Account account);
}
