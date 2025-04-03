package com.inglo.giggle.resume.persistence.entity;

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
public class LanguageSkillEntity extends BaseEntity {

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
    @OneToMany(mappedBy = "languageSkillEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdditionalLanguageEntity> additionalLanguageEntities = new ArrayList<>();

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------- */
    /* -------------------------------------------- */
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private ResumeEntity resumeEntity;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public LanguageSkillEntity(ResumeEntity resumeEntity, Integer topikLevel, Integer socialIntegrationLevel, Integer sejongInstituteLevel, List<AdditionalLanguageEntity> additionalLanguageEntities) {
        this.resumeEntity = resumeEntity;
        this.topikLevel = topikLevel;
        this.socialIntegrationLevel = socialIntegrationLevel;
        this.sejongInstituteLevel = sejongInstituteLevel;
        this.additionalLanguageEntities = additionalLanguageEntities;
    }

    public void fetchResumeEntity(ResumeEntity resumeEntity) {
        this.resumeEntity = resumeEntity;
    }
}