package com.inglo.giggle.resume.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class UpdateEducationCommand extends SelfValidating<UpdateEducationCommand> {
    @NotNull(message = "accountId은 필수 입력 값입니다.")
    private final UUID accountId;

    @NotNull(message = "educationId는 필수 입력 값입니다.")
    @Positive(message = "education_id는 양수여야 합니다.")
    @Max(value = Long.MAX_VALUE, message = "education_id는 최대값을 초과할 수 없습니다.")
    private final Long educationId;

    @NotNull(message = "education_level은 필수 입력 값입니다.")
    private final String educationLevel;

    @NotNull(message = "school_id는 필수 입력 값입니다.")
    @Positive(message = "school_id는 양수여야 합니다.")
    @Max(value = Long.MAX_VALUE, message = "school_id는 최대값을 초과할 수 없습니다.")
    private final Long schoolId;

    @NotNull(message = "major은 필수 입력 값입니다.")
    @Size(min = 1, max = 30, message = "전공은 1자 이상 30자 이하로 입력 가능합니다.")
    private final String major;

    @NotNull(message = "gpa는 필수 입력 값입니다.")
    @Max(value = 5, message = "gpa는 5 이하로 입력 가능합니다.")
    @Min(value = 0, message = "gpa는 0 이상으로 입력 가능합니다.")
    private final Double gpa;

    @NotNull(message = "start_date은 필수 입력 값입니다.")
    private final LocalDate startDate;

    @NotNull(message = "end_date은 필수 입력 값입니다.")
    private final LocalDate endDate;

    @NotNull(message = "grade은 필수 입력 값입니다.")
    @Max(value = 6, message = "grade은 6 이하로 입력 가능합니다.")
    @Min(value = 1, message = "grade은 1 이상으로 입력 가능합니다.")
    private final Integer grade;

    public UpdateEducationCommand(
            UUID accountId,
            Long educationId,
            String educationLevel,
            Long schoolId,
            String major,
            Double gpa,
            LocalDate startDate,
            LocalDate endDate,
            Integer grade
    ) {
        this.accountId = accountId;
        this.educationId = educationId;
        this.educationLevel = educationLevel;
        this.schoolId = schoolId;
        this.major = major;
        this.gpa = gpa;
        this.startDate = startDate;
        this.endDate = endDate;
        this.grade = grade;

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new CommonException(ErrorCode.INVALID_DATE_RANGE);
        }

        this.validateSelf();
    }
}
