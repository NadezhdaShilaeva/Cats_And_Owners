package com.shilaeva.services;

import com.shilaeva.dto.CatDto;
import com.shilaeva.dto.OwnerDto;

import java.util.Date;
import java.util.List;

public interface OwnerService {
    OwnerDto findById(long ownerId);

    List<OwnerDto> findAll();

    List<CatDto> findCats(long ownerId);

    OwnerDto createOwner(String name, Date birthdate);

    OwnerDto setName(long ownerId, String newName);

    OwnerDto setBirthdate(long ownerId, Date newBirthdate);

    void addCat(long ownerId, long catId);

    void removeCat(long ownerId, long catId);

    void deleteOwner(long ownerId);
}
