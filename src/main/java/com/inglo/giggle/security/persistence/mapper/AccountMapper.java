package com.inglo.giggle.security.persistence.mapper;

import com.inglo.giggle.account.domain.Admin;
import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.persistence.entity.AdminEntity;
import com.inglo.giggle.account.persistence.entity.OwnerEntity;
import com.inglo.giggle.account.persistence.entity.UserEntity;
import com.inglo.giggle.account.persistence.mapper.AdminMapper;
import com.inglo.giggle.account.persistence.mapper.OwnerMapper;
import com.inglo.giggle.account.persistence.mapper.UserMapper;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.domain.Account;
import com.inglo.giggle.security.persistence.entity.mysql.AccountEntity;

import java.util.List;

public class AccountMapper {
    public static Account toDomain(AccountEntity entity) {
        if (entity == null) {
            return null;
        }

        if (entity instanceof UserEntity user) {
            return UserMapper.toDomain(user);
        } else if (entity instanceof OwnerEntity owner) {
            return OwnerMapper.toDomain(owner);
        } else if (entity instanceof AdminEntity admin) {
            return AdminMapper.toDomain(admin);
        } else {
            throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
        }
    }

    public static AccountEntity toEntity(Account domain) {
        if (domain == null) {
            return null;
        }
        if (domain instanceof User user) {
            return UserMapper.toEntity(user);
        } else if (domain instanceof Owner owner) {
            return OwnerMapper.toEntity(owner);
        } else if (domain instanceof Admin admin) {
            return AdminMapper.toEntity(admin);
        } else {
            throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
        }
    }

    public static List<Account> toDomains(List<AccountEntity> entities) {
        return entities.stream()
                .map(AccountMapper::toDomain)
                .toList();
    }

    public static List<AccountEntity> toEntities(List<Account> domains) {
        return domains.stream()
                .map(AccountMapper::toEntity)
                .toList();
    }
}

