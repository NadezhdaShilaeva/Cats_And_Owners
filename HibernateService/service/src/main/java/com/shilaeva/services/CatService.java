package com.shilaeva.services;

import com.shilaeva.dto.CatDto;
import com.shilaeva.dto.OwnerDto;

import java.util.Date;
import java.util.List;

public interface CatService {
    CatDto findById(long catId);

    List<CatDto> findAll();

    OwnerDto findOwner(long catId);

    List<CatDto> findFriends(long catId);

    CatDto createCat(String name, Date birthdate, String color);

    CatDto setName(long catId, String newName);

    CatDto setBirthdate(long catId, Date newBirthdate);

    CatDto setBreed(long catId, String newBreed);

    CatDto setColor(long catId, String newColor);

    void addFriend(long firstCatId, long secondCatId);

    void removeFriend(long firstCatId, long secondCatId);

    void deleteCat(long catId);
}
