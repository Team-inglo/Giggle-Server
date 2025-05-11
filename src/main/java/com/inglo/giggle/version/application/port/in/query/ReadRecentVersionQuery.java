package com.inglo.giggle.version.application.port.in.query;

import com.inglo.giggle.version.application.port.in.result.ReadRecentVersionResult;
import com.inglo.giggle.version.domain.type.EOsType;

public interface ReadRecentVersionQuery {

    /**
     * 0.1 최근 버전 조회
     */
    ReadRecentVersionResult execute(
            EOsType osType
    );
}
