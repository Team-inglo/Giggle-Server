package com.inglo.giggle.document.persistence.entity;

import com.inglo.giggle.core.dto.BaseEntity;
import com.inglo.giggle.posting.persistence.entity.UserOwnerJobPostingEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "documents")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@SQLDelete(sql = "UPDATE documents SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public abstract class DocumentEntity extends BaseEntity {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */

    @Column(name = "hwp_url", length = 320)
    private String hwpUrl;

    @Column(name = "word_url", length = 320)
    private String wordUrl;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_owner_job_postings_id", nullable = false)
    private UserOwnerJobPostingEntity userOwnerJobPostingEntity;

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------- */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "documentEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RejectEntity> rejectEntity;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    protected DocumentEntity(UserOwnerJobPostingEntity userOwnerJobPostingEntity) {
        this.userOwnerJobPostingEntity = userOwnerJobPostingEntity;
    }

    public void updateWordUrl(String wordUrl) {
        this.wordUrl = wordUrl;
    }

    public void updateHwpUrl(String hwpUrl) {
        this.hwpUrl = hwpUrl;
    }

}