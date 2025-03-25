package com.inglo.giggle.account.repository;

import com.inglo.giggle.account.domain.Owner;

import java.util.UUID;

public interface OwnerRepository {

    Owner findByIdOrElseThrow(UUID ownerId);

    Owner findByDocumentIdOrElseThrow(Long documentId);

    void save(Owner owner);
}
