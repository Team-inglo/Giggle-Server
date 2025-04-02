package com.inglo.giggle.account.persistence.repository.mysql;

import com.inglo.giggle.account.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID>{
}
