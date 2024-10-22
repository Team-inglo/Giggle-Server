package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOwnerJobPostingRepository extends JpaRepository<UserOwnerJobPosting, Long>{
}
