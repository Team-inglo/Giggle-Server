package com.inglo.giggle.admin.application.service;

import com.inglo.giggle.admin.application.port.in.query.ReadAdminAccountSignUpOverviewQuery;
import com.inglo.giggle.admin.application.port.in.result.ReadAdminAccountSignUpOverviewResult;
import com.inglo.giggle.core.dto.CountInfoDto;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccumulatedAccountQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccumulatedAccountResult;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAdminAccountSignUpOverviewService implements ReadAdminAccountSignUpOverviewQuery {

    private final ReadAccumulatedAccountQuery readAccumulatedAccountQuery;

    @Override
    public ReadAdminAccountSignUpOverviewResult execute(LocalDate startDate, LocalDate endDate) {

        int duration = startDate.until(endDate).getDays();

        LocalDate priorStartDate = startDate.minusDays(duration);
        LocalDate priorEndDate = endDate.minusDays(duration);

        ReadAccumulatedAccountResult accumulatedAccounts = readAccumulatedAccountQuery.execute(endDate.plusDays(1).atStartOfDay());

        List<ReadAccumulatedAccountResult.AccountDto> accumulatedUsers = accumulatedAccounts.getAccountDtos().stream()
                .filter(account -> account.getRole() == ESecurityRole.USER)
                .map(account -> new ReadAccumulatedAccountResult.AccountDto(
                        account.getAccountId(),
                        account.getRole(),
                        account.getCreatedAt()))
                .toList();

        List<ReadAccumulatedAccountResult.AccountDto> accumulatedOwners = accumulatedAccounts.getAccountDtos().stream()
                .filter(account -> account.getRole() == ESecurityRole.OWNER)
                .map(account -> new ReadAccumulatedAccountResult.AccountDto(
                        account.getAccountId(),
                        account.getRole(),
                        account.getCreatedAt()))
                .toList();

        List<ReadAccumulatedAccountResult.AccountDto> newUsers = accumulatedUsers.stream()
                .filter(user -> user.getCreatedAt().isAfter(startDate.atStartOfDay()))
                .toList();

        List<ReadAccumulatedAccountResult.AccountDto> newOwners = accumulatedOwners.stream()
                .filter(owner -> owner.getCreatedAt().isAfter(startDate.atStartOfDay()))
                .toList();

        List<ReadAccumulatedAccountResult.AccountDto> priorAccumulatedUsers = accumulatedUsers.stream()
                .filter(user -> user.getCreatedAt().isBefore(startDate.atStartOfDay()))
                .toList();

        List<ReadAccumulatedAccountResult.AccountDto> priorAccumulatedOwners = accumulatedOwners.stream()
                .filter(owner -> owner.getCreatedAt().isBefore(startDate.atStartOfDay()))
                .toList();

        List<ReadAccumulatedAccountResult.AccountDto> priorUsers = accumulatedUsers.stream()
                .filter(user -> user.getCreatedAt().isAfter(priorStartDate.atStartOfDay()) && user.getCreatedAt().isBefore(priorEndDate.atStartOfDay()))
                .toList();

        List<ReadAccumulatedAccountResult.AccountDto> priorOwners = accumulatedOwners.stream()
                .filter(owner -> owner.getCreatedAt().isAfter(priorStartDate.atStartOfDay()) && owner.getCreatedAt().isBefore(priorEndDate.atStartOfDay()))
                .toList();

        CountInfoDto userSignUpInfo = CountInfoDto.of(newUsers.size(), priorUsers.size(), calculatePercentage(newUsers.size(), priorUsers.size()));
        CountInfoDto accumulatedUserSignUpInfo = CountInfoDto.of(accumulatedUsers.size(), priorAccumulatedUsers.size(), calculatePercentage(accumulatedUsers.size(), priorAccumulatedUsers.size()));
        CountInfoDto ownerSignUpInfo = CountInfoDto.of(newOwners.size(), priorOwners.size(), calculatePercentage(newOwners.size(), priorOwners.size()));
        CountInfoDto accumulatedOwnerSignUpInfo = CountInfoDto.of(accumulatedOwners.size(), priorAccumulatedOwners.size(), calculatePercentage(accumulatedOwners.size(), priorAccumulatedOwners.size()));

        return ReadAdminAccountSignUpOverviewResult.of(userSignUpInfo, accumulatedUserSignUpInfo, ownerSignUpInfo, accumulatedOwnerSignUpInfo);
    }

    private double calculatePercentage(int current, int prior) {
        if (current == 0 && prior == 0) return 0.0;
        if (prior == 0) return 100.00;
        double raw = ((double) (current - prior) / prior) * 100;
        return Math.round(raw * 100.0) / 100.0;
    }
}
