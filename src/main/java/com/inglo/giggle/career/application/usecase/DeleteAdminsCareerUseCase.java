package com.inglo.giggle.career.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;

@UseCase
public interface DeleteAdminsCareerUseCase {

    void execute(
            Long careerId
    );
}
