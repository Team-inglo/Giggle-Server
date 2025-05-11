package com.inglo.giggle.security.temporaryaccount.application.port.out;

import com.inglo.giggle.security.temporaryaccount.domain.TemporaryAccount;

public interface CreateTemporaryAccountPort {

    void createTemporaryAccount(TemporaryAccount temporaryAccount);
}
