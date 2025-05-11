package com.inglo.giggle.security.account.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.account.application.port.in.command.ChangePasswordCommand;
import com.inglo.giggle.security.account.application.port.in.usecase.ChangePasswordUseCase;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import com.inglo.giggle.security.account.application.port.out.UpdateAccountPort;
import com.inglo.giggle.security.account.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangePasswordService implements ChangePasswordUseCase {

    private final LoadAccountPort loadAccountPort;
    private final UpdateAccountPort updateAccountPort;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void execute(ChangePasswordCommand command) {
        // Account 조회
        Account account = loadAccountPort.loadAccount(command.getAccountId());

        // 현재 비밀번호 확인
        if (!(bCryptPasswordEncoder.matches(command.getCurrentPassword(), account.getPassword()))) {
            throw new CommonException(ErrorCode.INVALID_PASSWORD);
        }

        // 비밀번호 변경
        account.updatePassword(bCryptPasswordEncoder.encode(command.getNewPassword()));
        updateAccountPort.updateAccount(account);
    }
}
