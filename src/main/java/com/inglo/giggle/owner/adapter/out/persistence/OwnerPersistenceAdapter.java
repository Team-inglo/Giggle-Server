package com.inglo.giggle.owner.adapter.out.persistence;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.owner.adapter.out.persistence.entity.OwnerEntity;
import com.inglo.giggle.owner.adapter.out.persistence.mapper.OwnerMapper;
import com.inglo.giggle.owner.adapter.out.persistence.repository.mysql.OwnerJpaRepository;
import com.inglo.giggle.owner.application.port.out.CreateOwnerPort;
import com.inglo.giggle.owner.application.port.out.DeleteOwnerPort;
import com.inglo.giggle.owner.application.port.out.LoadOwnerPort;
import com.inglo.giggle.owner.application.port.out.UpdateOwnerPort;
import com.inglo.giggle.owner.domain.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OwnerPersistenceAdapter implements LoadOwnerPort, UpdateOwnerPort, CreateOwnerPort, DeleteOwnerPort {

    private final OwnerJpaRepository ownerJpaRepository;
    private final OwnerMapper ownerMapper;

    @Override
    public Owner loadOwnerOrElseThrow(UUID ownerId) {
        return ownerMapper.toDomain(ownerJpaRepository.findById(ownerId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_OWNER)));
    }

    @Override
    public Owner loadOwnerOrElseNull(UUID ownerId) {
        return ownerMapper.toDomain(ownerJpaRepository.findById(ownerId)
                .orElse(null));
    }

    @Override
    public Owner updateOwner(Owner owner) {
        OwnerEntity entity = ownerJpaRepository.save(ownerMapper.toEntity(owner));
        return ownerMapper.toDomain(entity);
    }

    @Override
    public Owner createOwner(Owner owner) {
        OwnerEntity entity = ownerJpaRepository.save(ownerMapper.toEntity(owner));
        return ownerMapper.toDomain(entity);
    }

    @Override
    public void deleteOwner(Owner owner) {
        ownerJpaRepository.delete(ownerMapper.toEntity(owner));
    }
}
