package com.inglo.giggle.user.application.service;

import com.inglo.giggle.core.dto.AddressResponseDto;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.user.application.port.in.query.ReadUserSelfDetailQuery;
import com.inglo.giggle.user.application.port.in.result.ReadUserSelfDetailResult;
import com.inglo.giggle.user.application.port.out.LoadUserPort;
import com.inglo.giggle.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserSelfDetailService implements ReadUserSelfDetailQuery {

    private final LoadUserPort loadUserPort;

    @Override
    @Transactional(readOnly = true)
    public ReadUserSelfDetailResult execute(UUID accountId) {

        // 유저 정보 조회
        User user = loadUserPort.loadUserOrElseThrow(accountId);

        return ReadUserSelfDetailResult.of(
                user.getEmail(),
                user.getProfileImgUrl(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirth() != null ? DateTimeUtil.convertLocalDateToString(user.getBirth()) : null,
                user.getGender().name(),
                user.getNationality() != null ? user.getNationality() : null,
                user.getVisa().name(),
                user.getPhoneNumber(),
                user.getAddress() != null ? AddressResponseDto.from(user.getAddress()) : null
        );
    }

}
