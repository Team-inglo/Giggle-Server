package com.inglo.giggle.school.domain;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.dto.BaseDomain;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class School extends BaseDomain {

    private final Long id;
    private final String schoolName;
    private final String schoolPhoneNumber;
    private final String instituteName;
    private final String coordinatorName;
    private final String coordinatorPhoneNumber;
    private final Boolean isMetropolitan;
    private final Address address;

    @Builder
    public School(Long id,
                  String schoolName,
                  String schoolPhoneNumber,
                  String instituteName,
                  String coordinatorName,
                  String coordinatorPhoneNumber,
                  Boolean isMetropolitan,
                  Address address,
                  LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt
    ) {
        this.id = id;
        this.schoolName = schoolName;
        this.schoolPhoneNumber = schoolPhoneNumber;
        this.instituteName = instituteName;
        this.coordinatorName = coordinatorName;
        this.coordinatorPhoneNumber = coordinatorPhoneNumber;
        this.isMetropolitan = isMetropolitan;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public School updateSelf(String schoolName, String schoolPhoneNumber, String instituteName,
                             String coordinatorName, String coordinatorPhoneNumber, Address address, Boolean isMetropolitan) {
        return School.builder()
                .id(this.id)
                .schoolName(schoolName)
                .schoolPhoneNumber(schoolPhoneNumber)
                .instituteName(instituteName)
                .coordinatorName(coordinatorName)
                .coordinatorPhoneNumber(coordinatorPhoneNumber)
                .isMetropolitan(isMetropolitan)
                .address(address)
                .build();
    }
}

