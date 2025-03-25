package com.inglo.giggle.term.application.service;

import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.repository.AccountRepository;
import com.inglo.giggle.term.application.dto.request.CreateTermAccountRequestDto;
import com.inglo.giggle.term.application.usecase.CreateTermAccountUseCase;
import com.inglo.giggle.term.domain.Term;
import com.inglo.giggle.term.domain.TermAccount;
import com.inglo.giggle.term.domain.service.TermAccountService;
import com.inglo.giggle.term.domain.type.ETermType;
import com.inglo.giggle.term.repository.TermAccountRepository;
import com.inglo.giggle.term.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateTermAccountService implements CreateTermAccountUseCase {

    private final AccountRepository accountRepository;
    private final TermRepository termRepository;
    private final TermAccountRepository termAccountRepository;

    private final TermAccountService termAccountService;

    @Override
    public void execute(UUID accountId, CreateTermAccountRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findByIdOrElseThrow(accountId);

        // 약관 타입 파싱
        List<ETermType> termTypes = requestDto.termTypes().stream()
                .map(ETermType::fromString)
                .toList();

        // Account TermType 검증
        termAccountService.validateAccountTermType(account, termTypes);

        // Term 조회
        List<Term> terms = termRepository.findLatestTermsByTermType(termTypes);

        // 약관 동의 생성
        List<TermAccount> termAccounts = termAccountService.createTermAccount(account, terms);

        // 약관 동의 저장
        termAccountRepository.saveAll(termAccounts);

    }
}
