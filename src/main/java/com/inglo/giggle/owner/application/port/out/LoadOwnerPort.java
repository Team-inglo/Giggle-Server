package com.inglo.giggle.owner.application.port.out;

import com.inglo.giggle.owner.domain.Owner;

import java.util.UUID;

public interface LoadOwnerPort {

    Owner loadOwner(UUID ownerId);

    Owner loadOwnerOrElseNull(UUID ownerId);
}
