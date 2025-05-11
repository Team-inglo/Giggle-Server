package com.inglo.giggle.user.application.service;

import com.inglo.giggle.user.application.port.in.query.ReadUserNameQuery;
import com.inglo.giggle.user.application.port.in.result.ReadUserNameResult;
import com.inglo.giggle.user.application.port.out.LoadUserPort;
import com.inglo.giggle.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserNameService implements ReadUserNameQuery {

    private final LoadUserPort loadUserPort;

    @Override
    public ReadUserNameResult execute(UUID accountId) {
        User user = loadUserPort.loadUserOrElseNull(accountId);
        return user != null ? new ReadUserNameResult(user.getName()) : null;
    }
}
