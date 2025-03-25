package com.inglo.giggle.account.application.service;

import com.inglo.giggle.account.application.dto.response.ReadOwnerDetailResponseDto;
import com.inglo.giggle.account.application.usecase.ReadOwnerDetailUseCase;
import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.repository.OwnerRepository;
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
        Owner owner = ownerRepository.findByIdOrElseThrow(accountId);

        return ReadOwnerDetailResponseDto.fromEntity(owner);
    }

}
