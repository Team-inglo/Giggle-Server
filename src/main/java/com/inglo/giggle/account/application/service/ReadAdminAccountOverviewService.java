package com.inglo.giggle.account.application.service;

import com.inglo.giggle.account.application.dto.response.ReadAdminAccountOverviewResponseDto;
import com.inglo.giggle.account.application.usecase.ReadAdminAccountOverviewUseCase;
import com.inglo.giggle.core.dto.PageInfoDto;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReadAdminAccountOverviewService implements ReadAdminAccountOverviewUseCase {

    private final AccountRepository accountRepository;

    @Override
    public ReadAdminAccountOverviewResponseDto execute(
            Integer page,
            Integer size,
            String search,
            LocalDate startDate,
            LocalDate endDate,
            String filterType,
            String filter,
            String sortType,
            Direction sort
    ) {

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Account> accountPage = accountRepository.findAccountByFilter(
                pageable,
                search,
                startDate,
                endDate,
                filterType,
                filter,
                sortType,
                sort
        );

        PageInfoDto pageInfo = PageInfoDto.of(
                accountPage.getNumber() + 1,
                accountPage.getContent().size(),
                accountPage.getSize(),
                accountPage.getTotalPages(),
                (int) accountPage.getTotalElements()
        );

        return ReadAdminAccountOverviewResponseDto.of(
                accountPage.getContent(),
                pageInfo
        );
    }
}
