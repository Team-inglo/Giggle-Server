package com.inglo.giggle.document.domain;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.school.domain.School;
import com.inglo.giggle.core.domain.type.EGender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "integrated_applications")
@PrimaryKeyJoinColumn(
        name = "document_id",
        foreignKey = @ForeignKey(name = "fk_integrated_application_document")
)
public class IntegratedApplication extends Document {

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private EGender gender;

    @Column(name = "nationality", length = 56)
    private String nationality;

    @Column(name = "tele_phone_number", length = 20)
    private String telePhoneNumber;

    @Column(name = "cell_phone_number", length = 20, nullable = false)
    private String cellPhoneNumber;

    @Column(name = "is_accredited", nullable = false)
    private Boolean isAccredited;

    @Column(name = "new_work_place_name", length = 50, nullable = false)
    private String newWorkPlaceName;

    @Column(name = "new_work_place_registration_number", length = 12)
    private String newWorkPlaceRegistrationNumber;

    @Column(name = "new_work_place_phone_number", length = 20, nullable = false)
    private String newWorkPlacePhoneNumber;

    @Column(name = "annual_income_amount", nullable = false)
    private Integer annualIncomeAmount;

    @Column(name = "occupation", nullable = false)
    private Boolean occupation;

    @Column(name = "intended_period_of_reentry", nullable = false)
    private LocalDate intendedPeriodOfReentry;

    @Column(name = "email", length = 320, nullable = false)
    private String email;

    @Lob
    @Column(name = "employee_signature_base64")
    private String employeeSignatureBase64;

    /* -------------------------------------------- */
    /* Embedded Column ---------------------------- */
    /* -------------------------------------------- */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "addressName", column = @Column(name = "employee_address_name")),
            @AttributeOverride(name = "region1depthName", column = @Column(name = "employee_region_1depth_name")),
            @AttributeOverride(name = "region2depthName", column = @Column(name = "employee_region_2depth_name")),
            @AttributeOverride(name = "region3depthName", column = @Column(name = "employee_region_3depth_name")),
            @AttributeOverride(name = "region4depthName", column = @Column(name = "employee_region_4depth_name")),
            @AttributeOverride(name = "addressDetail", column = @Column(name = "employee_address_detail")),
            @AttributeOverride(name = "longitude", column = @Column(name = "employee_longitude")),
            @AttributeOverride(name = "latitude", column = @Column(name = "employee_latitude"))
    })
    private Address employeeAddress;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public IntegratedApplication(UserOwnerJobPosting userOwnerJobPosting, String firstName, String lastName,
                                 LocalDate birth, EGender gender, String nationality, String telePhoneNumber,
                                 String cellPhoneNumber, Boolean isAccredited, String newWorkPlaceName,
                                 String newWorkPlaceRegistrationNumber, String newWorkPlacePhoneNumber,
                                 Integer annualIncomeAmount, Boolean occupation, LocalDate intendedPeriodOfReentry,
                                 String email, String employeeSignatureBase64, Address employeeAddress, School school) {
        super(userOwnerJobPosting);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth = birth;
        this.gender = gender;
        this.nationality = nationality;
        this.telePhoneNumber = telePhoneNumber;
        this.cellPhoneNumber = cellPhoneNumber;
        this.isAccredited = isAccredited;
        this.newWorkPlaceName = newWorkPlaceName;
        this.newWorkPlaceRegistrationNumber = newWorkPlaceRegistrationNumber;
        this.newWorkPlacePhoneNumber = newWorkPlacePhoneNumber;
        this.annualIncomeAmount = annualIncomeAmount;
        this.occupation = occupation;
        this.intendedPeriodOfReentry = intendedPeriodOfReentry;
        this.email = email;
        this.employeeSignatureBase64 = employeeSignatureBase64;
        this.employeeAddress = employeeAddress;
        this.school = school;
    }
}