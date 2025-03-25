package com.inglo.giggle.account.repository;

import com.inglo.giggle.account.domain.User;

import java.util.UUID;

public interface UserRepository {

    User findByIdOrElseThrow(UUID accountId);

    void save(User user);
}
