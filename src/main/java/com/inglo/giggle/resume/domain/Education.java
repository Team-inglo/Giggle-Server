package com.inglo.giggle.resume.domain;

import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.school.domain.School;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "educations")
public class Education {

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
    private Resume resume;

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------- */
    /* -------------------------------------------- */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Education(EEducationLevel educationLevel, String major, Double gpa, LocalDate enrollmentDate,
                     LocalDate graduationDate, Integer grade, School school) {
        this.educationLevel = educationLevel;
        this.major = major;
        this.gpa = gpa;
        this.enrollmentDate = enrollmentDate;
        this.graduationDate = graduationDate;
        this.grade = grade;
        this.school = school;
    }

}