package com.inglo.giggle.security.service;

import com.inglo.giggle.security.dto.response.ValidationResponseDto;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
import com.inglo.giggle.security.usecase.ValidateIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateIdService implements ValidateIdUseCase {
    private final AccountRepository accountRepository;
    @Override
    public ValidationResponseDto execute(String id) {
        return ValidationResponseDto.builder()
                .isValid(isDuplicatedId(id))
                .build();
    }

    /**
     * 중복된 아이디인지 확인
     * @param id 아이디
     * @return 중복된 아이디인지 여부
     */
    private Boolean isDuplicatedId(String id) {
        return accountRepository.findBySerialId(id).isPresent();
    }
}
