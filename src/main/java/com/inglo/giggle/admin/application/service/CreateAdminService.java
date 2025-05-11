package com.inglo.giggle.admin.application.service;

import com.inglo.giggle.admin.application.port.in.command.CreateAdminCommand;
import com.inglo.giggle.admin.application.port.in.usecase.CreateAdminUseCase;
import com.inglo.giggle.admin.application.port.out.CreateAdminPort;
import com.inglo.giggle.admin.domain.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAdminService implements CreateAdminUseCase {

    private final CreateAdminPort createAdminPort;

    @Override
    public void execute(CreateAdminCommand createAdminCommand) {
        Admin admin = Admin.builder()
                .id(createAdminCommand.getId())
                .build();
        createAdminPort.createAdmin(admin);
    }
}
