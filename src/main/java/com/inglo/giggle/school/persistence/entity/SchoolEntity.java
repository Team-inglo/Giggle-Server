package com.inglo.giggle.school.persistence.entity;

import com.inglo.giggle.address.persistence.entity.AddressEntity;
import com.inglo.giggle.core.dto.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "schools")
@SQLDelete(sql = "UPDATE schools SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class SchoolEntity extends BaseEntity {

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
    private AddressEntity addressEntity;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public SchoolEntity(String schoolName, String schoolPhoneNumber, String instituteName,
                        String coordinatorName, String coordinatorPhoneNumber, AddressEntity addressEntity, Boolean isMetropolitan) {
        this.schoolName = schoolName;
        this.schoolPhoneNumber = schoolPhoneNumber;
        this.instituteName = instituteName;
        this.coordinatorName = coordinatorName;
        this.coordinatorPhoneNumber = coordinatorPhoneNumber;
        this.addressEntity = addressEntity;
        this.isMetropolitan = isMetropolitan;
    }

    public void updateSelf(String schoolName, String schoolPhoneNumber, String instituteName,
                           String coordinatorName, String coordinatorPhoneNumber, AddressEntity addressEntity, Boolean isMetropolitan) {
        this.schoolName = schoolName;
        this.schoolPhoneNumber = schoolPhoneNumber;
        this.instituteName = instituteName;
        this.coordinatorName = coordinatorName;
        this.coordinatorPhoneNumber = coordinatorPhoneNumber;
        this.addressEntity = addressEntity;
        this.isMetropolitan = isMetropolitan;
    }

}