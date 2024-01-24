package com.shilaeva.exceptions;

import com.shilaeva.models.Cat;
import com.shilaeva.models.Owner;

import java.util.Date;

public class CatException extends RuntimeException {
    private CatException(String message) {
        super(message);
    }

    public static CatException ownerNotExists(Long ownerId) {
        return new CatException("Error: owner with id " + ownerId + " does not exists.");
    }

    public static CatException catNotExists(Long catId) {
        return new CatException("Error: cat with id " + catId + " does not exists.");
    }

    public static CatException catsNotFoundByName(String catName) {
        return new CatException("Error: cats with name " + catName + " were not found.");
    }

    public static CatException catsNotFoundByBirthdate(Date catBirthdate) {
        return new CatException("Error: cats with birthdate " + catBirthdate + " were not found.");
    }

    public static CatException catsNotFoundByBreed(String catBreed) {
        return new CatException("Error: cats with breed " + catBreed + " were not found.");
    }

    public static CatException catsNotFoundByColor(String catColor) {
        return new CatException("Error: cats with color " + catColor + " were not found.");
    }

    public static CatException catsNotFoundByOwner(Owner catOwner) {
        return new CatException("Error: cats with owner " + catOwner.getName() + " (Id: " + catOwner.getId()
                + " ) was not found.");
    }

    public static CatException catsNotFound() {
        return new CatException("Error: cats was not found.");
    }

    public static CatException ownerNotFound(Cat cat) {
        return new CatException("Error: the cat " + cat.getName() +  "(Id: " + cat.getId() + " ) does not have owner.");
    }

    public static CatException friendsNotFound(Cat cat) {
        return new CatException("Error: the cat " + cat.getName() +  "(Id: " + cat.getId()
                + " ) does not have friends.");
    }

    public static CatException theFriendIsAlreadyExist(Cat firstCat, Cat secondCat) {
        return new CatException("Error: the cat " + firstCat.getName() + " (Id: " + firstCat.getId() + " ) and cat "
                + secondCat.getName() + " (Id: " + secondCat.getId() + " ) are already friends.");
    }

    public static CatException theSameCat(Cat cat) {
        return new CatException("Error: the cat " + cat.getName() + " (Id: " + cat.getId()
                + " ) cannot be friends with itself.");
    }

    public static CatException catNotRemoved(Cat firstCat, Cat secondCat) {
        return new CatException("Error: the cat " + firstCat.getName() + "(Id: " + firstCat.getId()
                + ") was not removed from friends of the cat " + secondCat.getName() + " (Id: " + secondCat.getId()
                + " ).");
    }
}
