package com.inglo.giggle.school.domain;

import com.inglo.giggle.address.domain.Address;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "schools")
public class School {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "school_name", length = 100, nullable = false)
    private String schoolName;

    @Column(name = "school_phone_number", length = 20, nullable = false)
    private String schoolPhoneNumber;

    @Column(name = "institute_name", length = 100, nullable = false)
    private String instituteName;

    @Column(name = "coordinator_name", length = 50, nullable = false)
    private String coordinatorName;

    @Column(name = "coordinator_phone_number", length = 20, nullable = false)
    private String coordinatorPhoneNumber;

    @Column(name = "is_metropolitan", nullable = false)
    private Boolean isMetropolitan;

    /* -------------------------------------------- */
    /* Embedded Column ---------------------------- */
    /* -------------------------------------------- */
    @Embedded
    private Address address;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public School(String schoolName, String schoolPhoneNumber, String instituteName,
                  String coordinatorName, String coordinatorPhoneNumber, Address address, Boolean isMetropolitan) {
        this.schoolName = schoolName;
        this.schoolPhoneNumber = schoolPhoneNumber;
        this.instituteName = instituteName;
        this.coordinatorName = coordinatorName;
        this.coordinatorPhoneNumber = coordinatorPhoneNumber;
        this.address = address;
        this.isMetropolitan = isMetropolitan;
    }

}