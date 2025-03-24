package com.inglo.giggle.posting.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public enum EWorkingHours {
    MORNING(LocalTime.of(6, 0), LocalTime.of(12, 0)),
    AFTERNOON(LocalTime.of(12, 0), LocalTime.of(18, 0)),
    EVENING(LocalTime.of(18, 0), LocalTime.of(22, 0)),
    FULL_DAY(LocalTime.of(12, 0), LocalTime.of(23, 59)),
    DAWN(LocalTime.of(0, 0), LocalTime.of(6, 0)),
    NEGOTIABLE_HOURS(LocalTime.MIN, LocalTime.MAX);  // 협의 가능한 경우 전체 시간 범위

    private final LocalTime startTime;
    private final LocalTime endTime;
}
