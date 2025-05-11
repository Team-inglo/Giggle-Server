package com.inglo.giggle.version.application.service;

import com.inglo.giggle.version.domain.Version;
import com.inglo.giggle.version.application.port.in.result.ReadRecentVersionResult;
import com.inglo.giggle.version.application.port.in.query.ReadRecentVersionQuery;
import com.inglo.giggle.version.domain.type.EOsType;
import com.inglo.giggle.version.application.port.out.LoadVersionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadRecentVersionService implements ReadRecentVersionQuery {

    private final LoadVersionPort loadVersionPort;

    @Override
    public ReadRecentVersionResult execute(EOsType osType) {
        Version version = loadVersionPort.loadVersion(osType);

        return ReadRecentVersionResult.from(version);
    }
}
