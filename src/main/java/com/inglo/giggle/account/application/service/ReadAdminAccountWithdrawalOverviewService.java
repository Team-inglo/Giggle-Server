package com.inglo.giggle.account.application.service;

import com.inglo.giggle.account.application.dto.response.ReadAdminAccountWithdrawalOverviewResponseDto;
import com.inglo.giggle.account.application.usecase.ReadAdminAccountWithdrawalOverviewUseCase;
import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.core.dto.CountInfoDto;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadAdminAccountWithdrawalOverviewService implements ReadAdminAccountWithdrawalOverviewUseCase {

    private final AccountRepository accountRepository;

    @Override
    public ReadAdminAccountWithdrawalOverviewResponseDto execute(LocalDate startDate, LocalDate endDate) {

        int duration = startDate.until(endDate).getDays();

        LocalDate priorStartDate = startDate.minusDays(duration);
        LocalDate priorEndDate = endDate.minusDays(duration);

        List<Account> accumulatedAccounts = accountRepository.findAllBeforeEndDateWithDeleted(endDate.plusDays(1).atStartOfDay());

        for (Account account : accumulatedAccounts) {
            log.info("account: {}", account);
            log.info("account.getName(): {}", account.getName());
            log.info("account.getDeletedAt(): {}", account.getDeletedAt());
        }

        List<User> deletedAccumulatedUsers = accumulatedAccounts.stream()
                .filter(account -> account instanceof User && account.getDeletedAt() != null)
                .map(account -> (User) account)
                .toList();

        List<Owner> deletedAccumulatedOwners = accumulatedAccounts.stream()
                .filter(account -> account instanceof Owner && account.getDeletedAt() != null)
                .map(account -> (Owner) account)
                .toList();

        List<User> newUsers = deletedAccumulatedUsers.stream()
                .filter(user -> user.getCreatedAt().isAfter(startDate.atStartOfDay()))
                .toList();

        List<Owner> newOwners = deletedAccumulatedOwners.stream()
                .filter(owner -> owner.getCreatedAt().isAfter(startDate.atStartOfDay()))
                .toList();

        List<User> priorAccumulatedUsers = deletedAccumulatedUsers.stream()
                .filter(user -> user.getCreatedAt().isBefore(startDate.atStartOfDay()))
                .toList();

        List<Owner> priorAccumulatedOwners = deletedAccumulatedOwners.stream()
                .filter(owner -> owner.getCreatedAt().isBefore(startDate.atStartOfDay()))
                .toList();

        List<User> priorUsers = deletedAccumulatedUsers.stream()
                .filter(user -> user.getCreatedAt().isAfter(priorStartDate.atStartOfDay()) && user.getCreatedAt().isBefore(priorEndDate.atStartOfDay()))
                .toList();

        List<Owner> priorOwners = deletedAccumulatedOwners.stream()
                .filter(owner -> owner.getCreatedAt().isAfter(priorStartDate.atStartOfDay()) && owner.getCreatedAt().isBefore(priorEndDate.atStartOfDay()))
                .toList();

        CountInfoDto userSignUpInfo = CountInfoDto.of(newUsers.size(), priorUsers.size(), calculatePercentage(newUsers.size(), priorUsers.size()));
        CountInfoDto accumulatedUserSignUpInfo = CountInfoDto.of(deletedAccumulatedUsers.size(), priorAccumulatedUsers.size(), calculatePercentage(deletedAccumulatedUsers.size(), priorAccumulatedUsers.size()));
        CountInfoDto ownerSignUpInfo = CountInfoDto.of(newOwners.size(), priorOwners.size(), calculatePercentage(newOwners.size(), priorOwners.size()));
        CountInfoDto accumulatedOwnerSignUpInfo = CountInfoDto.of(deletedAccumulatedOwners.size(), priorAccumulatedOwners.size(), calculatePercentage(deletedAccumulatedOwners.size(), priorAccumulatedOwners.size()));

        return ReadAdminAccountWithdrawalOverviewResponseDto.of(userSignUpInfo, accumulatedUserSignUpInfo, ownerSignUpInfo, accumulatedOwnerSignUpInfo);
    }

    private double calculatePercentage(int current, int prior) {
        if (current == 0 && prior == 0) return 0.0;
        if (prior == 0) return 100.00;
        double raw = ((double) (current - prior) / prior) * 100;
        return Math.round(raw * 100.0) / 100.0;
    }
}
