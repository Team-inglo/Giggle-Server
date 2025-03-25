package com.inglo.giggle.resume.domain;

import com.inglo.giggle.core.dto.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "language_skills")
@SQLDelete(sql = "UPDATE language_skills SET deleted_at = CURRENT_TIMESTAMP WHERE resume_id = ?")
@SQLRestriction("deleted_at IS NULL")
public class LanguageSkill extends BaseEntity {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @Column(name = "resume_id")
    private UUID resumeId;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "topik_level")
    private Integer topikLevel;

    @Column(name = "social_integration_level")
    private Integer socialIntegrationLevel;

    @Column(name = "sejong_institute_level")
    private Integer sejongInstituteLevel;

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "languageSkill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdditionalLanguage> additionalLanguages = new ArrayList<>();

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------- */
    /* -------------------------------------------- */
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public LanguageSkill(Resume resume) {
        this.resume = resume;
        this.topikLevel = 0;
        this.socialIntegrationLevel = 0;
        this.sejongInstituteLevel = 0;
    }

    public void updateTopikLevel(Integer topikLevel) {
        this.topikLevel = topikLevel;
    }

    public void updateSocialIntegrationLevel(Integer socialIntegrationLevel) {
        this.socialIntegrationLevel = socialIntegrationLevel;
    }

    public void updateSejongInstituteLevel(Integer sejongInstituteLevel) {
        this.sejongInstituteLevel = sejongInstituteLevel;
    }
}