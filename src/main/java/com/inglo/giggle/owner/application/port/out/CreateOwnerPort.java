package com.inglo.giggle.owner.application.port.out;

import com.inglo.giggle.owner.domain.Owner;

public interface CreateOwnerPort {

    Owner createOwner(Owner owner);
}
