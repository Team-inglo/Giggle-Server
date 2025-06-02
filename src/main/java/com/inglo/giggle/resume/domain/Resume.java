package com.inglo.giggle.resume.domain;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.core.dto.BaseEntity;
import com.inglo.giggle.posting.domain.type.EJobCategory;
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
public class Resume extends BaseEntity {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @Column(name = "account_id")
    private UUID accountId;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "introduction", length = 200)
    private String introduction;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkExperience> workExperiences = new ArrayList<>();

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educations = new ArrayList<>();

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookMarkResume> bookMarks = new ArrayList<>();

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------- */
    /* -------------------------------------------- */
    @MapsId
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private User user;

    @OneToOne(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private LanguageSkill languageSkill;

    @OneToOne(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private WorkPreference workPreference;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Resume(User user, String title, String introduction) {
        this.user = user;
        this.accountId = user.getId();
        this.title = title;
        this.introduction = introduction;
        this.isPublic = true;
    }

    public String getWorkPreferenceJobCategoriesName() {
        if (this.workPreference == null || this.workPreference.getJobCategories().isEmpty()) {
            return " - ";
        }
        var jobCategories = this.workPreference.getJobCategories();
        // 첫 번째 직종만 가져오고 나머지 개수 카운트
        EJobCategory first = jobCategories.iterator().next();
        int count = jobCategories.size();
        if (count == 1) {
            return first.getKrName();
        } else {
            return first.getKrName() + " 외 " + (count - 1) + "개";
        }
    }

    public void updateIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void updateResume(String title, String introduction) {
        this.title = title;
        this.introduction = introduction;
    }

    public void updatePublicStatus(boolean isPublic) {
        this.isPublic = isPublic;
    }
}