package com.inglo.giggle.version.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.version.domain.Version;
import com.inglo.giggle.version.domain.type.EOsType;
import com.inglo.giggle.version.repository.VersionRepository;
import com.inglo.giggle.version.repository.mysql.VersionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VersionRepositoryImpl implements VersionRepository {

    private final VersionJpaRepository versionJpaRepository;

    @Override
    public Version findByOsTypeOrElseThrow(EOsType osType) {
        return versionJpaRepository.findByOsType(osType)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_VERSION));
    }
}
