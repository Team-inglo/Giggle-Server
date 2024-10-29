package com.inglo.giggle.document.domain;

import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.resume.domain.Resume;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "rejects")
public class Reject {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @Column(name = "document_id")
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "reason", length = 100, nullable = false)
    private String reason;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Reject(Document document, String reason) {
        this.document = document;
        this.reason = reason;
    }
}