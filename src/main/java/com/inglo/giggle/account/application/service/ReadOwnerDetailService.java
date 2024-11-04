package com.inglo.giggle.account.application.service;

import com.inglo.giggle.account.application.dto.response.ReadOwnerDetailResponseDto;
import com.inglo.giggle.account.application.usecase.ReadOwnerDetailUseCase;
import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.repository.mysql.OwnerRepository;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ReadOwnerDetailService implements ReadOwnerDetailUseCase {

    private final OwnerRepository ownerRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadOwnerDetailResponseDto execute(UUID accountId) {
        // 고용주 정보 조회
        Owner owner = ownerRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        return ReadOwnerDetailResponseDto.fromEntity(owner);
    }

}
