package com.inglo.giggle.user.application.port.out;

import com.inglo.giggle.user.domain.User;

public interface DeleteUserPort {

    void deleteUser(User user);
}
