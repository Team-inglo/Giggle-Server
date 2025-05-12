package com.inglo.giggle.security.account.application.port.out;

import com.inglo.giggle.security.account.domain.Account;

public interface UpdateAccountPort {

    void updateAccount(Account account);
}
