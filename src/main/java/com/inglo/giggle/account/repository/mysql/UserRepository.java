package com.inglo.giggle.account.repository.mysql;

import com.inglo.giggle.account.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
}
