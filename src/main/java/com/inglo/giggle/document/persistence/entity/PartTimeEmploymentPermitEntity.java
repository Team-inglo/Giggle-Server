package com.inglo.giggle.document.persistence.entity;

import com.inglo.giggle.address.persistence.entity.AddressEntity;
import com.inglo.giggle.document.domain.type.EEmployeeStatus;
import com.inglo.giggle.document.domain.type.EEmployerStatus;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Lob;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "part_time_employment_permits")
@DiscriminatorValue("PART_TIME_EMPLOYMENT_PERMIT")
@PrimaryKeyJoinColumn(
        name = "document_id",
        foreignKey = @ForeignKey(name = "fk_part_time_employment_permit_document")
)
@OnDelete(action = OnDeleteAction.CASCADE)
public class PartTimeEmploymentPermitEntity extends DocumentEntity {

    /* -------------------------------------------- */
    /* Information Column(Employee) --------------- */
    /* -------------------------------------------- */
    @Column(name = "employee_first_name", length = 50, nullable = false)
    private String employeeFirstName;

    @Column(name = "employee_last_name", length = 100, nullable = false)
    private String employeeLastName;

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

    @Column(name = "employer_name", length = 100)
    private String employerName;

    @Column(name = "employer_phone_number", length = 20)
    private String employerPhoneNumber;

    @Lob
    @Column(name = "employer_signature_base64", columnDefinition = "TEXT")
    private String employerSignatureBase64;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_period")
    private EWorkPeriod workPeriod;

    @Column(name = "hourly_rate")
    private Integer hourlyRate;

    @Column(name = "work_days_weekdays", length = 150)
    private String workDaysWeekDays;

    @Column(name = "work_days_weekends", length = 150)
    private String workDaysWeekends;

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
    private AddressEntity employerAddressEntity;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public PartTimeEmploymentPermitEntity(Long id, String hwpUrl, String wordUrl, Long userOwnerJobPostingId, String employeeFirstName, String employeeLastName,
                                          String major, Integer termOfCompletion, String employeePhoneNumber,
                                          String employeeEmail, EEmployeeStatus employeeStatus, String companyName,
                                          String companyRegistrationNumber, String jobType, String employerName,
                                          String employerPhoneNumber, String employerSignatureBase64, EWorkPeriod workPeriod,
                                          Integer hourlyRate, String workDaysWeekDays, String workDaysWeekends,
                                          EEmployerStatus employerStatus, AddressEntity employerAddressEntity) {
        super(id, hwpUrl, wordUrl, userOwnerJobPostingId);
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.major = major;
        this.termOfCompletion = termOfCompletion;
        this.employeePhoneNumber = employeePhoneNumber;
        this.employeeEmail = employeeEmail;
        this.employeeStatus = employeeStatus;
        this.companyName = companyName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.jobType = jobType;
        this.employerName = employerName;
        this.employerPhoneNumber = employerPhoneNumber;
        this.employerSignatureBase64 = employerSignatureBase64;
        this.workPeriod = workPeriod;
        this.hourlyRate = hourlyRate;
        this.workDaysWeekDays = workDaysWeekDays;
        this.workDaysWeekends = workDaysWeekends;
        this.employerStatus = employerStatus;
        this.employerAddressEntity = employerAddressEntity;
    }
}