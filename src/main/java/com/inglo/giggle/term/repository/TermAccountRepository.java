package com.inglo.giggle.term.repository;

import com.inglo.giggle.term.domain.TermAccount;

import java.util.List;

public interface TermAccountRepository {

    TermAccount findByIdOrElseThrow(Long id);

    void save(TermAccount termAccount);

    void saveAll(List<TermAccount> termAccounts);

    void delete(TermAccount termAccount);
}
