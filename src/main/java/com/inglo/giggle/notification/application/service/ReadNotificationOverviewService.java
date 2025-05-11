package com.inglo.giggle.notification.application.service;

import com.inglo.giggle.notification.presentation.dto.ReadNotificationOverviewResponseDto;
import com.inglo.giggle.notification.application.usecase.ReadNotificationOverviewUseCase;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.persistence.repository.NotificationRepository;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import com.inglo.giggle.security.account.application.port.out.LoadAccountPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadNotificationOverviewService implements ReadNotificationOverviewUseCase {

    private final LoadAccountPort loadAccountPort;
    private final NotificationRepository notificationRepository;

    private final static String DESCENDING = "DESC";

    @Override
    @Transactional(readOnly = true)
    public ReadNotificationOverviewResponseDto execute(UUID accountId, Integer page, Integer size) {

        Account account = loadAccountPort.loadAccount(accountId);

        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        Page<Notification> notificationList;
        if (account.getRole().equals(ESecurityRole.USER)) {
            notificationList = notificationRepository.findByUserOwnerJobPostingUserId(accountId, PageRequest.of(page - 1, size, sort));
        } else {
            notificationList = notificationRepository.findByUserOwnerJobPostingOwnerId(accountId, PageRequest.of(page - 1, size, sort));
        }

        return ReadNotificationOverviewResponseDto.fromPages(notificationList);
    }
}
