package com.inglo.giggle.user.adapter.out.persistence;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.user.adapter.out.persistence.mapper.UserMapper;
import com.inglo.giggle.user.adapter.out.persistence.repository.mysql.UserJpaRepository;
import com.inglo.giggle.user.application.port.out.CreateUserPort;
import com.inglo.giggle.user.application.port.out.DeleteUserPort;
import com.inglo.giggle.user.application.port.out.LoadUserPort;
import com.inglo.giggle.user.application.port.out.UpdateUserPort;
import com.inglo.giggle.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserPersistenceAdapter implements LoadUserPort, CreateUserPort, UpdateUserPort, DeleteUserPort {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public User loadUserOrElseThrow(UUID accountId) {
        return userMapper.toDomain(userJpaRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER)));
    }

    @Override
    public User loadUserOrElseNull(UUID accountId) {
        return userMapper.toDomain(userJpaRepository.findById(accountId)
                .orElse(null));
    }

    @Override
    public void createUser(User user) {
        userJpaRepository.save(userMapper.toEntity(user));
    }

    @Override
    public void updateUser(User user) {
        userJpaRepository.save(userMapper.toEntity(user));
    }

    @Override
    public void deleteUser(User user) {
        userJpaRepository.delete(userMapper.toEntity(user));
    }
}
