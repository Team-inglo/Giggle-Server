package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.dto.response.ReadPartTimeEmploymentPermitDetailResponseDto;
import com.inglo.giggle.document.application.usecase.ReadPartTimeEmploymentPermitDetailUseCase;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.repository.mysql.PartTimeEmploymentPermitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadPartTimeEmploymentPermitDetailService implements ReadPartTimeEmploymentPermitDetailUseCase {
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;

    @Override
    public ReadPartTimeEmploymentPermitDetailResponseDto execute(Long documentId) {
        PartTimeEmploymentPermit partTimeEmploymentPermit = partTimeEmploymentPermitRepository.findById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
        return ReadPartTimeEmploymentPermitDetailResponseDto.fromEntity(partTimeEmploymentPermit);
    }
}
