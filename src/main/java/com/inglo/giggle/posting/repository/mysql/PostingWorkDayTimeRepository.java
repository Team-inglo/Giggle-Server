package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.posting.domain.PostingWorkDayTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingWorkDayTimeRepository extends JpaRepository<PostingWorkDayTime, Long> {
}
