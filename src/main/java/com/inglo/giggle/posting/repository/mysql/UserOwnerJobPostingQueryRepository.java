package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface UserOwnerJobPostingQueryRepository {

    Page<UserOwnerJobPosting> searchWithConditions(
            String search,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            String filterType,
            String filter,
            Pageable pageable
    );
}
