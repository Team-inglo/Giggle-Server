package com.inglo.giggle.document.domain;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.document.domain.type.EEmployeeStatus;
import com.inglo.giggle.document.domain.type.EEmployerStatus;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "part_time_employment_permits")
@PrimaryKeyJoinColumn(
        name = "document_id",
        foreignKey = @ForeignKey(name = "fk_part_time_employment_permit_document")
)
public class PartTimeEmploymentPermit extends Document {

    /* -------------------------------------------- */
    /* Information Column(Employee) --------------- */
    /* -------------------------------------------- */
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Column(name = "major", length = 50, nullable = false)
    private String major;

    @Column(name = "term_of_completion", nullable = false)
    private Integer termOfCompletion;

    @Column(name = "employee_phone_number", length = 20, nullable = false)
    private String employeePhoneNumber;

    @Column(name = "employee_email", length = 320, nullable = false)
    private String employeeEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_status", nullable = false)
    private EEmployeeStatus employeeStatus;

    /* -------------------------------------------- */
    /* Information Column(Employer) --------------- */
    /* -------------------------------------------- */
    @Column(name = "company_name", length = 20)
    private String companyName;

    @Column(name = "company_registration_number", length = 12)
    private String companyRegistrationNumber;

    @Column(name = "job_type", length = 20)
    private String jobType;

    @Enumerated(EnumType.STRING)
    @Column(name = "employer_status")
    private EEmployerStatus employerStatus;

    /* -------------------------------------------- */
    /* Embedded Column ---------------------------- */
    /* -------------------------------------------- */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "addressName", column = @Column(name = "employer_address_name")),
            @AttributeOverride(name = "region1depthName", column = @Column(name = "employer_region_1depth_name")),
            @AttributeOverride(name = "region2depthName", column = @Column(name = "employer_region_2depth_name")),
            @AttributeOverride(name = "region3depthName", column = @Column(name = "employer_region_3depth_name")),
            @AttributeOverride(name = "region4depthName", column = @Column(name = "employer_region_4depth_name")),
            @AttributeOverride(name = "addressDetail", column = @Column(name = "employer_address_detail")),
            @AttributeOverride(name = "longitude", column = @Column(name = "employer_longitude")),
            @AttributeOverride(name = "latitude", column = @Column(name = "employer_latitude"))
    })
    private Address employerAddress;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public PartTimeEmploymentPermit(UserOwnerJobPosting userOwnerJobPosting, String firstName, String lastName,
                                    String major, Integer termOfCompletion, String employeePhoneNumber,
                                    String employeeEmail, EEmployeeStatus employeeStatus, String companyName,
                                    String companyRegistrationNumber, String jobType, EEmployerStatus employerStatus,
                                    Address employerAddress) {
        super(userOwnerJobPosting);
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
        this.termOfCompletion = termOfCompletion;
        this.employeePhoneNumber = employeePhoneNumber;
        this.employeeEmail = employeeEmail;
        this.employeeStatus = employeeStatus;
        this.companyName = companyName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.jobType = jobType;
        this.employerStatus = employerStatus;
        this.employerAddress = employerAddress;
    }
}