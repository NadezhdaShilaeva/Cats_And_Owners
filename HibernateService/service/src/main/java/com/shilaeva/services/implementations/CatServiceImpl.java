package com.shilaeva.services.implementations;

import com.shilaeva.dao.CatDao;
import com.shilaeva.dto.CatDto;
import com.shilaeva.dto.OwnerDto;
import com.shilaeva.exceptions.CatException;
import com.shilaeva.mapping.CatMapping;
import com.shilaeva.mapping.OwnerMapping;
import com.shilaeva.models.Cat;
import com.shilaeva.models.CatColor;
import com.shilaeva.services.CatService;

import java.util.Date;
import java.util.List;

public class CatServiceImpl implements CatService {
    private final CatDao catDao;

    public CatServiceImpl(CatDao catDao) {
        this.catDao = catDao;
    }

    public CatDto findById(long catId) {
        return CatMapping.asDto(catDao.findById(catId));
    }

    public List<CatDto> findAll() {
        return catDao.findAll().stream().map(CatMapping::asDto).toList();
    }

    public OwnerDto findOwner(long catId) {
        return OwnerMapping.asDto(catDao.findById(catId).getOwner());
    }

    public List<CatDto> findFriends(long catId) {
        return catDao.findById(catId).getFriends().stream().map(CatMapping::asDto).toList();
    }

    public CatDto createCat(String name, Date birthdate, String color) {
        Cat newCat = new Cat(name, birthdate, CatColor.valueOf(color.toUpperCase()));
        catDao.save(newCat);

        return CatMapping.asDto(newCat);
    }

    public CatDto setName(long catId, String newName) {
        Cat cat = catDao.findById(catId);
        cat.setName(newName);

        return CatMapping.asDto(catDao.update(cat));
    }

    public CatDto setBirthdate(long catId, Date newBirthdate) {
        Cat cat = catDao.findById(catId);
        cat.setBirthdate(newBirthdate);

        return CatMapping.asDto(catDao.update(cat));
    }

    public CatDto setBreed(long catId, String newBreed) {
        Cat cat = catDao.findById(catId);
        cat.setBreed(newBreed);

        return CatMapping.asDto(catDao.update(cat));
    }

    public CatDto setColor(long catId, String newColor) {
        Cat cat = catDao.findById(catId);
        cat.setColor(CatColor.valueOf(newColor.toUpperCase()));

        return CatMapping.asDto(catDao.update(cat));
    }

    public void addFriend(long firstCatId, long secondCatId) {
        Cat firstCat = catDao.findById(firstCatId);
        Cat secondCat = catDao.findById(secondCatId);

        if (firstCat.equals(secondCat)) {
            throw CatException.theSameCat(firstCat);
        }

        if (firstCat.getFriends().contains(secondCat)) {
            throw CatException.theFriendIsAlreadyExist(firstCat, secondCat);
        }

        firstCat.getFriends().add(secondCat);
        secondCat.getFriends().add(firstCat);

        catDao.update(firstCat);
        catDao.update(secondCat);
    }

    public void removeFriend(long firstCatId, long secondCatId) {
        Cat firstCat = catDao.findById(firstCatId);
        Cat secondCat = catDao.findById(secondCatId);

        if (!firstCat.getFriends().remove(secondCat)) {
            throw CatException.catNotRemoved(secondCat, firstCat);
        }

        if (!secondCat.getFriends().remove(firstCat)) {
            throw CatException.catNotRemoved(firstCat, secondCat);
        }

        catDao.update(firstCat);
        catDao.update(secondCat);
    }

    public void deleteCat(long catId) {
        Cat cat = catDao.findById(catId);
        catDao.delete(cat);
    }
}
