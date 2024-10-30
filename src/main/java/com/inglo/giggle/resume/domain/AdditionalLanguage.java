package com.inglo.giggle.resume.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "additional_languages")
public class AdditionalLanguage {

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

}