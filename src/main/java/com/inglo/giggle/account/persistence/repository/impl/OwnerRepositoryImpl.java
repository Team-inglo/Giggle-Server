package com.inglo.giggle.account.persistence.repository.impl;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.persistence.entity.OwnerEntity;
import com.inglo.giggle.account.persistence.mapper.OwnerMapper;
import com.inglo.giggle.account.persistence.repository.OwnerRepository;
import com.inglo.giggle.account.persistence.repository.mysql.OwnerJpaRepository;
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
        return OwnerMapper.toDomain(ownerJpaRepository.findById(ownerId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_OWNER)));
    }

    @Override
    public Owner save(Owner owner) {
        OwnerEntity entity = ownerJpaRepository.save(OwnerMapper.toEntity(owner));
        return OwnerMapper.toDomain(entity);
    }
}
