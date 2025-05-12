package com.inglo.giggle.version.application.port.out;

import com.inglo.giggle.version.domain.Version;
import com.inglo.giggle.version.domain.type.EOsType;

public interface LoadVersionPort {

    Version loadVersionOrElseThrow(EOsType osType);
}
