package com.inglo.giggle.version.repository;

import com.inglo.giggle.version.domain.Version;
import com.inglo.giggle.version.domain.type.EOsType;

public interface VersionRepository {

    Version findByOsTypeOrElseThrow(EOsType osType);
}
