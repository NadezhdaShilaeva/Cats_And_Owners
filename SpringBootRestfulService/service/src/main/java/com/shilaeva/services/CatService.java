package com.shilaeva.services;

import com.shilaeva.dto.CatDto;
import com.shilaeva.dto.OwnerDto;

import java.util.Date;
import java.util.List;

public interface CatService {
    CatDto getById(Long catId);

    List<CatDto> getByName(String catName);
    List<CatDto> getByNameAndOwner(String catName, Long ownerId);

    List<CatDto> getByBirthdate(Date catBirthdate);

    List<CatDto> getByBirthdateAndOwner(Date catBirthdate, Long ownerId);

    List<CatDto> getByBreed(String catBreed);

    List<CatDto> getByBreedAndOwner(String catBreed, Long ownerId);

    List<CatDto> getByColor(String catColor);

    List<CatDto> getByColorAndOwner(String catColor, Long ownerId);

    List<CatDto> getByOwner(Long catOwnerId);

    List<CatDto> getAll();

    OwnerDto getOwner(Long catId);

    List<CatDto> getFriends(Long catId);

    CatDto createCat(String name, Date birthdate, String breed, String color);

    CatDto setName(Long catId, String newName);

    CatDto setBirthdate(Long catId, Date newBirthdate);

    CatDto setBreed(Long catId, String newBreed);

    CatDto setColor(Long catId, String newColor);

    void addFriend(Long firstCatId, Long secondCatId);

    void removeFriend(Long firstCatId, Long secondCatId);

    void deleteCat(Long catId);
}
