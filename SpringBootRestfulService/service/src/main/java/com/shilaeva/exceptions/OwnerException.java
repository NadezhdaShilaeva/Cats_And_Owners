package com.shilaeva.exceptions;

import com.shilaeva.models.Cat;
import com.shilaeva.models.Owner;

import java.util.Date;

public class OwnerException extends RuntimeException {
    private OwnerException(String message) {
        super(message);
    }

    public static OwnerException ownerNotExists(Long ownerId) {
        return new OwnerException("Error: owner with id " + ownerId + " does not exists.");
    }

    public static OwnerException ownersNotFoundByName(String ownerName) {
        return new OwnerException("Error: owners with name " + ownerName + " were not found.");
    }

    public static OwnerException ownersNotFoundByBirthdate(Date ownerBirthdate) {
        return new OwnerException("Error: owners with birthdate " + ownerBirthdate + " were not found.");
    }

    public static OwnerException ownersNotFound() {
        return new OwnerException("Error: owners were not found.");
    }

    public static OwnerException catsNotFound(Owner owner) {
        return new OwnerException("Error: the owner " + owner.getName() + "(Id: " + owner.getId()
                + " ) does not have cats.");
    }

    public static OwnerException catIsAlreadyExist(Cat cat, Owner owner) {
        return new OwnerException("Error: the owner " + owner.getName() + " (Id: " + owner.getId()
                + ") is already has the cat " + cat.getName() + "(Id: " + cat.getId() + ").");
    }

    public static OwnerException catNotRemoved(Cat cat) {
        return new OwnerException("Error: the cat " + cat.getName() + "(Id: " + cat.getId() + ") was not removed.");
    }
}
