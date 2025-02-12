package com.inglo.giggle.security.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.application.dto.request.ChangePasswordRequestDto;
import com.inglo.giggle.security.application.usecase.ChangePasswordUseCase;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChangePasswordService implements ChangePasswordUseCase {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void execute(UUID accountId, ChangePasswordRequestDto requestDto) {
        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT));

        // 현재 비밀번호 확인
        if (!(bCryptPasswordEncoder.matches(requestDto.currentPassword(), account.getPassword()))) {
            throw new CommonException(ErrorCode.INVALID_PASSWORD);
        }

        // 비밀번호 변경
        account.updatePassword(bCryptPasswordEncoder.encode(requestDto.newPassword()));
        accountRepository.save(account);
    }
}
