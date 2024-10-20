package com.inglo.giggle.security.repository.redis;

import com.inglo.giggle.security.domain.redis.AuthenticationCodeHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationCodeHistoryRepository extends CrudRepository<AuthenticationCodeHistory, String> {
}
