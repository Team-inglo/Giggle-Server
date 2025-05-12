package com.inglo.giggle.security.temporarytoken.application.service;

import com.inglo.giggle.security.temporarytoken.application.port.in.query.ReadTemporaryTokenQuery;
import com.inglo.giggle.security.temporarytoken.application.port.in.result.ReadTemporaryTokenResult;
import com.inglo.giggle.security.temporarytoken.application.port.out.LoadTemporaryTokenPort;
import com.inglo.giggle.security.temporarytoken.domain.TemporaryToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadTemporaryTokenService implements ReadTemporaryTokenQuery {

    private final LoadTemporaryTokenPort loadTemporaryTokenPort;

    @Override
    public ReadTemporaryTokenResult execute(String value) {
        TemporaryToken temporaryToken = loadTemporaryTokenPort.loadTemporaryTokenOrElseThrow(value);
        return new ReadTemporaryTokenResult(
                temporaryToken.getEmail(),
                temporaryToken.getValue()
        );
    }
}
