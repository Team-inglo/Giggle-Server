package com.inglo.giggle.document.adapter.in.web;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.document.adapter.in.web.dto.UpdateOwnerPartTimeEmploymentPermitRequestDto;
import com.inglo.giggle.document.adapter.in.web.dto.UpdateOwnerStandardLaborContractRequestDto;
import com.inglo.giggle.document.application.port.in.command.UpdateOwnerDocumentStatusSubmissionCommand;
import com.inglo.giggle.document.application.port.in.command.UpdateOwnerPartTimeEmploymentPermitCommand;
import com.inglo.giggle.document.application.port.in.command.UpdateOwnerStandardLaborContractCommand;
import com.inglo.giggle.document.application.port.in.usecase.UpdateOwnerDocumentStatusSubmissionUseCase;
import com.inglo.giggle.document.application.port.in.usecase.UpdateOwnerPartTimeEmploymentPermitUseCase;
import com.inglo.giggle.document.application.port.in.usecase.UpdateOwnerStandardLaborContractUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/owners")
public class DocumentOwnersCommandV1Controller {

    private final UpdateOwnerPartTimeEmploymentPermitUseCase updateOwnerPartTimeEmploymentPermitUseCase;
    private final UpdateOwnerStandardLaborContractUseCase updateOwnerStandardLaborContractUseCase;
    private final UpdateOwnerDocumentStatusSubmissionUseCase updateOwnerDocumentStatusSubmissionUseCase;

    /**
     * 8.11 (고용주) 시간제 취업허가서 수정하기
     */
    @PutMapping("/documents/{id}/part-time-employment-permits")
    public ResponseDto<Void> updateOwnerPartTimeEmploymentPermit(
            @AccountID UUID accountId,
            @PathVariable Long id,
            @RequestBody @Valid UpdateOwnerPartTimeEmploymentPermitRequestDto requestDto
    ) {

        UpdateOwnerPartTimeEmploymentPermitCommand command = new UpdateOwnerPartTimeEmploymentPermitCommand(
                accountId,
                id,
                requestDto.companyName(),
                requestDto.companyRegistrationNumber(),
                requestDto.jobType(),
                requestDto.name(),
                requestDto.phoneNumber(),
                requestDto.signatureBase64(),
                requestDto.workPeriod(),
                requestDto.hourlyRate(),
                requestDto.workDaysWeekdays(),
                requestDto.workDaysWeekends(),
                requestDto.address()
        );

        updateOwnerPartTimeEmploymentPermitUseCase.execute(command);
        return ResponseDto.ok(null);
    }

    /**
     * 8.13 (고용주) 근로계약서 수정하기
     */
    @PutMapping("/documents/{id}/standard-labor-contracts")
    public ResponseDto<Void> updateOwnerStandardLaborContract(
            @AccountID UUID accountId,
            @PathVariable Long id,
            @RequestBody @Valid UpdateOwnerStandardLaborContractRequestDto requestDto
    ) {
        UpdateOwnerStandardLaborContractCommand command = new UpdateOwnerStandardLaborContractCommand(
                accountId,
                id,
                requestDto.companyName(),
                requestDto.companyRegistrationNumber(),
                requestDto.phoneNumber(),
                requestDto.name(),
                requestDto.startDate(),
                requestDto.endDate(),
                requestDto.address(),
                requestDto.description(),
                requestDto.workDayTimeList().stream()
                        .map(workDayTime -> new UpdateOwnerStandardLaborContractCommand.WorkDayTime(
                                workDayTime.dayOfWeek(),
                                workDayTime.workStartTime(),
                                workDayTime.workEndTime(),
                                workDayTime.breakStartTime(),
                                workDayTime.breakEndTime()
                        ))
                        .toList(),
                requestDto.weeklyLastDays(),
                requestDto.hourlyRate(),
                requestDto.bonus(),
                requestDto.additionalSalary(),
                requestDto.wageRate(),
                requestDto.paymentDay(),
                requestDto.paymentMethod(),
                requestDto.insurance(),
                requestDto.signatureBase64()
        );
        updateOwnerStandardLaborContractUseCase.execute(command);
        return ResponseDto.ok(null);
    }

    /**
     * 8.16 (고용주) 서류 제출하기
     */
    @PatchMapping("/documents/{id}/status/submission")
    public ResponseDto<Void> updateOwnerDocumentStatusSubmission(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        UpdateOwnerDocumentStatusSubmissionCommand command = new UpdateOwnerDocumentStatusSubmissionCommand(
                accountId,
                id
        );
        updateOwnerDocumentStatusSubmissionUseCase.execute(command);
        return ResponseDto.ok(null);
    }
}
