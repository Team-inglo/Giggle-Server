package com.inglo.giggle.banner.application.service;

import com.inglo.giggle.banner.application.port.in.query.ReadBannerDetailQuery;
import com.inglo.giggle.banner.application.port.in.result.ReadBannerDetailResult;
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
public class ReadBannerDetailService implements ReadBannerDetailQuery {

    private final LoadBannerPort loadBannerPort;

    @Override
    @Transactional(readOnly = true)
    public ReadBannerDetailResult execute(ESecurityRole role, Long bannerId) {

        // Banner 조회
        Banner banner = loadBannerPort.loadBanner(bannerId);

        // 관리자인지 확인
        if (role == ESecurityRole.ADMIN) {
            return ReadBannerDetailResult.from(banner);
        }

        // Banner 권한 확인
        if (banner.getRole() != ESecurityRole.GUEST && !banner.getRole().equals(role)) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        return ReadBannerDetailResult.from(banner);
    }


}
