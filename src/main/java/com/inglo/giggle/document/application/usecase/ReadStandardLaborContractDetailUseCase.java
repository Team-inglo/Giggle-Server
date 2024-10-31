package com.inglo.giggle.document.application.usecase;

import com.inglo.giggle.core.annotation.bean.UseCase;
import com.inglo.giggle.document.application.dto.response.ReadStandardLaborContractDetailResponseDto;

import java.util.UUID;

@UseCase
public interface ReadStandardLaborContractDetailUseCase {
    ReadStandardLaborContractDetailResponseDto execute(UUID accountId, Long documentId);
}
