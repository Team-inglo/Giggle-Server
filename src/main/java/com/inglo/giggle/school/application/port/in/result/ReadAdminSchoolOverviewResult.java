package com.inglo.giggle.school.application.port.in.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inglo.giggle.core.dto.PageInfoDto;
import com.inglo.giggle.core.dto.SelfValidating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadAdminSchoolOverviewResult extends SelfValidating<ReadAdminSchoolOverviewResult> {

    @JsonProperty("accounts")
    private final List<SchoolOverviewDto> accounts;

    @JsonProperty("page_info")
    @NotNull(message = "페이지 정보를 입력해주세요.")
    private final PageInfoDto pageInfo;

    @Builder
    public ReadAdminSchoolOverviewResult(List<SchoolOverviewDto> accounts, PageInfoDto pageInfo) {
        this.accounts = accounts;
        this.pageInfo = pageInfo;
        this.validateSelf();
    }

    public static class SchoolOverviewDto extends SelfValidating<SchoolOverviewDto> {
        @JsonProperty("id")
        @NotNull(message = "아이디는 필수입니다.")
        private final Long id;

        @JsonProperty("school_name")
        @NotBlank(message = "학교 이름은 필수입니다.")
        private final String schoolName;

        @JsonProperty("school_phone_number")
        private final String schoolPhoneNumber;

        @JsonProperty("institute_name")
        private final String instituteName;

        @JsonProperty("coordinator_name")
        private final String coordinatorName;

        @JsonProperty("coordinator_phone_number")
        private final String coordinatorPhoneNumber;

        @JsonProperty("address_detail")
        private final String addressDetail;

        @Builder
        public SchoolOverviewDto(Long id, String schoolName, String schoolPhoneNumber, String instituteName, String coordinatorName, String coordinatorPhoneNumber, String addressDetail) {
            this.id = id;
            this.schoolName = schoolName;
            this.schoolPhoneNumber = schoolPhoneNumber;
            this.instituteName = instituteName;
            this.coordinatorName = coordinatorName;
            this.coordinatorPhoneNumber = coordinatorPhoneNumber;
            this.addressDetail = addressDetail;
            this.validateSelf();
        }

        public static SchoolOverviewDto of(
                Long id,
                String schoolName,
                String schoolPhoneNumber,
                String instituteName,
                String coordinatorName,
                String coordinatorPhoneNumber,
                String addressDetail
        ) {
            return SchoolOverviewDto.builder()
                    .id(id)
                    .schoolName(schoolName)
                    .schoolPhoneNumber(schoolPhoneNumber)
                    .instituteName(instituteName)
                    .coordinatorName(coordinatorName)
                    .coordinatorPhoneNumber(coordinatorPhoneNumber)
                    .addressDetail(addressDetail)
                    .build();
        }
    }
    public static ReadAdminSchoolOverviewResult of(List<SchoolOverviewDto> schools, PageInfoDto pageInfo) {
        return ReadAdminSchoolOverviewResult.builder()
                .accounts(schools)
                .pageInfo(pageInfo)
                .build();
    }
}
