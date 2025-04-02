package com.inglo.giggle.account.persistence.repository;

import com.inglo.giggle.account.domain.Owner;

import java.util.UUID;

public interface OwnerRepository {

    Owner findByIdOrElseThrow(UUID ownerId);

    Owner save(Owner owner);
}
