package com.inglo.giggle.term.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
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
    public TermAccount findByIdOrElseThrow(Long id) {
        return termAccountJpaRepository.findById(id).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TERM_ACCOUNT));
    }

    @Override
    public void save(TermAccount termAccount) {
        termAccountJpaRepository.save(termAccount);
    }

    @Override
    public void saveAll(List<TermAccount> termAccounts) {
        termAccountJpaRepository.saveAll(termAccounts);
    }

    @Override
    public void delete(TermAccount termAccount) {
        termAccountJpaRepository.delete(termAccount);
    }
}
