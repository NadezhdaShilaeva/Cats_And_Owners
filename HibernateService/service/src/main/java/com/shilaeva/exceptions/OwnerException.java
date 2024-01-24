package com.shilaeva.exceptions;

import com.shilaeva.models.Cat;
import com.shilaeva.models.Owner;

public class OwnerException extends RuntimeException {
    private OwnerException(String message) {
        super(message);
    }

    public static OwnerException catIsAlreadyExist(Cat cat, Owner owner) {
        return new OwnerException("Error: the owner " + owner.getName() + " (Id: " + owner.getId()
                + ") is already has the cat " + cat.getName() + "(Id: " + cat.getId() + ").");
    }

    public static OwnerException catNotRemoved(Cat cat) {
        return new OwnerException("Error: the cat " + cat.getName() + "(Id: " + cat.getId() + ") was not removed.");
    }
}
