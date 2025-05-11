package com.inglo.giggle.admin.application.service;

import com.inglo.giggle.admin.application.port.in.query.ReadAdminAccountWithdrawalOverviewUseCase;
import com.inglo.giggle.admin.application.port.in.result.ReadAdminAccountWithdrawalOverviewResponseDto;
import com.inglo.giggle.core.dto.CountInfoDto;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccumulatedAccountWithDeletedQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccumulatedAccountWithDeletedResult;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAdminAccountWithdrawalOverviewService implements ReadAdminAccountWithdrawalOverviewUseCase {

    private final ReadAccumulatedAccountWithDeletedQuery readAccumulatedAccountWithDeletedQuery;

    @Override
    public ReadAdminAccountWithdrawalOverviewResponseDto execute(LocalDate startDate, LocalDate endDate) {

        int duration = startDate.until(endDate).getDays();

        LocalDate priorStartDate = startDate.minusDays(duration);
        LocalDate priorEndDate = endDate.minusDays(duration);

        ReadAccumulatedAccountWithDeletedResult accumulatedAccounts = readAccumulatedAccountWithDeletedQuery.execute(endDate.plusDays(1).atStartOfDay());

        List<ReadAccumulatedAccountWithDeletedResult.AccountDto> deletedAccumulatedUser = accumulatedAccounts.getAccountDtos().stream()
                .filter(account -> account.getRole() == ESecurityRole.USER && account.getDeletedAt() != null)
                .map(account -> new ReadAccumulatedAccountWithDeletedResult.AccountDto(
                        account.getAccountId(),
                        account.getRole(),
                        account.getCreatedAt(),
                        account.getDeletedAt()))
                .toList();

        List<ReadAccumulatedAccountWithDeletedResult.AccountDto> deletedAccumulatedOwners = accumulatedAccounts.getAccountDtos().stream()
                .filter(account -> account.getRole() == ESecurityRole.OWNER && account.getDeletedAt() != null)
                .map(account -> new ReadAccumulatedAccountWithDeletedResult.AccountDto(
                        account.getAccountId(),
                        account.getRole(),
                        account.getCreatedAt(),
                        account.getDeletedAt()))
                .toList();

        List<ReadAccumulatedAccountWithDeletedResult.AccountDto> newUsers = deletedAccumulatedUser.stream()
                .filter(user -> user.getCreatedAt().isAfter(startDate.atStartOfDay()))
                .toList();

        List<ReadAccumulatedAccountWithDeletedResult.AccountDto> newOwners = deletedAccumulatedOwners.stream()
                .filter(owner -> owner.getCreatedAt().isAfter(startDate.atStartOfDay()))
                .toList();

        List<ReadAccumulatedAccountWithDeletedResult.AccountDto> priorAccumulatedUsers = deletedAccumulatedUser.stream()
                .filter(user -> user.getCreatedAt().isBefore(startDate.atStartOfDay()))
                .toList();

        List<ReadAccumulatedAccountWithDeletedResult.AccountDto> priorAccumulatedOwners = deletedAccumulatedOwners.stream()
                .filter(owner -> owner.getCreatedAt().isBefore(startDate.atStartOfDay()))
                .toList();

        List<ReadAccumulatedAccountWithDeletedResult.AccountDto> priorUsers = deletedAccumulatedUser.stream()
                .filter(user -> user.getCreatedAt().isAfter(priorStartDate.atStartOfDay()) && user.getCreatedAt().isBefore(priorEndDate.atStartOfDay()))
                .toList();

        List<ReadAccumulatedAccountWithDeletedResult.AccountDto> priorOwners = deletedAccumulatedOwners.stream()
                .filter(owner -> owner.getCreatedAt().isAfter(priorStartDate.atStartOfDay()) && owner.getCreatedAt().isBefore(priorEndDate.atStartOfDay()))
                .toList();

        CountInfoDto userSignUpInfo = CountInfoDto.of(newUsers.size(), priorUsers.size(), calculatePercentage(newUsers.size(), priorUsers.size()));
        CountInfoDto accumulatedUserSignUpInfo = CountInfoDto.of(deletedAccumulatedUser.size(), priorAccumulatedUsers.size(), calculatePercentage(deletedAccumulatedUser.size(), priorAccumulatedUsers.size()));
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
