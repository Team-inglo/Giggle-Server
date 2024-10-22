package com.inglo.giggle.account.repository.mysql;

import com.inglo.giggle.account.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OwnerRepository extends JpaRepository<Owner, UUID>{
}
