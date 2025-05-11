package com.inglo.giggle.school.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.school.domain.School;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadUserSchoolBriefByIdsResult extends SelfValidating<ReadUserSchoolBriefByIdsResult> {

    @JsonProperty("school_list")
    private final List<SchoolListDto> schoolList;

    public ReadUserSchoolBriefByIdsResult(List<SchoolListDto> schoolList) {
        this.schoolList = schoolList;

        this.validateSelf();
    }

    @Getter
    public static class SchoolListDto extends SelfValidating<SchoolListDto> {
        @JsonProperty("id")
        private final Long id;

        @JsonProperty("name")
        private final String name;

        @Builder
        public SchoolListDto(Long id, String name) {
            this.id = id;
            this.name = name;

            this.validateSelf();
        }

        public static SchoolListDto of(Long id, String name) {
            return SchoolListDto.builder()
                    .id(id)
                    .name(name)
                    .build();
        }
    }

    public static ReadUserSchoolBriefByIdsResult of(List<SchoolListDto> schools) {
        return new ReadUserSchoolBriefByIdsResult(schools);
    }
}
