package com.inglo.giggle;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.TimeZone;

@EnableAsync
@EnableJpaRepositories(basePackages = "com.inglo.giggle.*.adapter.out.persistence.repository.mysql")
@EnableRedisRepositories(basePackages = "com.inglo.giggle.security.*.adapter.out.persistence.repository.redis")
@SpringBootApplication
public class GiggleSeverApplication {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args) {
        SpringApplication.run(GiggleSeverApplication.class, args);
    }

}
