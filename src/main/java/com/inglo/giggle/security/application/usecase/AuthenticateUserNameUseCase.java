package com.inglo.giggle.security.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import org.springframework.security.core.userdetails.UserDetailsService;

@UseCase
public interface AuthenticateUserNameUseCase extends UserDetailsService {
}
