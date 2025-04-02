package com.inglo.giggle.posting.domain;

import com.inglo.giggle.address.domain.Address;
import com.inglo.giggle.core.dto.BaseDomain;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EGender;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import com.inglo.giggle.posting.domain.type.EWorkPeriod;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class UserOwnerJobPosting extends BaseDomain {
    private Long id;
    private EApplicationStep step;
    private LocalDate lastStepUpdated;
    private Boolean result;
    private String feedback;
    private String memo;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    private UUID userId;
    private UUID ownerId;
    private Long jobPostingId;

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    private List<Document> documents;
    private List<Notification> notifications;

    /* -------------------------------------------- */
    /* Nested class ------------------------------- */
    /* -------------------------------------------- */
    private UserInfo userInfo;
    private OwnerInfo ownerInfo;
    private JobPostingInfo jobPostingInfo;

    @Builder
    public UserOwnerJobPosting(Long id, EApplicationStep step, LocalDate lastStepUpdated, Boolean result,
                               String feedback, String memo,
                               LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt,
                               UUID userId, UUID ownerId, Long jobPostingId,
                               List<Document> documents, List<Notification> notifications,
                               UserInfo userInfo, OwnerInfo ownerInfo, JobPostingInfo jobPostingInfo
    ) {
        this.id = id;
        this.step = step;
        this.lastStepUpdated = lastStepUpdated;
        this.result = result;
        this.feedback = feedback;
        this.memo = memo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.userId = userId;
        this.ownerId = ownerId;
        this.jobPostingId = jobPostingId;
        this.documents = documents;
        this.notifications = notifications;
        this.userInfo = userInfo;
        this.ownerInfo = ownerInfo;
        this.jobPostingInfo = jobPostingInfo;
    }

    @Getter
    public static class UserInfo {
        private UUID id;
        private String profileImgUrl;
        private String serialId;
        private String firstName;
        private String lastName;
        private String name;
        private String phoneNumber;
        private String nationality;
        private EGender gender;
        private EVisa visa;
        private LocalDate birth;
        private String email;
        private Address address;
        private Boolean notificationAllowed;

        @Builder
        public UserInfo(
                UUID id,
                String profileImgUrl,
                String serialId,
                String firstName,
                String lastName,
                String name,
                String phoneNumber,
                String nationality,
                EGender gender,
                EVisa visa,
                LocalDate birth,
                Address address,
                String email,
                Boolean notificationAllowed
        ) {
            this.id = id;
            this.profileImgUrl = profileImgUrl;
            this.serialId = serialId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.nationality = nationality;
            this.gender = gender;
            this.visa = visa;
            this.birth = birth;
            this.address = address;
            this.email = email;
            this.notificationAllowed = notificationAllowed;
        }
    }

    @Getter
    public static class OwnerInfo {
        private UUID id;
        private String profileImgUrl;
        private String companyName;
        private Boolean notificationAllowed;

        @Builder
        public OwnerInfo(UUID id, String profileImgUrl, String companyName, Boolean notificationAllowed) {
            this.id = id;
            this.profileImgUrl = profileImgUrl;
            this.companyName = companyName;
            this.notificationAllowed = notificationAllowed;
        }
    }

    @Getter
    public static class JobPostingInfo {
        private Long id;
        private String title;
        private String addressName;
        private Integer hourlyRate;
        private EWorkPeriod workPeriod;
        private List<PostingWorkDayTime> workDayTimes;
        private String recruiterName;
        private String recruiterPhoneNumber;

        @Builder
        public JobPostingInfo(Long id, String title, String addressName, Integer hourlyRate, EWorkPeriod workPeriod, List<PostingWorkDayTime> workDayTimes,
                                String recruiterName, String recruiterPhoneNumber
            ) {
                this.id = id;
                this.title = title;
                this.addressName = addressName;
                this.hourlyRate = hourlyRate;
                this.workPeriod = workPeriod;
                this.workDayTimes = workDayTimes;
                this.recruiterName = recruiterName;
                this.recruiterPhoneNumber = recruiterPhoneNumber;
            }
    }

    public void checkUserUserOwnerJobPostingValidation(UUID accountId) {
        if (!this.getUserInfo().getId().equals(accountId)) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    public void checkOwnerUserOwnerJobPostingValidation(UUID accountId) {
        if (!this.getOwnerInfo().getId().equals(accountId)) {
            throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        }
    }

    public void updateStepFromResumeUnderReview(boolean isAccepted) {
        if (isAccepted) {
            this.step = EApplicationStep.WAITING_FOR_INTERVIEW;
            this.lastStepUpdated = LocalDate.now();
        } else {
            this.step = EApplicationStep.RESUME_REJECTED;
            this.result = false;
            this.lastStepUpdated = LocalDate.now();
        }
    }

    public void updateStepFromStepWaitingForInterview() {
        this.step = EApplicationStep.FILLING_OUT_DOCUMENTS;
        this.lastStepUpdated = LocalDate.now();
    }

    public void updateStepFromStepFillingOutDocument() {
        this.step = EApplicationStep.DOCUMENT_UNDER_REVIEW;
        this.lastStepUpdated = LocalDate.now();
    }

    public void updateStepFromStepApplicationInProgress() {
        this.step = EApplicationStep.REGISTERING_RESULTS;
        this.lastStepUpdated = LocalDate.now();
    }

    public void updateStepFromStepDocumentUnderReview() {
        this.step = EApplicationStep.APPLICATION_IN_PROGRESS;
        this.lastStepUpdated = LocalDate.now();
    }

    public void updateFinalResult(boolean isApproved, String feedback) {
        if (isApproved) {
            this.step = EApplicationStep.APPLICATION_SUCCESS;
            this.result = true;
            this.lastStepUpdated = LocalDate.now();
        } else {
            this.step = EApplicationStep.APPLICATION_REJECTED;
            this.result = false;
            this.lastStepUpdated = LocalDate.now();
        }
        this.feedback = feedback;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }
}

