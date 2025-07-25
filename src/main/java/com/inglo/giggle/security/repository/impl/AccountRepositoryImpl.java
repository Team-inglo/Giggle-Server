package com.inglo.giggle.security.repository.impl;

import com.inglo.giggle.account.domain.QAdmin;
import com.inglo.giggle.account.domain.QOwner;
import com.inglo.giggle.account.domain.QUser;
import com.inglo.giggle.account.domain.type.ELanguage;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.ENationality;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.mysql.QAccount;
import com.inglo.giggle.security.domain.type.ESecurityProvider;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import com.inglo.giggle.security.repository.AccountRepository;
import com.inglo.giggle.security.repository.mysql.AccountJpaRepository;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountJpaRepository accountJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Account findByIdOrElseThrow(UUID accountId) {
        return accountJpaRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT));
    }

    @Override
    public Account findBySerialIdOrElseNull(String serialId) {
        return accountJpaRepository.findBySerialId(serialId).orElse(null);
    }

    @Override
    public Account findBySerialIdAndProviderOrElseThrowUserNameNotFoundException(String serialId, ESecurityProvider provider) {
        return accountJpaRepository.findBySerialIdAndProvider(serialId, provider)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with serialId: " + serialId));
    }

    @Override
    public Account findBySerialIdAndProviderOrElseThrow(String serialId, ESecurityProvider provider) {
        return accountJpaRepository.findBySerialIdAndProvider(serialId, provider)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT));
    }

    @Override
    public Account findByEmailAndProviderOrElseNull(String email, ESecurityProvider provider) {
        return accountJpaRepository.findByEmailAndProvider(email, provider).orElse(null);
    }

    @Override
    public Account findByEmailOrElseNull(String email) {
        return accountJpaRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<Account> findAllBeforeEndDate(LocalDateTime endDate) {
        return accountJpaRepository.findAllBeforeEndDate(endDate);
    }

    @Override
    public List<Account> findAllBeforeEndDateWithDeleted(LocalDateTime endDate) {
        return accountJpaRepository.findAllBeforeEndDateWithDeleted(endDate);
    }

    @Override
    public void save(Account account) {
        accountJpaRepository.save(account);
    }

    @Override
    public Account saveAndReturn(Account account) {
        return accountJpaRepository.save(account);
    }

    @Override
    public void deleteById(UUID accountId) {
        accountJpaRepository.deleteById(accountId);
    }

    @Override
    public Page<Account> findAccountByFilter(
            Pageable pageable,
            String search,
            LocalDate startDate,
            LocalDate endDate,
            String filterType,
            String filter,
            String sortType,
            Sort.Direction sort
    ) {
        QAccount account = QAccount.account;
        QUser user = QUser.user;
        QOwner owner = QOwner.owner;
        QAdmin admin = QAdmin.admin;

        JPAQuery<Account> baseQuery = queryFactory
                .selectFrom(account)
                .leftJoin(user).on(user.id.eq(account.id))
                .leftJoin(owner).on(owner.id.eq(account.id))
                .leftJoin(admin).on(admin.id.eq(account.id))
                .where(admin.isNull());

        // 검색 조건
        if (search != null && !search.isBlank()) {
            // 이름 + 이메일 + 주소 + 전화번호
            baseQuery.where(
                    account.email.containsIgnoreCase(search)
                            .or(account.phoneNumber.containsIgnoreCase(search))
                            .or(user.firstName.containsIgnoreCase(search))
                            .or(user.lastName.containsIgnoreCase(search))
                            .or(user.address.addressDetail.containsIgnoreCase(search))
                            .or(owner.companyName.containsIgnoreCase(search))
                            .or(owner.address.addressDetail.containsIgnoreCase(search))
            );
        }

        // 날짜 조건
        if (startDate != null || endDate != null) {
            if (startDate == null) {
                startDate = LocalDate.of(1970, 1, 1);
            }
            if (endDate == null) {
                endDate = LocalDate.now();
            }
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
            baseQuery.where(account.createdAt.between(startDateTime, endDateTime));
        }

        // 필터 조건
        if (filterType != null && !filterType.isBlank()
                && filter != null && !filter.isBlank()) {

            switch (filterType) {

                case "role" -> {
                    ESecurityRole role = ESecurityRole.fromString(filter);

                    switch (role) {
                        case USER ->
                                baseQuery.where(user.isNotNull());
                        case OWNER ->
                                baseQuery.where(owner.isNotNull());
                    }
                }

                case "gender" -> {
                    EGender gender = EGender.fromString(filter);
                    baseQuery.where(user.gender.eq(gender));
                }

                case "nationality" -> {
                    ENationality nationality = ENationality.fromString(filter);
                    baseQuery.where(user.nationality.eq(nationality));
                }

                case "visa" -> {
                    EVisa visa = EVisa.fromString(filter);
                    baseQuery.where(user.visa.eq(visa));
                }

                case "language" -> {
                    ELanguage language = ELanguage.fromString(filter);
                    baseQuery.where(user.language.eq(language));
                }
            }
        }

        // 정렬 조건
        if (sortType != null && !sortType.isBlank()) {
            Order direction = (sort != null && sort.equals(Sort.Direction.DESC))
                    ? Order.DESC : Order.ASC;

            switch (sortType) {
                case "createdAt" ->
                        baseQuery.orderBy(new OrderSpecifier<>(direction, account.createdAt));
                case "email" ->
                        baseQuery.orderBy(new OrderSpecifier<>(direction, account.email));
                case "name" -> {
                    baseQuery.orderBy(new OrderSpecifier<>(direction, user.firstName))
                            .orderBy(new OrderSpecifier<>(direction, user.lastName));
                }
                default ->
                        baseQuery.orderBy(new OrderSpecifier<>(Order.DESC, account.createdAt));
            }
        }

        long total = Optional.ofNullable(
                baseQuery.clone()
                        .select(account.id.count())
                        .fetchOne()
        ).orElse(0L);

        List<Account> content = baseQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, total);
    }

}
