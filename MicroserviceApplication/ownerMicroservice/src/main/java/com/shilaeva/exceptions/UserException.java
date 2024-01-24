package com.shilaeva.exceptions;

public class UserException extends RuntimeException {
    private UserException(String message) {
        super(message);
    }

    public static UserException userAlreadyExists(String username) {
        return new UserException("Error: the user with username " + username + " is already exists.");
    }

    public static UserException userNotExists(Long userId) {
        return new UserException("Error: user with id " + userId + " does not exist.");
    }

    public static UserException userNotFoundByUsername(String username) {
        return new UserException("Error: user with username " + username + " was not found.");
    }

    public static UserException userDoesNotHaveOwner(Long userId) {
        return new UserException("Error: user with id " + userId + " is not the owner.");
    }

    public static UserException usersNotFound() {
        return new UserException("Error: users were not found.");
    }
}
