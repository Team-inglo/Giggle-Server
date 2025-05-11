package com.inglo.giggle.termaccount.adapter.out.persistence;

import com.inglo.giggle.termaccount.adapter.out.persistence.entity.TermAccountEntity;
import com.inglo.giggle.termaccount.adapter.out.persistence.mapper.TermAccountMapper;
import com.inglo.giggle.termaccount.adapter.out.persistence.repository.mysql.TermAccountJpaRepository;
import com.inglo.giggle.termaccount.application.port.out.CreateTermAccountPort;
import com.inglo.giggle.termaccount.domain.TermAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TermAccountPersistenceAdapter implements CreateTermAccountPort {

    private final TermAccountJpaRepository termAccountJpaRepository;
    private final TermAccountMapper termAccountMapper;

    @Override
    public void createTermAccounts(List<TermAccount> termAccounts) {
        List<TermAccountEntity> termAccountEntities = termAccounts.stream()
                .map(termAccountMapper::toEntity)
                .toList();

        termAccountJpaRepository.saveAll(termAccountEntities);
    }
}
