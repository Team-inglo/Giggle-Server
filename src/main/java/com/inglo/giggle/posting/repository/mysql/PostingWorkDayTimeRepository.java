package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.posting.domain.PostingWorkDayTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PostingWorkDayTimeRepository extends JpaRepository<PostingWorkDayTime, Long> {
}
