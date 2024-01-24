package com.shilaeva.mapping;

import com.shilaeva.dto.OwnerDto;
import com.shilaeva.models.Owner;

public class OwnerMapping {
    public static OwnerDto asDto(Owner owner) {
        return new OwnerDto(owner.getId(), owner.getName(), owner.getBirthdate());
    }
}
