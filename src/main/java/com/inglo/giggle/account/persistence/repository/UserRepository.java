package com.inglo.giggle.account.persistence.repository;

import com.inglo.giggle.account.domain.User;

import java.util.UUID;

public interface UserRepository {

    User findByIdOrElseThrow(UUID accountId);

    User save(User user);
}
