package com.inglo.giggle.notification.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.repository.NotificationRepository;
import com.inglo.giggle.notification.repository.mysql.NotificationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationJpaRepository notificationJpaRepository;

    @Override
    public Notification findByIdOrElseThrow(Long id) {
        return notificationJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_NOTIFICATION));
    }

    @Override
    public Page<Notification> findByUserOwnerJobPostingUserId(UUID userId, Pageable pageable) {
        return notificationJpaRepository.findByUserOwnerJobPostingUserId(userId, pageable);
    }

    @Override
    public Page<Notification> findByUserOwnerJobPostingOwnerId(UUID ownerId, Pageable pageable) {
        return notificationJpaRepository.findByUserOwnerJobPostingOwnerId(ownerId, pageable);
    }

    @Override
    public void save(Notification notification) {
        notificationJpaRepository.save(notification);
    }

    @Override
    public void delete(Notification notification) {
        notificationJpaRepository.delete(notification);
    }

    @Override
    public void deleteById(Long id) {
        notificationJpaRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<Notification> notifications) {
        notificationJpaRepository.deleteAll(notifications);
    }
}
