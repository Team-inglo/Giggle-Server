package com.inglo.giggle.version.adapter.out.persistence;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.version.adapter.out.persistence.mapper.VersionMapper;
import com.inglo.giggle.version.adapter.out.persistence.repository.mysql.VersionJpaRepository;
import com.inglo.giggle.version.application.port.out.LoadVersionPort;
import com.inglo.giggle.version.domain.Version;
import com.inglo.giggle.version.domain.type.EOsType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VersionPersistenceAdapter implements LoadVersionPort {

    private final VersionJpaRepository versionJpaRepository;
    private final VersionMapper versionMapper;

    @Override
    public Version loadVersion(EOsType osType) {
        return versionMapper.toDomain(versionJpaRepository.findByOsType(osType)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VERSION)));
    }
}
