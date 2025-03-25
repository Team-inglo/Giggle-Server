package com.inglo.giggle.account.application.service;

import com.inglo.giggle.account.application.dto.response.ReadOwnerBriefResponseDto;
import com.inglo.giggle.account.application.usecase.ReadOwnerBriefUseCase;
import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadOwnerBriefService implements ReadOwnerBriefUseCase {

    private final OwnerRepository ownerRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadOwnerBriefResponseDto execute(UUID accountId) {

        // 고용주 정보 조회
        Owner owner = ownerRepository.findByIdOrElseThrow(accountId);

        return ReadOwnerBriefResponseDto.fromEntity(owner);
    }

}
