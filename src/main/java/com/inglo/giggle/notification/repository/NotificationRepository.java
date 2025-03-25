package com.inglo.giggle.notification.repository;

import com.inglo.giggle.notification.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository {

    Notification findByIdOrElseThrow(Long id);

    Page<Notification> findByUserOwnerJobPostingUserId(UUID userId, Pageable pageable);

    Page<Notification> findByUserOwnerJobPostingOwnerId(UUID ownerId, Pageable pageable);

    void save(Notification notification);

    void delete(Notification notification);

    void deleteById(Long id);

    void deleteAll(List<Notification> notifications);
}
