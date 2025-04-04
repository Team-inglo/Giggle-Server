package com.inglo.giggle.resume.persistence.entity;

import com.inglo.giggle.core.dto.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

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
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public LanguageSkillEntity(UUID resumeId, Integer topikLevel, Integer socialIntegrationLevel, Integer sejongInstituteLevel) {
        this.resumeId = resumeId;
        this.topikLevel = topikLevel;
        this.socialIntegrationLevel = socialIntegrationLevel;
        this.sejongInstituteLevel = sejongInstituteLevel;
    }
}