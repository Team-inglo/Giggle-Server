package com.inglo.giggle.resume.persistence.entity;

import com.inglo.giggle.core.dto.BaseEntity;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.school.persistence.entity.SchoolEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "educations")
@SQLDelete(sql = "UPDATE educations SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class EducationEntity extends BaseEntity {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Enumerated(EnumType.STRING)
    @Column(name = "education_level", nullable = false) // BACHELOR(4년제), ASSOCIATE(2년제), HIGHSCHOOL(고졸)
    private EEducationLevel educationLevel;

    @Column(name = "major", length = 30, nullable = false)
    private String major;

    @Column(name = "gpa", nullable = false)
    private Double gpa;

    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;

    @Column(name = "graduation_date", nullable = false)
    private LocalDate graduationDate;

    @Column(name = "grade", nullable = false)
    private Integer grade;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private ResumeEntity resumeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private SchoolEntity schoolEntity;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public EducationEntity(EEducationLevel educationLevel, String major, Double gpa, LocalDate enrollmentDate,
                           LocalDate graduationDate, Integer grade, SchoolEntity schoolEntity, ResumeEntity resumeEntity) {
        this.educationLevel = educationLevel;
        this.major = major;
        this.gpa = gpa;
        this.enrollmentDate = enrollmentDate;
        this.graduationDate = graduationDate;
        this.grade = grade;
        this.schoolEntity = schoolEntity;
        this.resumeEntity = resumeEntity;
    }

    public void fetchSchoolEntity(SchoolEntity schoolEntity) {
        this.schoolEntity = schoolEntity;
    }

    public void fetchResumeEntity(ResumeEntity resumeEntity) {
        this.resumeEntity = resumeEntity;
    }

}