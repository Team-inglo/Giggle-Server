package com.inglo.giggle.account.repository.impl;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.repository.UserRepository;
import com.inglo.giggle.account.repository.mysql.UserJpaRepository;
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
        return userJpaRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
    }

    @Override
    public void save(User user) {
        userJpaRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userJpaRepository.delete(user);
    }
}
