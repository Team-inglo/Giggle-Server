package com.inglo.giggle.account.repository.impl;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.repository.OwnerRepository;
import com.inglo.giggle.account.repository.mysql.OwnerJpaRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OwnerRepositoryImpl implements OwnerRepository {

    private final OwnerJpaRepository ownerJpaRepository;

    @Override
    public Owner findByIdOrElseThrow(UUID ownerId) {
        return ownerJpaRepository.findById(ownerId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_OWNER));
    }

    @Override
    public Owner findByDocumentIdOrElseThrow(Long documentId) {
        return ownerJpaRepository.findByDocumentId(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_OWNER));
    }

    @Override
    public void save(Owner owner) {
        ownerJpaRepository.save(owner);
    }

    @Override
    public void delete(Owner owner) {
        ownerJpaRepository.delete(owner);
    }
}
