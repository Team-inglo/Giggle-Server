package com.inglo.giggle.admin.adapter.out.persistence;

import com.inglo.giggle.admin.adapter.out.persistence.mapper.AdminMapper;
import com.inglo.giggle.admin.adapter.out.persistence.repository.mysql.AdminJpaRepository;
import com.inglo.giggle.admin.application.port.out.CreateAdminPort;
import com.inglo.giggle.admin.domain.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminPersistenceAdapter implements CreateAdminPort {

    private final AdminJpaRepository adminJpaRepository;
    private final AdminMapper adminMapper;

    @Override
    public void createAdmin(Admin admin) {
        adminJpaRepository.save(adminMapper.toEntity(admin));
    }
}
