package com.inglo.giggle.resume.persistence.entity;

import com.inglo.giggle.core.dto.BaseEntity;
import jakarta.persistence.Column;
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

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "additional_languages")
@SQLDelete(sql = "UPDATE additional_languages SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class AdditionalLanguageEntity extends BaseEntity {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "language_name", length = 50, nullable = false)
    private String languageName;

    @Column(name = "level", nullable = false)
    private Integer level;

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @Column(name = "language_skills_id", nullable = false)
    private UUID languageSkillsId;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public AdditionalLanguageEntity(Long id, String languageName, Integer level, UUID languageSkillsId) {
        this.id = id;
        this.languageName = languageName;
        this.level = level;
        this.languageSkillsId = languageSkillsId;
    }
}