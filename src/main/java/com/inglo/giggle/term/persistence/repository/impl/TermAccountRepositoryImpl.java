package com.inglo.giggle.term.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.persistence.entity.mysql.AccountEntity;
import com.inglo.giggle.security.persistence.repository.mysql.AccountJpaRepository;
import com.inglo.giggle.term.domain.TermAccount;
import com.inglo.giggle.term.persistence.entity.TermAccountEntity;
import com.inglo.giggle.term.persistence.entity.TermEntity;
import com.inglo.giggle.term.persistence.mapper.TermAccountMapper;
import com.inglo.giggle.term.persistence.repository.TermAccountRepository;
import com.inglo.giggle.term.persistence.repository.mysql.TermAccountJpaRepository;
import com.inglo.giggle.term.persistence.repository.mysql.TermJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TermAccountRepositoryImpl implements TermAccountRepository {

    private final TermAccountJpaRepository termAccountJpaRepository;
    private final AccountJpaRepository accountJpaRepository;
    private final TermJpaRepository termJpaRepository;

    @Override
    public void saveAll(List<TermAccount> termAccounts) {
        List<TermAccountEntity> termAccountEntities = termAccounts.stream()
                        .map(termAccount -> {
                            AccountEntity accountEntity = accountJpaRepository.findById(termAccount.getAccountId())
                                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT));
                            TermEntity termEntity = termJpaRepository.findById(termAccount.getTermId())
                                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TERM));
                            TermAccountEntity termAccountEntity = TermAccountMapper.toEntity(termAccount);
                            termAccountEntity.fetchTermEntity(termEntity);
                            termAccountEntity.fetchAccountEntity(accountEntity);
                            return termAccountEntity;
                        })
                .toList();
        termAccountJpaRepository.saveAll(termAccountEntities);
    }
}
