package com.inglo.giggle.resume.domain;

import com.inglo.giggle.core.dto.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "additional_languages")
@SQLDelete(sql = "UPDATE additional_languages SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class AdditionalLanguage extends BaseEntity {

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
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_skills_id", nullable = false)
    private LanguageSkill languageSkill;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public AdditionalLanguage(String languageName, Integer level, LanguageSkill languageSkill) {
        this.languageName = languageName;
        this.level = level;
        this.languageSkill = languageSkill;
    }

    public void updateLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public void updateLevel(Integer level) {
        this.level = level;
    }
}