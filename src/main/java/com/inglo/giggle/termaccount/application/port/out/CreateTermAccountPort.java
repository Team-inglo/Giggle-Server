package com.inglo.giggle.termaccount.application.port.out;

import com.inglo.giggle.termaccount.domain.TermAccount;

import java.util.List;

public interface CreateTermAccountPort {

    void createTermAccounts(List<TermAccount> termAccounts);
}
