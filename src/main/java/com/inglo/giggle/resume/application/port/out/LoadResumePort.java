package com.inglo.giggle.resume.application.port.out;

import com.inglo.giggle.resume.domain.Resume;

import java.util.UUID;

public interface LoadResumePort {

    Resume loadResume(UUID id);
}
