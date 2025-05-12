package com.inglo.giggle.security.temporaryaccount.adapter.out.persistence;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.temporaryaccount.adapter.out.persistence.mapper.TemporaryAccountMapper;
import com.inglo.giggle.security.temporaryaccount.adapter.out.persistence.repository.redis.TemporaryAccountRedisRepository;
import com.inglo.giggle.security.temporaryaccount.application.port.out.CreateTemporaryAccountPort;
import com.inglo.giggle.security.temporaryaccount.application.port.out.DeleteTemporaryAccountPort;
import com.inglo.giggle.security.temporaryaccount.application.port.out.LoadTemporaryAccountPort;
import com.inglo.giggle.security.temporaryaccount.domain.TemporaryAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TemporaryAccountPersistenceAdapter implements LoadTemporaryAccountPort, CreateTemporaryAccountPort, DeleteTemporaryAccountPort {

    private final TemporaryAccountRedisRepository temporaryAccountRedisRepository;
    private final TemporaryAccountMapper temporaryAccountMapper;

    @Override
    public TemporaryAccount loadTemporaryAccountOrElseThrow(String id) {
        return temporaryAccountMapper.toDomain(temporaryAccountRedisRepository.findById(id).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TEMPORARY_ACCOUNT)));
    }

    @Override
    public void createTemporaryAccount(TemporaryAccount temporaryAccount) {
        temporaryAccountRedisRepository.save(temporaryAccountMapper.toEntity(temporaryAccount));
    }

    @Override
    public void deleteTemporaryAccount(String id) {
        temporaryAccountRedisRepository.deleteById(id);
    }
}
