package com.inglo.giggle.document.application.service;

import com.inglo.giggle.core.dto.AddressResponseDto;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.application.port.in.query.ReadPartTimeEmploymentPermitDetailQuery;
import com.inglo.giggle.document.application.port.in.result.ReadPartTimeEmploymentPermitDetailResult;
import com.inglo.giggle.document.application.port.out.LoadPartTimeEmploymentPermitPort;
import com.inglo.giggle.document.domain.PartTimeEmploymentPermit;
import com.inglo.giggle.security.account.application.port.in.query.ReadAccountRoleQuery;
import com.inglo.giggle.security.account.application.port.in.result.ReadAccountRoleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadPartTimeEmploymentPermitDetailService implements ReadPartTimeEmploymentPermitDetailQuery {

    private final LoadPartTimeEmploymentPermitPort loadPartTimeEmploymentPermitPort;

    private final ReadAccountRoleQuery readAccountRoleQuery;

    @Override
    public ReadPartTimeEmploymentPermitDetailResult execute(UUID accountId, Long documentId) {

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

        // PartTimeEmploymentPermit 조회
        PartTimeEmploymentPermit partTimeEmploymentPermit = loadPartTimeEmploymentPermitPort.loadPartTimeEmploymentPermit(documentId);

        ReadPartTimeEmploymentPermitDetailResult.EmployeeInformationDto employeeInformationDto;
        ReadPartTimeEmploymentPermitDetailResult.EmployerInformationDto employerInformationDto;

        employeeInformationDto = ReadPartTimeEmploymentPermitDetailResult.EmployeeInformationDto.of(
                partTimeEmploymentPermit.getEmployeeFirstName(),
                partTimeEmploymentPermit.getEmployeeLastName(),
                partTimeEmploymentPermit.getMajor(),
                partTimeEmploymentPermit.getTermOfCompletion(),
                partTimeEmploymentPermit.getEmployeePhoneNumber(),
                partTimeEmploymentPermit.getEmployeeEmail()
        );

        if (partTimeEmploymentPermit.getCompanyName() == null) {
            employerInformationDto = null;
        } else {
            employerInformationDto = ReadPartTimeEmploymentPermitDetailResult.EmployerInformationDto.of(
                    partTimeEmploymentPermit.getCompanyName(),
                    partTimeEmploymentPermit.getCompanyRegistrationNumber(),
                    partTimeEmploymentPermit.getJobType(),
                    partTimeEmploymentPermit.getEmployerName(),
                    partTimeEmploymentPermit.getEmployerPhoneNumber(),
                    partTimeEmploymentPermit.getEmployerSignatureBase64(),
                    partTimeEmploymentPermit.getWorkPeriod().getEnName(),
                    partTimeEmploymentPermit.getHourlyRate(),
                    partTimeEmploymentPermit.getWorkDaysWeekDays(),
                    partTimeEmploymentPermit.getWorkDaysWeekends(),
                    AddressResponseDto.from(partTimeEmploymentPermit.getEmployerAddress())
            );
        }

        return ReadPartTimeEmploymentPermitDetailResult.of(employeeInformationDto, employerInformationDto);
    }
}
