package com.inglo.giggle.term.repository;

import com.inglo.giggle.term.domain.TermAccount;

import java.util.List;

public interface TermAccountRepository {

    void saveAll(List<TermAccount> termAccounts);
}
