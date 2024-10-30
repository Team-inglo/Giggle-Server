package com.inglo.giggle.document.application.service;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.address.domain.service.AddressService;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.document.application.dto.request.UpdateOwnerStandardLaborContractRequestDto;
import com.inglo.giggle.document.application.usecase.UpdateOwnerStandardLaborContractUseCase;
import com.inglo.giggle.document.domain.ContractWorkDayTime;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.service.ContractWorkDayTimeService;
import com.inglo.giggle.document.domain.service.StandardLaborContractService;
import com.inglo.giggle.document.domain.type.EEmployerStatus;
import com.inglo.giggle.document.domain.type.EInsurance;
import com.inglo.giggle.document.domain.type.EPaymentMethod;
import com.inglo.giggle.document.repository.mysql.ContractWorkDayTimeRepository;
import com.inglo.giggle.document.repository.mysql.StandardLaborContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpdateOwnerStandardLaborContractService implements UpdateOwnerStandardLaborContractUseCase {

    private final StandardLaborContractRepository standardLaborContractRepository;
    private final ContractWorkDayTimeRepository contractWorkDayTimeRepository;
    private final StandardLaborContractService standardLaborContractService;
    private final AddressService addressService;
    private final ContractWorkDayTimeService contractWorkDayTimeService;

    @Override
    @Transactional
    public void execute(Long documentId, UpdateOwnerStandardLaborContractRequestDto requestDto) {
        StandardLaborContract standardLaborContract = standardLaborContractRepository.findById(documentId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        if (standardLaborContract.getEmployerStatus() != null) {
            if (!standardLaborContract.getEmployerStatus().equals(EEmployerStatus.TEMPORARY_SAVE) &&
                    !standardLaborContract.getEmployerStatus().equals(EEmployerStatus.REWRITING))
                throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        List<ContractWorkDayTime> contractWorkDayTimes = contractWorkDayTimeRepository.findByStandardLaborContractId(documentId);
        contractWorkDayTimeRepository.deleteAll(contractWorkDayTimes);

        requestDto.workDayTimeList().stream()
                .map(workDayTime -> contractWorkDayTimeService.createContractWorkDayTime(
                        EDayOfWeek.fromString(workDayTime.dayOfWeek()),
                        workDayTime.workStartTime(),
                        workDayTime.workEndTime(),
                        workDayTime.breakStartTime(),
                        workDayTime.breakEndTime(),
                        standardLaborContract))
                .forEach(contractWorkDayTimeRepository::save);

        Address address = addressService.createAddress(
                requestDto.address().addressName(),
                requestDto.address().region1DepthName(),
                requestDto.address().region2DepthName(),
                requestDto.address().region3DepthName(),
                requestDto.address().region4DepthName(),
                requestDto.address().addressDetail(),
                requestDto.address().latitude(),
                requestDto.address().longitude()
        );

        StandardLaborContract updatedStandardLaborContract = standardLaborContractService.updateOwnerStandardLaborContract(
                standardLaborContract,
                requestDto.companyName(),
                requestDto.companyRegistrationNumber(),
                requestDto.phoneNumber(),
                requestDto.name(),
                requestDto.startDate(),
                requestDto.endDate(),
                address,
                requestDto.description(),
                requestDto.weeklyLastDays().stream().map(EDayOfWeek::fromString).collect(Collectors.toSet()),
                requestDto.hourlyRate(),
                requestDto.bonus(),
                requestDto.additionalSalary(),
                requestDto.wageRate(),
                requestDto.paymentDay(),
                EPaymentMethod.fromString(requestDto.paymentMethod()),
                requestDto.insurance().stream().map(EInsurance::fromString).collect(Collectors.toSet()),
                requestDto.signatureBase64()
        );

        standardLaborContractRepository.save(updatedStandardLaborContract);
    }
}
