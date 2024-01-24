package com.shilaeva.exceptions;

import com.shilaeva.models.Cat;

public class CatException extends RuntimeException {
    private CatException(String message) {
        super(message);
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
