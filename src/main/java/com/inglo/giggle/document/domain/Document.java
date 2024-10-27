package com.inglo.giggle.document.domain;

import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "documents")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
public abstract class Document {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "pdf_url", length = 320 , nullable = false)
    private String pdfUrl;

    @Column(name = "hwp_url", length = 320 , nullable = false)
    private String hwpUrl;

    @Column(name = "word_url", length = 320 , nullable = false)
    private String wordUrl;

    /* -------------------------------------------- */
    /* Timestamp Column --------------------------- */
    /* -------------------------------------------- */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_owner_job_postings_id", nullable = false)
    private UserOwnerJobPosting userOwnerJobPosting;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    protected Document(UserOwnerJobPosting userOwnerJobPosting) {
        this.createdAt = LocalDate.now();
        this.userOwnerJobPosting = userOwnerJobPosting;
    }
}