package com.inglo.giggle.document.domain;

import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.core.type.EDayOfWeek;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class ContractWorkDayTime extends BaseDomain {
    private Long id;
    private EDayOfWeek dayOfWeek;
    private LocalTime workStartTime;
    private LocalTime workEndTime;
    private LocalTime breakStartTime;
    private LocalTime breakEndTime;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    private Long standardLaborContractId;

    @Builder
    public ContractWorkDayTime(Long id, EDayOfWeek dayOfWeek, LocalTime workStartTime, LocalTime workEndTime,
                               LocalTime breakStartTime, LocalTime breakEndTime, Long standardLaborContractId, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
        this.standardLaborContractId = standardLaborContractId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
}

