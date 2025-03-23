package com.inglo.giggle.security.repository.mysql.querydsl;

import com.inglo.giggle.security.domain.mysql.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import java.time.LocalDate;

public interface AccountRepositoryQuery {

    Page<Account> findAccountByFilter(
            Pageable pageable,
            String search,
            LocalDate startDate,
            LocalDate endDate,
            String filterType,
            String filter,
            String sortType,
            Direction sort
    );
}
