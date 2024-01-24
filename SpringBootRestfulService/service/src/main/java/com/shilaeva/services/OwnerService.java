package com.shilaeva.services;

import com.shilaeva.dto.CatDto;
import com.shilaeva.dto.OwnerDto;

import java.util.Date;
import java.util.List;

public interface OwnerService {
    OwnerDto getById(Long ownerId);

    List<OwnerDto> getByName(String ownerName);

    List<OwnerDto> getByBirthdate(Date ownerBirthdate);

    List<OwnerDto> getAll();

    List<CatDto> getCats(Long ownerId);

    OwnerDto createOwner(String name, Date birthdate);

    OwnerDto setName(Long ownerId, String newName);

    OwnerDto setBirthdate(Long ownerId, Date newBirthdate);

    void addCat(Long ownerId, Long catId);

    void removeCat(Long ownerId, Long catId);

    void deleteOwner(Long ownerId);
}
