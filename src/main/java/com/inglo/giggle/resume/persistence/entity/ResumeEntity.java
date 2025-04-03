package com.inglo.giggle.resume.persistence.entity;

import com.inglo.giggle.account.persistence.entity.UserEntity;
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
@Table(name = "resumes")
@SQLDelete(sql = "UPDATE resumes SET deleted_at = CURRENT_TIMESTAMP WHERE account_id = ?")
@SQLRestriction("deleted_at IS NULL")
public class ResumeEntity extends BaseEntity {

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
    @OneToMany(mappedBy = "resumeEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkExperienceEntity> workExperienceEntities = new ArrayList<>();

    @OneToMany(mappedBy = "resumeEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EducationEntity> educationEntities = new ArrayList<>();

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------- */
    /* -------------------------------------------- */
    @MapsId
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private UserEntity userEntity;

    @OneToOne(mappedBy = "resumeEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private LanguageSkillEntity languageSkillEntity;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public ResumeEntity(UserEntity userEntity, String introduction, List<WorkExperienceEntity> workExperienceEntities, List<EducationEntity> educationEntities, LanguageSkillEntity languageSkillEntity) {
        this.userEntity = userEntity;
        this.introduction = introduction;
        this.workExperienceEntities = workExperienceEntities;
        this.educationEntities = educationEntities;
        this.languageSkillEntity = languageSkillEntity;
    }

    public void fetchUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

}