package com.inglo.giggle.document.domain;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.document.domain.type.*;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "standard_labor_contracts")
@PrimaryKeyJoinColumn(
        name = "document_id",
        foreignKey = @ForeignKey(name = "fk_standard_labor_contract_document")
)
public class StandardLaborContract extends Document {

    /* -------------------------------------------- */
    /* Information Column(Employee) --------------- */
    /* -------------------------------------------- */
    @Column(name = "employee_first_name", length = 50, nullable = false)
    private String employeeFirstName;

    @Column(name = "employee_last_name", length = 50, nullable = false)
    private String employeeLastName;

    @Column(name = "employee_phone_number", length = 20, nullable = false)
    private String employeePhoneNumber;

    @Lob
    @Column(name = "employee_signature_url", nullable = false)
    private String employeeSignatureUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_status", nullable = false)
    private EEmployeeStatus employeeStatus;

    /* -------------------------------------------- */
    /* Information Column(Employer) --------------- */
    /* -------------------------------------------- */
    @Column(name = "company_name", length = 150)
    private String companyName;

    @Column(name = "employer_name", length = 150)
    private String employerName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "description", length = 200)
    private String description;

    @ElementCollection(targetClass = EDayOfWeek.class)
    @CollectionTable(name = "contract_weekly_last_days", joinColumns = @JoinColumn(name = "standard_labor_contract_id"))
    @Column(name = "weekly_last_day")
    @Enumerated(EnumType.STRING)
    private EnumSet<EDayOfWeek> weeklyLastDays;

    @Column(name = "hourly_rate")
    private Integer hourlyRate;

    @Column(name = "bonus")
    private Integer bonus;

    @Column(name = "additional_salary")
    private Integer additionalSalary;

    @Column(name = "wage_rate")
    private Double wageRate;

    @Column(name = "payment_day")
    private Integer paymentDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private EPaymentMethod paymentMethod;

    @ElementCollection(targetClass = EInsurance.class)
    @CollectionTable(name = "contract_insurances", joinColumns = @JoinColumn(name = "contract_id"))
    @Column(name = "insurance")
    @Enumerated(EnumType.STRING)
    private EnumSet<EInsurance> insurances = EnumSet.noneOf(EInsurance.class);

    @Lob
    @Column(name = "employer_signature_url")
    private String employerSignatureUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "employer_status")
    private EEmployerStatus employerStatus;

    /* -------------------------------------------- */
    /* Embedded Column ---------------------------- */
    /* -------------------------------------------- */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "addressName", column = @Column(name = "employee_address_name")),
            @AttributeOverride(name = "region1DepthName", column = @Column(name = "employee_region_1depth_name")),
            @AttributeOverride(name = "region2DepthName", column = @Column(name = "employee_region_2depth_name")),
            @AttributeOverride(name = "region3DepthName", column = @Column(name = "employee_region_3depth_name")),
            @AttributeOverride(name = "region4DepthName", column = @Column(name = "employee_region_4depth_name")),
            @AttributeOverride(name = "addressDetail", column = @Column(name = "employee_address_detail")),
            @AttributeOverride(name = "longitude", column = @Column(name = "employee_longitude")),
            @AttributeOverride(name = "latitude", column = @Column(name = "employee_latitude"))
    })
    private Address employeeAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "addressName", column = @Column(name = "employer_address_name")),
            @AttributeOverride(name = "region1DepthName", column = @Column(name = "employer_region_1depth_name")),
            @AttributeOverride(name = "region2DepthName", column = @Column(name = "employer_region_2depth_name")),
            @AttributeOverride(name = "region3DepthName", column = @Column(name = "employer_region_3depth_name")),
            @AttributeOverride(name = "region4DepthName", column = @Column(name = "employer_region_4depth_name")),
            @AttributeOverride(name = "addressDetail", column = @Column(name = "employer_address_detail")),
            @AttributeOverride(name = "longitude", column = @Column(name = "employer_longitude")),
            @AttributeOverride(name = "latitude", column = @Column(name = "employer_latitude"))
    })
    private Address employerAddress;

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "standardLaborContract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContractWorkDayTime> contractWorkDayTimes = new ArrayList<>();

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public StandardLaborContract(UserOwnerJobPosting userOwnerJobPosting, String employeeFirstName, String employeeLastName,
                                 String employeePhoneNumber, String employeeSignatureUrl, Address employeeAddress,
                                 EEmployeeStatus employeeStatus, String companyName, String employerName,
                                 LocalDate startDate, LocalDate endDate, Address employerAddress, String description,
                                 EnumSet<EDayOfWeek> weeklyLastDays, Integer hourlyRate, Integer bonus, Integer additionalSalary,
                                 Double wageRate, Integer paymentDay, EPaymentMethod paymentMethod, EnumSet<EInsurance> insurances,
                                 String employerSignatureUrl, EEmployerStatus employerStatus) {
        super(userOwnerJobPosting);
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.employeePhoneNumber = employeePhoneNumber;
        this.employeeSignatureUrl = employeeSignatureUrl;
        this.employeeAddress = employeeAddress;
        this.employeeStatus = employeeStatus;
        this.companyName = companyName;
        this.employerName = employerName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employerAddress = employerAddress;
        this.description = description;
        this.weeklyLastDays = weeklyLastDays;
        this.hourlyRate = hourlyRate;
        this.bonus = bonus;
        this.additionalSalary = additionalSalary;
        this.wageRate = wageRate;
        this.paymentDay = paymentDay;
        this.paymentMethod = paymentMethod;
        this.insurances = insurances != null ? insurances : EnumSet.noneOf(EInsurance.class);
        this.employerSignatureUrl = employerSignatureUrl;
        this.employerStatus = employerStatus;
    }
}