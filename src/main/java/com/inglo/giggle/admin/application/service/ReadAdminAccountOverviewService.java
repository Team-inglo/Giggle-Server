package com.inglo.giggle.admin.application.service;

import com.inglo.giggle.admin.application.port.in.query.ReadAdminAccountOverviewQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadAdminAccountOverviewService implements ReadAdminAccountOverviewQuery {
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
