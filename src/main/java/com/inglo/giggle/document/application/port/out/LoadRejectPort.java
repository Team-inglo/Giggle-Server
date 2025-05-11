package com.inglo.giggle.document.application.port.out;

import com.inglo.giggle.document.domain.Reject;

import java.util.List;

public interface LoadRejectPort {

    Reject loadRejectOrElseNull(Long id);

    List<Reject> loadRejects(Long id);

}
