package com.inglo.giggle.term.repository.impl;

import com.inglo.giggle.term.domain.TermAccount;
import com.inglo.giggle.term.repository.TermAccountRepository;
import com.inglo.giggle.term.repository.mysql.TermAccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TermAccountRepositoryImpl implements TermAccountRepository {

    private final TermAccountJpaRepository termAccountJpaRepository;

    @Override
    public void saveAll(List<TermAccount> termAccounts) {
        termAccountJpaRepository.saveAll(termAccounts);
    }
}
