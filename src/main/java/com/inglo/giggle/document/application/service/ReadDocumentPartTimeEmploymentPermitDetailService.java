package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.dto.response.ReadDocumentPartTimeEmploymentPermitDetailResponseDto;
import com.inglo.giggle.document.application.usecase.ReadDocumentPartTimeEmploymentPermitDetailUseCase;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.document.repository.mysql.PartTimeEmploymentPermitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadDocumentPartTimeEmploymentPermitDetailService implements ReadDocumentPartTimeEmploymentPermitDetailUseCase {
    private final PartTimeEmploymentPermitRepository partTimeEmploymentPermitRepository;

    @Override
    public ReadDocumentPartTimeEmploymentPermitDetailResponseDto execute(Long documentId) {
        PartTimeEmploymentPermit partTimeEmploymentPermit = partTimeEmploymentPermitRepository.findById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
        return ReadDocumentPartTimeEmploymentPermitDetailResponseDto.fromEntity(partTimeEmploymentPermit);
    }
}
