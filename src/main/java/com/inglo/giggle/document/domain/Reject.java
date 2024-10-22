package com.inglo.giggle.document.domain;

import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "rejects")
@PrimaryKeyJoinColumn(
        name = "document_id",
        foreignKey = @ForeignKey(name = "fk_reject_document")
)
public class Reject extends Document {

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "reason", length = 100, nullable = false)
    private String reason;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Reject(UserOwnerJobPosting userOwnerJobPosting, String reason) {
        super(userOwnerJobPosting);
        this.reason = reason;
    }
}