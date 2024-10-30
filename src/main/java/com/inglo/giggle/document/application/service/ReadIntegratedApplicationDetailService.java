package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.dto.response.ReadIntegratedApplicationDetailResponseDto;
import com.inglo.giggle.document.application.usecase.ReadIntegratedApplicationDetailUseCase;
import com.inglo.giggle.document.domain.IntegratedApplication;
import com.inglo.giggle.document.repository.mysql.IntegratedApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadIntegratedApplicationDetailService implements ReadIntegratedApplicationDetailUseCase {
    private final IntegratedApplicationRepository integratedApplicationRepository;

    @Override
    public ReadIntegratedApplicationDetailResponseDto execute(Long documentId) {

        IntegratedApplication integratedApplication = integratedApplicationRepository.findWithSchoolById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        return ReadIntegratedApplicationDetailResponseDto.fromEntity(integratedApplication);
    }


}
