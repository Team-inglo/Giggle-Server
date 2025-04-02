package com.inglo.giggle.term.persistence.repository.impl;

import com.inglo.giggle.term.domain.TermAccount;
import com.inglo.giggle.term.persistence.mapper.TermAccountMapper;
import com.inglo.giggle.term.persistence.repository.TermAccountRepository;
import com.inglo.giggle.term.persistence.repository.mysql.TermAccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TermAccountRepositoryImpl implements TermAccountRepository {

    private final TermAccountJpaRepository termAccountJpaRepository;

    @Override
    public void saveAll(List<TermAccount> termAccounts) {
        termAccountJpaRepository.saveAll(TermAccountMapper.toEntities(termAccounts));
    }
}
