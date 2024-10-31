package com.inglo.giggle.resume.domain;

import com.inglo.giggle.account.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "resumes")
public class Resume {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @Column(name = "account_id")
    private UUID accountId;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "introduction", length = 200)
    private String introduction;

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkExperience> workExperiences = new ArrayList<>();

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educations = new ArrayList<>();

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------- */
    /* -------------------------------------------- */
    @MapsId
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private User user;

    @OneToOne(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private LanguageSkill languageSkill;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Resume(User user, String introduction) {
        this.user = user;
        this.introduction = introduction;
    }

    public void updateIntroduction(String introduction) {
        this.introduction = introduction;
    }

}