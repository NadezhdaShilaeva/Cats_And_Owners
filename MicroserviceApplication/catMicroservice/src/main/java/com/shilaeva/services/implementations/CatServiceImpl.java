package com.shilaeva.services.implementations;

import com.shilaeva.dto.CatDto;
import com.shilaeva.dto.OwnerDto;
import com.shilaeva.exceptions.CatException;
import com.shilaeva.models.Cat;
import com.shilaeva.models.CatColor;
import com.shilaeva.models.Owner;
import com.shilaeva.repositories.CatRepository;
import com.shilaeva.repositories.OwnerRepository;
import com.shilaeva.services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CatServiceImpl implements CatService {
    @Autowired
    private CatRepository catRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    public CatDto getById(Long catId) {
        return CatDto.asDto(findCatById(catId));
    }

    public List<CatDto> getByName(String catName) {
        List<Cat> cats = catRepository.findByName(catName);

        if (cats == null || cats.isEmpty()) {
            throw CatException.catsNotFoundByName(catName);
        }

        return cats.stream().map(CatDto::asDto).toList();
    }

    public List<CatDto> getByNameAndOwner(String catName, Long ownerId) {
        Owner catOwner = findOwnerById(ownerId);
        List<Cat> cats = catRepository.findByNameAndOwner(catName, catOwner);

        if (cats == null || cats.isEmpty()) {
            throw CatException.catsNotFoundByName(catName);
        }

        return cats.stream().map(CatDto::asDto).toList();
    }

    public List<CatDto> getByBirthdate(Date catBirthdate) {
        List<Cat> cats = catRepository.findByBirthdate(catBirthdate);

        if (cats == null || cats.isEmpty()) {
            throw CatException.catsNotFoundByBirthdate(catBirthdate);
        }

        return cats.stream().map(CatDto::asDto).toList();
    }

    public List<CatDto> getByBirthdateAndOwner(Date catBirthdate, Long ownerId) {
        Owner catOwner = findOwnerById(ownerId);
        List<Cat> cats = catRepository.findByBirthdateAndOwner(catBirthdate, catOwner);

        if (cats == null || cats.isEmpty()) {
            throw CatException.catsNotFoundByBirthdate(catBirthdate);
        }

        return cats.stream().map(CatDto::asDto).toList();
    }

    public List<CatDto> getByBreed(String catBreed) {
        List<Cat> cats = catRepository.findByBreed(catBreed);

        if (cats == null || cats.isEmpty()) {
            throw CatException.catsNotFoundByBreed(catBreed);
        }

        return cats.stream().map(CatDto::asDto).toList();
    }

    public List<CatDto> getByBreedAndOwner(String catBreed, Long ownerId) {
        Owner catOwner = findOwnerById(ownerId);
        List<Cat> cats = catRepository.findByBreedAndOwner(catBreed, catOwner);

        if (cats == null || cats.isEmpty()) {
            throw CatException.catsNotFoundByBreed(catBreed);
        }

        return cats.stream().map(CatDto::asDto).toList();
    }

    public List<CatDto> getByColor(String catColor) {
        List<Cat> cats = catRepository.findByColor(CatColor.valueOf(catColor.toUpperCase()));

        if (cats == null || cats.isEmpty()) {
            throw CatException.catsNotFoundByColor(catColor);
        }

        return cats.stream().map(CatDto::asDto).toList();
    }

    public List<CatDto> getByColorAndOwner(String catColor, Long ownerId) {
        Owner catOwner = findOwnerById(ownerId);
        List<Cat> cats = catRepository.findByColorAndOwner(CatColor.valueOf(catColor.toUpperCase()), catOwner);

        if (cats == null || cats.isEmpty()) {
            throw CatException.catsNotFoundByColor(catColor);
        }

        return cats.stream().map(CatDto::asDto).toList();
    }

    public List<CatDto> getByOwner(Long catOwnerId) {
        Owner catOwner = findOwnerById(catOwnerId);
        List<Cat> cats = catRepository.findByOwner(catOwner);
        if (cats == null || cats.isEmpty()) {
            throw CatException.catsNotFoundByOwner(catOwner);
        }

        return cats.stream().map(CatDto::asDto).toList();
    }

    public List<CatDto> getAll() {
        List<Cat> cats = catRepository.findAll();

        if (cats.isEmpty()) {
            throw CatException.catsNotFound();
        }

        return cats.stream().map(CatDto::asDto).toList();
    }

    public OwnerDto getOwner(Long catId) {
        Cat cat = findCatById(catId);
        if (cat.getOwner() == null) {
            throw CatException.ownerNotFound(cat);
        }

        return OwnerDto.asDto(cat.getOwner());
    }

    public List<CatDto> getFriends(Long catId) {
        Cat cat = findCatById(catId);
        if (cat.getFriends().isEmpty()) {
            throw CatException.friendsNotFound(cat);
        }

        return cat.getFriends().stream().map(CatDto::asDto).toList();
    }

    public CatDto createCat(String name, Date birthdate, String breed, String color) {
        Cat newCat = new Cat(name, birthdate, breed, CatColor.valueOf(color.toUpperCase()));

        return CatDto.asDto(catRepository.save(newCat));
    }

    public CatDto setName(Long catId, String newName) {
        Cat cat = findCatById(catId);
        cat.setName(newName);

        return CatDto.asDto(catRepository.save(cat));
    }

    public CatDto setBirthdate(Long catId, Date newBirthdate) {
        Cat cat = findCatById(catId);
        cat.setBirthdate(newBirthdate);

        return CatDto.asDto(catRepository.save(cat));
    }

    public CatDto setBreed(Long catId, String newBreed) {
        Cat cat = findCatById(catId);
        cat.setBreed(newBreed);

        return CatDto.asDto(catRepository.save(cat));
    }

    public CatDto setColor(Long catId, String newColor) {
        Cat cat = findCatById(catId);
        cat.setColor(CatColor.valueOf(newColor.toUpperCase()));

        return CatDto.asDto(catRepository.save(cat));
    }

    public void addFriend(Long firstCatId, Long secondCatId) {
        Cat firstCat = findCatById(firstCatId);
        Cat secondCat = findCatById(secondCatId);

        if (firstCat.equals(secondCat)) {
            throw CatException.theSameCat(firstCat);
        }

        if (firstCat.getFriends().contains(secondCat)) {
            throw CatException.theFriendIsAlreadyExist(firstCat, secondCat);
        }

        firstCat.getFriends().add(secondCat);
        secondCat.getFriends().add(firstCat);

        catRepository.save(firstCat);
        catRepository.save(secondCat);
    }

    public void removeFriend(Long firstCatId, Long secondCatId) {
        Cat firstCat = findCatById(firstCatId);
        Cat secondCat = findCatById(secondCatId);

        if (!firstCat.getFriends().remove(secondCat)) {
            throw CatException.catNotRemoved(secondCat, firstCat);
        }

        if (!secondCat.getFriends().remove(firstCat)) {
            throw CatException.catNotRemoved(firstCat, secondCat);
        }

        catRepository.save(firstCat);
        catRepository.save(secondCat);
    }

    public void deleteCat(Long catId) {
        catRepository.deleteById(catId);
    }

    private Cat findCatById(Long catId) {
        Optional<Cat> cat = catRepository.findById(catId);
        if (cat.isEmpty()) {
            throw CatException.catNotExists(catId);
        }

        return cat.get();
    }

    private Owner findOwnerById(Long ownerId) {
        Optional<Owner> owner = ownerRepository.findById(ownerId);
        if (owner.isEmpty()) {
            throw CatException.ownerNotExists(ownerId);
        }

        return owner.get();
    }
}
