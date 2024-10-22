package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.posting.domain.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMarkRepository extends JpaRepository<BookMark, Long>{
}
