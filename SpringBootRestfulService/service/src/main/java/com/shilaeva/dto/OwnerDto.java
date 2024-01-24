package com.shilaeva.dto;

import com.shilaeva.models.Owner;

import java.util.Date;

public record OwnerDto(Long id, String name, Date birthdate) {
    public static OwnerDto asDto(Owner owner) {
        return new OwnerDto(owner.getId(), owner.getName(), owner.getBirthdate());
    }
}
