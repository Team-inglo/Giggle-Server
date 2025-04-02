package com.inglo.giggle.security.application.service;

import com.inglo.giggle.security.application.usecase.DeleteAccountUseCase;
import com.inglo.giggle.security.persistence.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteAccountService implements DeleteAccountUseCase {

    private final AccountRepository accountRepository;

    @Override
    public void execute(UUID accountId) {
        accountRepository.deleteById(accountId);
    }
}
