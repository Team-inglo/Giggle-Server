package com.inglo.giggle.account.application.service;

import com.inglo.giggle.account.application.dto.response.ReadAdminAccountSignUpOverviewResponseDto;
import com.inglo.giggle.account.application.usecase.ReadAdminAccountSignUpOverviewUseCase;
import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.core.dto.CountInfoDto;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAdminAccountSignUpOverviewService implements ReadAdminAccountSignUpOverviewUseCase {

    private final AccountRepository accountRepository;

    @Override
    public ReadAdminAccountSignUpOverviewResponseDto execute(LocalDate startDate, LocalDate endDate) {

        int duration = startDate.until(endDate).getDays();

        LocalDate priorStartDate = startDate.minusDays(duration);
        LocalDate priorEndDate = endDate.minusDays(duration);

        List<Account> accumulatedAccounts = accountRepository.findAllBeforeEndDate(endDate.plusDays(1).atStartOfDay());

        List<User> accumulatedUsers = accumulatedAccounts.stream()
                .filter(account -> account instanceof User)
                .map(account -> (User) account)
                .toList();

        List<Owner> accumulatedOwners = accumulatedAccounts.stream()
                .filter(account -> account instanceof Owner)
                .map(account -> (Owner) account)
                .toList();

        List<User> newUsers = accumulatedUsers.stream()
                .filter(user -> user.getCreatedAt().isAfter(startDate.atStartOfDay()))
                .toList();

        List<Owner> newOwners = accumulatedOwners.stream()
                .filter(owner -> owner.getCreatedAt().isAfter(startDate.atStartOfDay()))
                .toList();

        List<User> priorAccumulatedUsers = accumulatedUsers.stream()
                .filter(user -> user.getCreatedAt().isBefore(startDate.atStartOfDay()))
                .toList();

        List<Owner> priorAccumulatedOwners = accumulatedOwners.stream()
                .filter(owner -> owner.getCreatedAt().isBefore(startDate.atStartOfDay()))
                .toList();

        List<User> priorUsers = accumulatedUsers.stream()
                .filter(user -> user.getCreatedAt().isAfter(priorStartDate.atStartOfDay()) && user.getCreatedAt().isBefore(priorEndDate.atStartOfDay()))
                .toList();

        List<Owner> priorOwners = accumulatedOwners.stream()
                .filter(owner -> owner.getCreatedAt().isAfter(priorStartDate.atStartOfDay()) && owner.getCreatedAt().isBefore(priorEndDate.atStartOfDay()))
                .toList();

        CountInfoDto userSignUpInfo = CountInfoDto.of(newUsers.size(), priorUsers.size(), calculatePercentage(newUsers.size(), priorUsers.size()));
        CountInfoDto accumulatedUserSignUpInfo = CountInfoDto.of(accumulatedUsers.size(), priorAccumulatedUsers.size(), calculatePercentage(accumulatedUsers.size(), priorAccumulatedUsers.size()));
        CountInfoDto ownerSignUpInfo = CountInfoDto.of(newOwners.size(), priorOwners.size(), calculatePercentage(newOwners.size(), priorOwners.size()));
        CountInfoDto accumulatedOwnerSignUpInfo = CountInfoDto.of(accumulatedOwners.size(), priorAccumulatedOwners.size(), calculatePercentage(accumulatedOwners.size(), priorAccumulatedOwners.size()));

        return ReadAdminAccountSignUpOverviewResponseDto.of(userSignUpInfo, accumulatedUserSignUpInfo, ownerSignUpInfo, accumulatedOwnerSignUpInfo);
    }

    private double calculatePercentage(int current, int prior) {
        if (current == 0 && prior == 0) return 0.0;
        if (prior == 0) return 100.00;
        double raw = ((double) (current - prior) / prior) * 100;
        return Math.round(raw * 100.0) / 100.0;
    }
}
