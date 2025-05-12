package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.dto.AddressResponseDto;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.core.utility.DateTimeUtil;
import com.inglo.giggle.document.application.port.in.query.ReadStandardLaborContractDetailQuery;
import com.inglo.giggle.document.application.port.in.result.ReadStandardLaborContractDetailResult;
import com.inglo.giggle.document.application.port.out.LoadStandardLaborContractPort;
import com.inglo.giggle.document.domain.StandardLaborContract;
import com.inglo.giggle.document.domain.type.EInsurance;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccountRoleQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountRoleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadStandardLaborContractService implements ReadStandardLaborContractDetailQuery {

    private final LoadStandardLaborContractPort loadStandardLaborContractPort;

    private final ReadAccountRoleQuery readAccountRoleQuery;

    @Override
    public ReadStandardLaborContractDetailResult execute(UUID accountId, Long documentId) {

        // Account 조회
        ReadAccountRoleResult readAccountRoleResult = readAccountRoleQuery.execute(accountId);

        // TODO: UOJP 합치기
        // UserOwnerJobPosting 정보 조회
//        UserOwnerJobPosting userOwnerJobPosting = userOwnerJobPostingRepository.findByDocumentOrElseThrow(document);

        switch (readAccountRoleResult.getRole()) {
            case USER:

                // UserOwnerJobPosting 유저 유효성 체크
//                userOwnerJobPosting.checkUserUserOwnerJobPostingValidation(accountId);

                break;

            case OWNER:

                // UserOwnerJobPosting 고용주 유효성 체크
//                userOwnerJobPosting.checkOwnerUserOwnerJobPostingValidation(accountId);

                break;

            default:
                throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }

        // StandardLaborContract 조회
        StandardLaborContract standardLaborContract = loadStandardLaborContractPort.loadStandardLaborContractWithContractWorkDayTimesOrElseThrow(documentId);

        ReadStandardLaborContractDetailResult.EmployeeInformationDto employeeInformationDto;
        ReadStandardLaborContractDetailResult.EmployerInformationDto employerInformationDto;

        employeeInformationDto = ReadStandardLaborContractDetailResult.EmployeeInformationDto.of(
                standardLaborContract.getEmployeeFirstName(),
                standardLaborContract.getEmployeeLastName(),
                AddressResponseDto.from(standardLaborContract.getEmployeeAddress()),
                standardLaborContract.getEmployeePhoneNumber(),
                standardLaborContract.getEmployeeSignatureBase64()
        );
        if (standardLaborContract.getCompanyName() == null) {
            employerInformationDto = null;
        } else {
            employerInformationDto = ReadStandardLaborContractDetailResult.EmployerInformationDto.of(
                    standardLaborContract.getCompanyName(),
                    standardLaborContract.getEmployerPhoneNumber(),
                    standardLaborContract.getCompanyRegistrationNumber(),
                    standardLaborContract.getEmployerName(),
                    DateTimeUtil.convertLocalDateToString(standardLaborContract.getStartDate()),
                    DateTimeUtil.convertLocalDateToString(standardLaborContract.getEndDate()),
                    AddressResponseDto.from(standardLaborContract.getEmployerAddress()),
                    standardLaborContract.getDescription(),
                    standardLaborContract.getWorkDayTimes().stream().map(workDayTime ->
                            ReadStandardLaborContractDetailResult.EmployerInformationDto.WorkDayTimeDto.builder()
                                    .dayOfWeek(workDayTime.getDayOfWeek().toString())
                                    .workStartTime(DateTimeUtil.convertLocalTimeToString(workDayTime.getWorkStartTime()))
                                    .workEndTime(DateTimeUtil.convertLocalTimeToString(workDayTime.getWorkEndTime()))
                                    .breakStartTime(DateTimeUtil.convertLocalTimeToString(workDayTime.getBreakStartTime()))
                                    .breakEndTime(DateTimeUtil.convertLocalTimeToString(workDayTime.getBreakEndTime()))
                                    .build()
                    ).toList(),
                    standardLaborContract.getWeeklyRestDays().stream().map(EDayOfWeek::toString).toList(),
                    standardLaborContract.getHourlyRate(),
                    standardLaborContract.getBonus(),
                    standardLaborContract.getAdditionalSalary(),
                    standardLaborContract.getWageRate(),
                    standardLaborContract.getPaymentDay(),
                    standardLaborContract.getPaymentMethod().toString(),
                    standardLaborContract.getInsurances().stream().map(EInsurance::toString).toList(),
                    standardLaborContract.getEmployerSignatureBase64()
            );
        }

        return ReadStandardLaborContractDetailResult.of(
                employeeInformationDto,
                employerInformationDto
        );
    }

}
