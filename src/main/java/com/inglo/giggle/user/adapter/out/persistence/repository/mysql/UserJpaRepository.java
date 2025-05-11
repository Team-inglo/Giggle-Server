package com.inglo.giggle.user.adapter.out.persistence.repository.mysql;

import com.inglo.giggle.user.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID>{
}
