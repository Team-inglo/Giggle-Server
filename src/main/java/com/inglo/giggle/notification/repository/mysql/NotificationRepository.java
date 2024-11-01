package com.inglo.giggle.notification.repository.mysql;

import com.inglo.giggle.notification.domain.Notification;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n " +
            "JOIN FETCH n.userOwnerJobPosting u " +
            "JOIN FETCH u.user " +
            "JOIN FETCH u.jobPosting " +
            "WHERE u.user.id = :userId")
    Page<Notification> findByUserOwnerJobPostingUserId(@Param("userId") UUID userId, Pageable pageable);

    @Query("SELECT n FROM Notification n " +
            "JOIN FETCH n.userOwnerJobPosting u " +
            "JOIN FETCH u.owner " +
            "JOIN FETCH u.jobPosting " +
            "WHERE u.owner.id = :ownerId")
    Page<Notification> findByUserOwnerJobPostingOwnerId(@Param("ownerId") UUID ownerId, Pageable pageable);


}
