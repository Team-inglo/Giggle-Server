package com.inglo.giggle.user.application.port.out;

import com.inglo.giggle.user.domain.User;

import java.util.UUID;

public interface LoadUserPort {

    User loadUserOrElseThrow(UUID accountId);

    User loadUserOrElseNull(UUID accountId);
}
