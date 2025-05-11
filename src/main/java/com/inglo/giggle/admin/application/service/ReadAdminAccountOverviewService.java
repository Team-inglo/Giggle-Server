package com.inglo.giggle.admin.application.service;

import com.inglo.giggle.admin.application.port.in.result.ReadAdminAccountOverviewResponseDto;
import com.inglo.giggle.admin.application.port.in.query.ReadAdminAccountOverviewUseCase;
import com.inglo.giggle.core.dto.PageInfoDto;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
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
// TODO: 어케해야하노

//    private final LoadAccountPort loadAccountPort;
//
//    @Override
//    public ReadAdminAccountOverviewResponseDto execute(
//            Integer page,
//            Integer size,
//            String search,
//            LocalDate startDate,
//            LocalDate endDate,
//            String filterType,
//            String filter,
//            String sortType,
//            Direction sort
//    ) {
//
//        Pageable pageable = PageRequest.of(page - 1, size);
//
//        Page<Account> accountPage = loadAccountPort.loadAccounts(
//                pageable,
//                search,
//                startDate,
//                endDate,
//                filterType,
//                filter,
//                sortType,
//                sort
//        );
//
//        PageInfoDto pageInfo = PageInfoDto.of(
//                accountPage.getNumber() + 1,
//                accountPage.getContent().size(),
//                accountPage.getSize(),
//                accountPage.getTotalPages(),
//                (int) accountPage.getTotalElements()
//        );
//
//        return ReadAdminAccountOverviewResponseDto.of(
//                accountPage.getContent(),
//                pageInfo
//        );
//    }
}
