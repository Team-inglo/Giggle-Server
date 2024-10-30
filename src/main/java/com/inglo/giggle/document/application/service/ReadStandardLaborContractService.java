package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.dto.response.ReadStandardLaborContractDetailResponseDto;
import com.inglo.giggle.document.application.usecase.ReadStandardLaborContractDetailUseCase;
import com.inglo.giggle.document.domain.ContractWorkDayTime;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.repository.mysql.ContractWorkDayTimeRepository;
import com.inglo.giggle.document.repository.mysql.StandardLaborContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadStandardLaborContractService implements ReadStandardLaborContractDetailUseCase {
    private final StandardLaborContractRepository standardLaborContractRepository;
    private final ContractWorkDayTimeRepository contractWorkDayTimeRepository;

    @Override
    public ReadStandardLaborContractDetailResponseDto execute(Long documentId) {

        StandardLaborContract standardLaborContract = standardLaborContractRepository.findWithWeeklyRestDaysAndInsurancesById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        List<ContractWorkDayTime> contractWorkDayTimes = contractWorkDayTimeRepository.findByStandardLaborContractId(documentId);
        return ReadStandardLaborContractDetailResponseDto.of(
                standardLaborContract,
                contractWorkDayTimes
        );
    }
}
