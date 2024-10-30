package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.response.ReadStandardLaborContractDetailResponseDto;

@UseCase
public interface ReadStandardLaborContractDetailUseCase {
    ReadStandardLaborContractDetailResponseDto execute(Long documentId);
}
