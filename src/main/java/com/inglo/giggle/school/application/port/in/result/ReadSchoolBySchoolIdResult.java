package com.inglo.giggle.school.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import lombok.Getter;

@Getter
public class ReadSchoolBySchoolIdResult extends SelfValidating<ReadSchoolBySchoolIdResult> {

    @JsonProperty("school_id")
    private Long schoolId;

    @JsonProperty("school_name")
    private String schoolName;

    @JsonProperty("school_phone_number")
    private String schoolPhoneNumber;

    public ReadSchoolBySchoolIdResult(Long schoolId, String schoolName, String schoolPhoneNumber) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.schoolPhoneNumber = schoolPhoneNumber;

        this.validateSelf();
    }
}
