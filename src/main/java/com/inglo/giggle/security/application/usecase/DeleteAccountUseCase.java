package com.inglo.giggle.security.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface DeleteAccountUseCase {
    void execute(UUID accountId);
}
