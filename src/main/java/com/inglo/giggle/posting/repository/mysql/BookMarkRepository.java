package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.posting.domain.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long>{
}
