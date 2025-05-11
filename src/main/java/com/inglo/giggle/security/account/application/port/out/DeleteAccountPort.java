package com.inglo.giggle.security.account.application.port.out;

import com.inglo.giggle.security.account.domain.Account;

public interface DeleteAccountPort {

    void deleteAccount(Account account);
}
