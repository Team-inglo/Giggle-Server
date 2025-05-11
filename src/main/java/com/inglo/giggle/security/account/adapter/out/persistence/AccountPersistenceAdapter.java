package com.inglo.giggle.security.account.adapter.out.persistence;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.security.account.adapter.out.persistence.entity.mysql.AccountDeviceEntity;
import com.inglo.giggle.security.account.adapter.out.persistence.entity.mysql.AccountEntity;
import com.inglo.giggle.security.account.adapter.out.persistence.mapper.AccountDeviceMapper;
import com.inglo.giggle.security.account.adapter.out.persistence.mapper.AccountMapper;
import com.inglo.giggle.security.account.adapter.out.persistence.mapper.RefreshTokenMapper;
import com.inglo.giggle.security.account.adapter.out.persistence.repository.mysql.AccountDeviceJpaRepository;
import com.inglo.giggle.security.account.adapter.out.persistence.repository.mysql.AccountJpaRepository;
import com.inglo.giggle.security.account.adapter.out.persistence.repository.redis.RefreshTokenRedisRepository;
import com.inglo.giggle.security.account.application.port.out.CreateAccountPort;
import com.inglo.giggle.security.account.application.port.out.CreateRefreshTokenPort;
import com.inglo.giggle.security.account.application.port.out.DeleteAccountPort;
import com.inglo.giggle.security.account.application.port.out.DeleteRefreshTokenPort;
import com.inglo.giggle.security.account.application.port.out.LoadAccountDevicePort;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import com.inglo.giggle.security.account.application.port.out.LoadRefreshTokenPort;
import com.inglo.giggle.security.account.application.port.out.UpdateAccountDevicePort;
import com.inglo.giggle.security.account.application.port.out.UpdateAccountPort;
import com.inglo.giggle.security.account.application.port.out.UpdateRefreshTokenPort;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.domain.AccountDevice;
import com.inglo.giggle.security.account.domain.RefreshToken;
import com.inglo.giggle.security.account.domain.type.ESecurityProvider;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import com.inglo.giggle.security.persistence.entity.mysql.QAccountEntity;
import com.inglo.giggle.user.domain.type.ELanguage;
import com.inglo.giggle.user.persistence.entity.QAdminEntity;
import com.inglo.giggle.user.persistence.entity.QOwnerEntity;
import com.inglo.giggle.user.persistence.entity.QUserEntity;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements
        LoadAccountPort, CreateAccountPort, DeleteAccountPort, UpdateAccountPort,
        LoadRefreshTokenPort, CreateRefreshTokenPort, UpdateRefreshTokenPort, DeleteRefreshTokenPort,
        LoadAccountDevicePort, UpdateAccountDevicePort
{
    private final AccountJpaRepository accountJpaRepository;
    private final AccountDeviceJpaRepository accountDeviceJpaRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final AccountMapper accountMapper;
    private final AccountDeviceMapper accountDeviceMapper;
    private final RefreshTokenMapper refreshTokenMapper;
    private final JPAQueryFactory queryFactory;

    @Override
    public Account loadAccount(UUID accountId) {
        Account account = accountMapper.toDomain(accountJpaRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT)));

        RefreshToken refreshToken = refreshTokenMapper.toDomain(
                refreshTokenRedisRepository.findByAccountIdAndValue(account.getId(), account.getRefreshToken().getValue())
                        .orElse(null)
        );

        account.updateRefreshToken(refreshToken);

        return account;
    }

    @Override
    public Account loadAccount(String serialId) {
        return accountMapper.toDomain(accountJpaRepository.findBySerialId(serialId).orElse(null));
    }

    @Override
    public Account loadAccountWithAccountDevices(UUID accountId) {
        AccountEntity accountEntity = accountJpaRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT));

        List<AccountDeviceEntity> accountDeviceEntities = accountDeviceJpaRepository.findByAccountEntityId(accountId);
        List<AccountDevice> accountDevices = accountDeviceEntities.stream()
                .map(accountDeviceMapper::toDomain)
                .toList();

        return accountMapper.toDomain(accountEntity, accountDevices);
    }

    @Override
    public Account loadAccountWithRefreshTokenAndAccountDevices(UUID accountId) {
        AccountEntity accountEntity = accountJpaRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT));

        List<AccountDeviceEntity> accountDeviceEntities = accountDeviceJpaRepository.findByAccountEntityId(accountId);
        List<AccountDevice> accountDevices = accountDeviceEntities.stream()
                .map(accountDeviceMapper::toDomain)
                .toList();

        Account account = accountMapper.toDomain(accountEntity, accountDevices);

        RefreshToken refreshToken = refreshTokenMapper.toDomain(
                refreshTokenRedisRepository.findByAccountIdAndValue(account.getId(), account.getRefreshToken().getValue())
                        .orElse(null)
        );
        account.updateRefreshToken(refreshToken);

        return account;
    }

    @Override
    public Account loadAccountOrElseThrowUserNameNotFoundException(String serialId, ESecurityProvider provider) {
        return accountMapper.toDomain(accountJpaRepository.findBySerialIdAndProvider(serialId, provider)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with serialId: " + serialId)));
    }

    @Override
    public Account loadAccount(String serialId, ESecurityProvider provider) {
        return accountMapper.toDomain(accountJpaRepository.findBySerialIdAndProvider(serialId, provider)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT)));
    }

    @Override
    public List<Account> loadAccounts(LocalDateTime endDate) {
        return accountJpaRepository.findAllBeforeEndDate(endDate).stream()
                .map(accountMapper::toDomain)
                .toList();
    }

    @Override
    public List<Account> loadAccountsWithDeleted(LocalDateTime endDate) {
        return accountJpaRepository.findAllBeforeEndDateWithDeleted(endDate).stream()
                .map(accountMapper::toDomain)
                .toList();
    }

    @Override
    public Account createAccount(Account account) {
        AccountEntity entity = accountJpaRepository.save(accountMapper.toEntity(account));
        return accountMapper.toDomain(entity);
    }

    @Override
    public Account updateAccount(Account account) {
        AccountEntity entity = accountJpaRepository.save(accountMapper.toEntity(account));
        return accountMapper.toDomain(entity);
    }

    @Override
    public void deleteAccount(Account account) {
        accountJpaRepository.delete(accountMapper.toEntity(account));
    }

    @Override
    public Page<Account> loadAccounts(
            Pageable pageable,
            String search,
            LocalDate startDate,
            LocalDate endDate,
            String filterType,
            String filter,
            String sortType,
            Sort.Direction sort
    ) {
        QAccountEntity account = QAccountEntity.accountEntity;
        QUserEntity user = QUserEntity.userEntity;
        QOwnerEntity owner = QOwnerEntity.ownerEntity;
        QAdminEntity admin = QAdminEntity.adminEntity;

        JPAQuery<AccountEntity> baseQuery = queryFactory
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
                            .or(user.addressEntity.addressDetail.containsIgnoreCase(search))
                            .or(owner.companyName.containsIgnoreCase(search))
                            .or(owner.addressEntity.addressDetail.containsIgnoreCase(search))
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
                        case USER -> baseQuery.where(user.isNotNull());
                        case OWNER -> baseQuery.where(owner.isNotNull());
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
            Order direction = (sort != null && sort.equals(Sort.Direction.DESC))
                    ? Order.DESC : Order.ASC;

            switch (sortType) {
                case "createdAt" -> baseQuery.orderBy(new OrderSpecifier<>(direction, account.createdAt));
                case "email" -> baseQuery.orderBy(new OrderSpecifier<>(direction, account.email));
                case "name" -> {
                    baseQuery.orderBy(new OrderSpecifier<>(direction, user.firstName))
                            .orderBy(new OrderSpecifier<>(direction, user.lastName));
                }
                default -> baseQuery.orderBy(new OrderSpecifier<>(Order.DESC, account.createdAt));
            }
        }

        long total = Optional.ofNullable(
                baseQuery.clone()
                        .select(account.id.count())
                        .fetchOne()
        ).orElse(0L);

        List<Account> content = baseQuery.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(accountMapper::toDomain)
                .toList();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public RefreshToken loadRefreshToken(UUID accountId, String value) {
        return refreshTokenMapper.toDomain(refreshTokenRedisRepository.findByAccountIdAndValue(accountId, value)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_TOKEN_ERROR)));
    }

    @Override
    public void createRefreshToken(RefreshToken refreshToken) {
        refreshTokenRedisRepository.save(refreshTokenMapper.toEntity(refreshToken));
    }

    @Override
    public void updateRefreshToken(RefreshToken refreshToken) {
        refreshTokenRedisRepository.save(refreshTokenMapper.toEntity(refreshToken));
    }

    @Override
    public void deleteRefreshToken(RefreshToken refreshToken) {
        refreshTokenRedisRepository.delete(refreshTokenMapper.toEntity(refreshToken));
    }

    @Override
    public List<AccountDevice> loadAccountDevices(UUID accountId) {
        List<AccountDeviceEntity> accountDeviceEntities = accountDeviceJpaRepository.findByAccountEntityId(accountId);
        return accountDeviceEntities.stream()
                .map(accountDeviceMapper::toDomain)
                .toList();
    }

    @Override
    public void updateAccountDevice(Account account) {
        // Mapper 에서 toEntity 를 위해 사용하기 위한 AccountEntity 조회
        AccountEntity accountEntity = accountMapper.toEntity(account);

        // 기존에 DB에 저장된 AccountDeviceEntity 조회
        List<AccountDeviceEntity> existingEntities = accountDeviceJpaRepository.findByAccountEntityId(account.getId());

        // Map 으로 변환
        Map<Long, AccountDeviceEntity> existingMap = existingEntities.stream()
                .collect(Collectors.toMap(AccountDeviceEntity::getId, Function.identity()));

        Set<Long> inputIds = new HashSet<>();
        List<AccountDeviceEntity> toSave = new ArrayList<>();
        List<AccountDeviceEntity> toUpdate = new ArrayList<>();

        for (AccountDevice input : account.getAccountDevices()) {
            if (input.getId() == null) { // 생성의 경우
                toSave.add(accountDeviceMapper.toEntity(input, accountEntity)); // AccountDeviceEntity 로 변환 후, toSave 리스트에 추가
            } else { // 수정의 경우
                inputIds.add(input.getId()); // inputIds 에 추가(이후 삭제 대상인지 판단을 위함)
                AccountDeviceEntity existing = existingMap.get(input.getId()); // 기존 DB 엔티티로 만든 Map 에서, Application Layer 에서 받은 객체의 id 가 존재하는지 확인
                if (existing == null) { // 존재하지 않는다면, CommonException 발생(ID 가 존재하므로 생성은 아닌데, DB 에는 존재하지 않음. 정합성 문제)
                    throw new CommonException(ErrorCode.INVALID_ARGUMENT);
                }
                AccountDeviceEntity updated = accountDeviceMapper.toEntity(input, accountEntity);
                // equals 메서드를 사용하여 기존 엔티티와 업데이트된 엔티티를 비교. 다르다면 업데이트 리스트에 추가
                if (!existing.equals(updated)) {
                    toUpdate.add(updated);
                }
            }
        }

        // inputIds 에 포함되지 않는 기존 엔티티는 삭제 대상
        List<AccountDeviceEntity> toDelete = existingEntities.stream()
                .filter(e -> !inputIds.contains(e.getId()))
                .toList();

        if (!toSave.isEmpty()) accountDeviceJpaRepository.saveAll(toSave);
        if (!toUpdate.isEmpty()) accountDeviceJpaRepository.saveAll(toUpdate);
        if (!toDelete.isEmpty()) accountDeviceJpaRepository.deleteAll(toDelete);
    }
}
