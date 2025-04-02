package com.inglo.giggle.document.persistence.entity;

import com.inglo.giggle.address.persistence.entity.AddressEntity;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.document.domain.type.EEmployeeStatus;
import com.inglo.giggle.posting.persistence.entity.UserOwnerJobPostingEntity;
import com.inglo.giggle.school.persistence.entity.SchoolEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "integrated_applications")
@DiscriminatorValue("INTEGRATED_APPLICATION")
@PrimaryKeyJoinColumn(
        name = "document_id",
        foreignKey = @ForeignKey(name = "fk_integrated_application_document")
)
@OnDelete(action = OnDeleteAction.CASCADE)
public class IntegratedApplicationEntity extends DocumentEntity {

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

    @Column(name = "occupation", length = 30, nullable = false)
    private String occupation;

    @Column(name = "email", length = 320, nullable = false)
    private String email;

    @Lob
    @Column(name = "employee_signature_base64", nullable = false, columnDefinition = "TEXT")
    private String employeeSignatureBase64;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_status", nullable = false)
    private EEmployeeStatus employeeStatus;

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
    private AddressEntity employeeAddressEntity;

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------- */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private SchoolEntity schoolEntity;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public IntegratedApplicationEntity(UserOwnerJobPostingEntity userOwnerJobPostingEntity, String firstName, String lastName,
                                       LocalDate birth, EGender gender, String nationality, String telePhoneNumber,
                                       String cellPhoneNumber, Boolean isAccredited, String newWorkPlaceName,
                                       String newWorkPlaceRegistrationNumber, String newWorkPlacePhoneNumber,
                                       Integer annualIncomeAmount, String occupation, String email, String employeeSignatureBase64,
                                       EEmployeeStatus employeeStatus, AddressEntity employeeAddressEntity, SchoolEntity schoolEntity) {
        super(userOwnerJobPostingEntity);
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
        this.email = email;
        this.employeeSignatureBase64 = employeeSignatureBase64;
        this.employeeStatus = employeeStatus;
        this.employeeAddressEntity = employeeAddressEntity;
        this.schoolEntity = schoolEntity;
    }

    public void fetchSchool(SchoolEntity schoolEntity) {
        this.schoolEntity = schoolEntity;
    }

}