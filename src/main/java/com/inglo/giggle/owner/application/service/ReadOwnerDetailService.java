package com.inglo.giggle.owner.application.service;

import com.inglo.giggle.core.dto.AddressResponseDto;
import com.inglo.giggle.owner.application.port.in.query.ReadOwnerDetailQuery;
import com.inglo.giggle.owner.application.port.in.result.ReadOwnerDetailResult;
import com.inglo.giggle.owner.application.port.out.LoadOwnerPort;
import com.inglo.giggle.owner.domain.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ReadOwnerDetailService implements ReadOwnerDetailQuery {

    private final LoadOwnerPort loadOwnerPort;

    @Override
    @Transactional(readOnly = true)
    public ReadOwnerDetailResult execute(UUID accountId) {
        // 고용주 정보 조회
        Owner owner = loadOwnerPort.loadOwnerOrElseThrow(accountId);

        return ReadOwnerDetailResult.of(
                owner.getCompanyName(),
                owner.getOwnerName(),
                AddressResponseDto.from(owner.getAddress()),
                owner.getCompanyRegistrationNumber(),
                owner.getPhoneNumber(),
                owner.getProfileImgUrl(),
                owner.getNotificationAllowed()
        );
    }

}
