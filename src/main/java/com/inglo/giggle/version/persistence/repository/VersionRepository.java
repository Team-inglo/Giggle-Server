package com.inglo.giggle.version.persistence.repository;

import com.inglo.giggle.version.domain.Version;
import com.inglo.giggle.version.persistence.entity.VersionEntity;
import com.inglo.giggle.version.domain.type.EOsType;

public interface VersionRepository {

    Version findByOsTypeOrElseThrow(EOsType osType);
}
