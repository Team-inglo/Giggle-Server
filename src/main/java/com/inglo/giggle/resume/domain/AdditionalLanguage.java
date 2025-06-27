package com.inglo.giggle.resume.domain;

import com.inglo.giggle.core.dto.BaseEntity;
import com.inglo.giggle.resume.domain.type.EAdditionalLanguageLevelType;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private EAdditionalLanguageLevelType level;

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
    public AdditionalLanguage(String languageName, EAdditionalLanguageLevelType level, LanguageSkill languageSkill) {
        this.languageName = languageName;
        this.level = level;
        this.languageSkill = languageSkill;
    }

    public void updateLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public void updateLevel(EAdditionalLanguageLevelType level) {
        this.level = level;
    }
}