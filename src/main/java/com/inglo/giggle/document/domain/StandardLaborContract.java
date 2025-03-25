package com.inglo.giggle.document.domain;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.type.EDayOfWeek;
import com.inglo.giggle.document.domain.type.EEmployeeStatus;
import com.inglo.giggle.document.domain.type.EEmployerStatus;
import com.inglo.giggle.document.domain.type.EInsurance;
import com.inglo.giggle.document.domain.type.EPaymentMethod;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "standard_labor_contracts")
@DiscriminatorValue("STANDARD_LABOR_CONTRACT")
@PrimaryKeyJoinColumn(
        name = "document_id",
        foreignKey = @ForeignKey(name = "fk_standard_labor_contract_document")
)
@OnDelete(action = OnDeleteAction.CASCADE)
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
    @Column(name = "employee_signature_base64", nullable = false, columnDefinition = "TEXT")
    private String employeeSignatureBase64;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_status", nullable = false)
    private EEmployeeStatus employeeStatus;

    /* -------------------------------------------- */
    /* Information Column(Employer) --------------- */
    /* -------------------------------------------- */
    @Column(name = "company_name", length = 150)
    private String companyName;

    @Column(name = "employer_phone_number", length = 20)
    private String employerPhoneNumber;

    @Column(name = "company_registration_number", length = 12)
    private String companyRegistrationNumber;

    @Column(name = "employer_name", length = 150)
    private String employerName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "description", length = 200)
    private String description;

    @ElementCollection(targetClass = EDayOfWeek.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "contract_weekly_last_days", joinColumns = @JoinColumn(name = "standard_labor_contract_id"))
    @Column(name = "weekly_last_day")
    @Enumerated(EnumType.STRING)
    private Set<EDayOfWeek> weeklyRestDays = new HashSet<>();

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

    @ElementCollection(targetClass = EInsurance.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "contract_insurances", joinColumns = @JoinColumn(name = "contract_id"))
    @Column(name = "insurance")
    @Enumerated(EnumType.STRING)
    private Set<EInsurance> insurances = new HashSet<>();

    @Lob
    @Column(name = "employer_signature_base64", columnDefinition = "TEXT")
    private String employerSignatureBase64;

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
                                 String employeePhoneNumber, String employeeSignatureBase64, Address employeeAddress,
                                 EEmployeeStatus employeeStatus, String companyName, String companyRegistrationNumber,
                                 String employerName, String employerPhoneNumber, LocalDate startDate, LocalDate endDate, Address employerAddress,
                                 String description, EnumSet<EDayOfWeek> weeklyRestDays, Integer hourlyRate, Integer bonus, Integer additionalSalary,
                                 Double wageRate, Integer paymentDay, EPaymentMethod paymentMethod, EnumSet<EInsurance> insurances,
                                 String employerSignatureBase64, EEmployerStatus employerStatus) {
        super(userOwnerJobPosting);
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.employeePhoneNumber = employeePhoneNumber;
        this.employeeSignatureBase64 = employeeSignatureBase64;
        this.employeeAddress = employeeAddress;
        this.employeeStatus = employeeStatus;
        this.companyName = companyName;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.employerName = employerName;
        this.employerPhoneNumber = employerPhoneNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employerAddress = employerAddress;
        this.description = description;
        this.weeklyRestDays = weeklyRestDays;
        this.hourlyRate = hourlyRate;
        this.bonus = bonus;
        this.additionalSalary = additionalSalary;
        this.wageRate = wageRate;
        this.paymentDay = paymentDay;
        this.paymentMethod = paymentMethod;
        this.insurances = insurances != null ? insurances : EnumSet.noneOf(EInsurance.class);
        this.employerSignatureBase64 = employerSignatureBase64;
        this.employerStatus = employerStatus;
    }

    public String getRestDays() {
        StringBuilder restDays = new StringBuilder();

        List<EDayOfWeek> sortedRestDays = new ArrayList<>(this.weeklyRestDays);
        sortedRestDays.sort(Comparator.comparing(EDayOfWeek::getOrder));

        for (EDayOfWeek day : sortedRestDays) {
            restDays.append(day.getKrName()).append(", ");
        }
        restDays.delete(restDays.length() - 2, restDays.length());
        return restDays.toString();
    }

    public String getEmployeeFullName() {
        return this.employeeFirstName + " " + this.employeeLastName;
    }

    public String getEmployerFullAddress() {
        return this.employerAddress.getFullAddress();
    }

    public void updateEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public void updateEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public void updateEmployeePhoneNumber(String employeePhoneNumber) {
        this.employeePhoneNumber = employeePhoneNumber;
    }

    public void updateEmployeeSignatureBase64(String employeeSignatureBase64) {
        this.employeeSignatureBase64 = employeeSignatureBase64;
    }

    public void updateCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void updateCompanyRegistrationNumber(String companyRegistrationNumber) {
        this.companyRegistrationNumber = companyRegistrationNumber;
    }

    public void updateEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public void updateEmployerPhoneNumber(String employerPhoneNumber) {
        this.employerPhoneNumber = employerPhoneNumber;
    }

    public void updateEmployerSignatureBase64(String employerSignatureBase64) {
        this.employerSignatureBase64 = employerSignatureBase64;
    }

    public void updateStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void updateEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateHourlyRate(Integer hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void updateBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public void updateAdditionalSalary(Integer additionalSalary) {
        this.additionalSalary = additionalSalary;
    }

    public void updateWageRate(Double wageRate) {
        this.wageRate = wageRate;
    }

    public void updatePaymentDay(Integer paymentDay) {
        this.paymentDay = paymentDay;
    }

    public void updatePaymentMethod(EPaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void updateInsurances(Set<EInsurance> insurances) {
        this.insurances = insurances;
    }

    public void updateWeeklyRestDays(Set<EDayOfWeek> weeklyRestDays) {
        this.weeklyRestDays = weeklyRestDays;
    }

    public void updateEmployerAddress(Address employerAddress) {
        this.employerAddress = employerAddress;
    }

    public void updateEmployeeAddress(Address employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public void updateContractWorkDayTimes(List<ContractWorkDayTime> contractWorkDayTimes) {
        this.contractWorkDayTimes = contractWorkDayTimes;
    }

    public void updateEmployeeStatus(EEmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public void updateEmployerStatus(EEmployerStatus employerStatus) {
        this.employerStatus = employerStatus;
    }
}