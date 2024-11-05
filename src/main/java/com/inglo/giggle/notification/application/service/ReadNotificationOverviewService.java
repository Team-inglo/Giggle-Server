package com.inglo.giggle.notification.application.service;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.notification.application.dto.ReadNotificationOverviewResponseDto;
import com.inglo.giggle.notification.application.usecase.ReadNotificationOverviewUseCase;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.repository.mysql.NotificationRepository;
import com.inglo.giggle.security.domain.mysql.Account;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import com.inglo.giggle.security.repository.mysql.AccountRepository;
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

    private final AccountRepository accountRepository;
    private final NotificationRepository notificationRepository;

    private final static String DESCENDING = "DESC";

    @Override
    @Transactional(readOnly = true)
    public ReadNotificationOverviewResponseDto execute(UUID accountId, Integer page, Integer size) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

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
