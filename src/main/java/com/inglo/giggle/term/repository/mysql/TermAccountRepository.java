package com.inglo.giggle.term.repository.mysql;

import com.inglo.giggle.term.domain.TermAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermAccountRepository extends JpaRepository<TermAccount, Long> {
}
