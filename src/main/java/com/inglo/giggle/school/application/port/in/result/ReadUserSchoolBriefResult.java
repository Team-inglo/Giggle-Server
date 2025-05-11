package com.inglo.giggle.school.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadUserSchoolBriefResult extends SelfValidating<ReadUserSchoolBriefResult> {

    @JsonProperty("school_list")
    private final List<SchoolListDto> schoolList;

    @JsonProperty("has_next")
    private final boolean hasNext;

    @Builder
    public ReadUserSchoolBriefResult(List<SchoolListDto> schoolList, boolean hasNext) {
        this.schoolList = schoolList;
        this.hasNext = hasNext;

        this.validateSelf();
    }

    public static class SchoolListDto extends SelfValidating<SchoolListDto> {
        @JsonProperty("id")
        private final Long id;

        @JsonProperty("name")
        private final String name;

        @JsonProperty("phone_number")
        private final String phoneNumber;

        @Builder
        public SchoolListDto(Long id, String name, String phoneNumber) {
            this.id = id;
            this.name = name;
            this.phoneNumber = phoneNumber;

            this.validateSelf();
        }
    }
}
