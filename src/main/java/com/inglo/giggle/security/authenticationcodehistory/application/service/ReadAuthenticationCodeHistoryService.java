package com.inglo.giggle.security.authenticationcodehistory.application.service;

import com.inglo.giggle.security.authenticationcodehistory.application.port.in.query.ReadAuthenticationCodeHistoryQuery;
import com.inglo.giggle.security.authenticationcodehistory.application.port.in.result.ReadAuthenticationCodeHistoryResult;
import com.inglo.giggle.security.authenticationcodehistory.application.port.out.LoadAuthenticationCodeHistoryPort;
import com.inglo.giggle.security.authenticationcodehistory.domain.AuthenticationCodeHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadAuthenticationCodeHistoryService implements ReadAuthenticationCodeHistoryQuery {
    
    private final LoadAuthenticationCodeHistoryPort loadAuthenticationCodeHistoryPort;
    
    public ReadAuthenticationCodeHistoryResult execute(String email) {

        // 인증 코드 전송 이력 조회
        AuthenticationCodeHistory authenticationCodeHistory = loadAuthenticationCodeHistoryPort.loadAuthenticationCodeHistoryOrElseNull(email);

        // 인증 코드 전송 이력이 없을 경우
        if (authenticationCodeHistory == null) {
            return ReadAuthenticationCodeHistoryResult.of(0, null);
        }
        return ReadAuthenticationCodeHistoryResult.of(authenticationCodeHistory.getCount(), authenticationCodeHistory.getLastSentAt());
    }
}
