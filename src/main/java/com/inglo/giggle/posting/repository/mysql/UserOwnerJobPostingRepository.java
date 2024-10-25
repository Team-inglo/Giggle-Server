package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserOwnerJobPostingRepository extends JpaRepository<UserOwnerJobPosting, Long>{

    @EntityGraph(attributePaths = {"jobPosting", "user"})
    Page<UserOwnerJobPosting> findAllByUser(User user, Pageable pageable);

    @EntityGraph(attributePaths = {"jobPosting", "user"})
    Page<UserOwnerJobPosting> findAllByUserAndStep(User user, EApplicationStep step, Pageable pageable);
}
