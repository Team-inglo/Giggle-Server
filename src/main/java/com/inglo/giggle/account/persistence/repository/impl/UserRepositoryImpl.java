package com.inglo.giggle.account.persistence.repository.impl;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.persistence.entity.UserEntity;
import com.inglo.giggle.account.persistence.mapper.UserMapper;
import com.inglo.giggle.account.persistence.repository.UserRepository;
import com.inglo.giggle.account.persistence.repository.mysql.UserJpaRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User findByIdOrElseThrow(UUID accountId) {
        return UserMapper.toDomain(userJpaRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER)));
    }

    @Override
    public User save(User user) {
        UserEntity entity = userJpaRepository.save(UserMapper.toEntity(user));
        return UserMapper.toDomain(entity);
    }
}
