package com.inglo.giggle.career.domain;

import com.inglo.giggle.core.dto.BaseEntity;
import com.inglo.giggle.career.domain.type.ECareerCategory;
import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.core.type.EVisa;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "careers")
@SQLDelete(sql = "UPDATE careers SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Career extends BaseEntity {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "host", length = 50)
    private String host;

    @Column(name = "organizer", length = 50)
    private String organizer;

    @Column(name = "address", length = 10)
    private String address;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "reward")
    private Integer reward;

    @ElementCollection(targetClass = EVisa.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "career_visas", joinColumns = @JoinColumn(name = "career_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "visa")
    private Set<EVisa> visa = new HashSet<>();

    @Column(name = "recruitment_period")
    private Integer recruitmentNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "education_level")
    private EEducationLevel educationLevel;

    @Column(name = "preferred_condition", length = 50)
    private String preferredConditions;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ECareerCategory category;

    @Lob
    @Column(name = "details")
    private String details;

    @Column(name = "application_url")
    private String applicationUrl;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "career", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CareerImage> careerImages = new ArrayList<>();

    @OneToMany(mappedBy = "career", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookMarkCareer> bookMarkCareers = new ArrayList<>();

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Career(
            String title,
            String host,
            String organizer,
            String address,
            LocalDate startDate,
            LocalDate endDate,
            Integer reward,
            Set<EVisa> visa,
            Integer recruitmentNumber,
            EEducationLevel educationLevel,
            String preferredConditions,
            ECareerCategory category,
            String details,
            String applicationUrl
    ) {
        this.title = title;
        this.host = host;
        this.organizer = organizer;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reward = reward;
        this.visa = visa;
        this.recruitmentNumber = recruitmentNumber;
        this.educationLevel = educationLevel;
        this.preferredConditions = preferredConditions;
        this.category = category;
        this.details = details;
        this.applicationUrl = applicationUrl;
    }

    public void updateSelf(
            String title,
            String host,
            String organizer,
            String address,
            LocalDate startDate,
            LocalDate endDate,
            Integer reward,
            Set<EVisa> visa,
            Integer recruitmentNumber,
            EEducationLevel educationLevel,
            String preferredConditions,
            ECareerCategory category,
            String details,
            String applicationUrl
    ) {
        this.title = title;
        this.host = host;
        this.organizer = organizer;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reward = reward;
        this.visa = visa;
        this.recruitmentNumber = recruitmentNumber;
        this.educationLevel = educationLevel;
        this.preferredConditions = preferredConditions;
        this.category = category;
        this.details = details;
        this.applicationUrl = applicationUrl;
    }
}
