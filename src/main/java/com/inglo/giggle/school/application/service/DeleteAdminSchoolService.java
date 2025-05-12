package com.inglo.giggle.school.application.service;

import com.inglo.giggle.school.application.port.in.command.DeleteAdminSchoolCommand;
import com.inglo.giggle.school.application.port.in.usecase.DeleteAdminSchoolUseCase;
import com.inglo.giggle.school.application.port.out.DeleteSchoolPort;
import com.inglo.giggle.school.application.port.out.LoadSchoolPort;
import com.inglo.giggle.school.domain.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteAdminSchoolService implements DeleteAdminSchoolUseCase {

    private final LoadSchoolPort loadSchoolPort;
    private final DeleteSchoolPort deleteSchoolPort;

    @Override
    @Transactional
    public void execute(DeleteAdminSchoolCommand command) {
        School school = loadSchoolPort.loadSchoolOrElseThrow(command.getSchoolId());

        // 학교 삭제
        deleteSchoolPort.deleteSchool(school);
    }
}
