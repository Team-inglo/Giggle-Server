package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.application.port.in.query.ReadGuestBannerDetailQuery;
import com.inglo.giggle.banner.application.port.in.result.ReadGuestBannerDetailResult;
import com.inglo.giggle.banner.application.port.out.LoadBannerPort;
import com.inglo.giggle.banner.domain.Banner;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadGuestBannerDetailService implements ReadGuestBannerDetailQuery {
    private final LoadBannerPort loadBannerPort;

    @Override
    @Transactional(readOnly = true)
    public ReadGuestBannerDetailResult execute(Long bannerId) {

        // Banner 조회
        Banner banner = loadBannerPort.loadBannerOrElseThrow(bannerId);

        // Banner 권한 확인
        if (!banner.getRole().equals(ESecurityRole.USER) && !banner.getRole().equals(ESecurityRole.GUEST)) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        return ReadGuestBannerDetailResult.from(banner);
    }


}
