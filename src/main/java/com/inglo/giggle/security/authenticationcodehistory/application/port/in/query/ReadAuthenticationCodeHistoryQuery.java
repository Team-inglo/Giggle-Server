package com.inglo.giggle.security.authenticationcodehistory.application.port.in.query;

import com.inglo.giggle.security.authenticationcodehistory.application.port.in.result.ReadAuthenticationCodeHistoryResult;

public interface ReadAuthenticationCodeHistoryQuery {

    ReadAuthenticationCodeHistoryResult execute(String email);
}
