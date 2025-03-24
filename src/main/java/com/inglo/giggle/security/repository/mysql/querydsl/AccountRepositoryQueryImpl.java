package com.inglo.giggle.security.repository.mysql.querydsl;

import com.inglo.giggle.account.domain.QAdmin;
import com.inglo.giggle.account.domain.QOwner;
import com.inglo.giggle.account.domain.QUser;
import com.inglo.giggle.account.domain.type.ELanguage;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.mysql.QAccount;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryQueryImpl implements AccountRepositoryQuery {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Account> findAccountByFilter(
            Pageable pageable,
            String search,
            LocalDate startDate,
            LocalDate endDate,
            String filterType,
            String filter,
            String sortType,
            Direction sort
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
                    baseQuery.where(user.nationality.eq(filter));
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
            Order direction = (sort != null && sort.equals(Direction.DESC))
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
