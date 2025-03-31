package com.inglo.giggle.version.application.service;

import com.inglo.giggle.version.application.dto.response.ReadRecentVersionResponseDto;
import com.inglo.giggle.version.application.usecase.ReadRecentVersionUseCase;
import com.inglo.giggle.version.domain.Version;
import com.inglo.giggle.version.domain.type.EOsType;
import com.inglo.giggle.version.repository.VersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadRecentVersionService implements ReadRecentVersionUseCase {

    private final VersionRepository versionRepository;

    @Override
    public ReadRecentVersionResponseDto execute(EOsType osType) {
        Version version = versionRepository.findByOsTypeOrElseThrow(osType);

        return ReadRecentVersionResponseDto.fromEntity(version);
    }
}
