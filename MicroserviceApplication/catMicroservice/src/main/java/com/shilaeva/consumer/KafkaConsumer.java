package com.shilaeva.consumer;

import com.shilaeva.dto.*;
import com.shilaeva.exceptions.CatException;
import com.shilaeva.services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @Autowired
    private CatService catService;

    @KafkaListener(topics = "get-cat-by-id")
    @SendTo
    public Object getCatById(Long catId) {
        try {
            return catService.getById(catId);
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-cats-by-name")
    @SendTo
    public Object getByName(String catName) {
        try {
            return catService.getByName(catName).toArray(new CatDto[0]);
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-cats-by-name-and-owner")
    @SendTo
    public Object getByNameAndOwner(CatNameAndOwnerDto catNameAndOwnerDto) {
        try {
            return catService.getByNameAndOwner(catNameAndOwnerDto.catName(), catNameAndOwnerDto.ownerId())
                    .toArray(new CatDto[0]);
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-cats-by-birthdate")
    @SendTo
    public Object getByBirthdate(BirthdateDto catBirthdate) {
        try {
            return catService.getByBirthdate(catBirthdate.asDate()).toArray(new CatDto[0]);
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-cats-by-birthdate-and-owner")
    @SendTo
    public Object getByBirthdateAndOwner(CatBirthdateAndOwnerDto catBirthdateAndOwnerDto) {
        try {
            return catService.getByBirthdateAndOwner(catBirthdateAndOwnerDto.catBirthdate().asDate(),
                    catBirthdateAndOwnerDto.ownerId()).toArray(new CatDto[0]);
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-cats-by-breed")
    @SendTo
    public Object getByBreed(String catBreed) {
        try {
            return catService.getByBreed(catBreed).toArray(new CatDto[0]);
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-cats-by-breed-and-owner")
    @SendTo
    public Object getByBreedAndOwner(CatBreedAndOwnerDto catBreedAndOwnerDto) {
        try {
            return catService.getByBreedAndOwner(catBreedAndOwnerDto.catBreed(), catBreedAndOwnerDto.ownerId())
                    .toArray(new CatDto[0]);
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-cats-by-color")
    @SendTo
    public Object getByColor(String catColor) {
        try {
            return catService.getByColor(catColor).toArray(new CatDto[0]);
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-cats-by-color-and-owner")
    @SendTo
    public Object getByColorAndOwner(CatColorAndOwnerDto catColorAndOwnerDto) {
        try {
            return catService.getByColorAndOwner(catColorAndOwnerDto.catColor(), catColorAndOwnerDto.ownerId())
                    .toArray(new CatDto[0]);
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-cats-by-owner")
    @SendTo
    public Object getByOwner(Long catOwnerId) {
        try {
            return catService.getByOwner(catOwnerId).toArray(new CatDto[0]);
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-all-cats")
    @SendTo
    public Object getAll() {
        try {
            return catService.getAll().toArray(new CatDto[0]);
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-cat-owner")
    @SendTo
    public Object getOwner(Long catId) {
        try {
            return catService.getOwner(catId);
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-cat-friends")
    @SendTo
    public Object getFriends(Long catId) {
        try {
            return catService.getFriends(catId).toArray(new CatDto[0]);
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "create-cat")
    @SendTo
    public Object createCat(CreateCatDto createCatDto) {
        try {
            return catService.createCat(createCatDto.name(), createCatDto.birthdate().asDate(), createCatDto.breed(),
                    createCatDto.color());
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "set-cat-name")
    @SendTo
    public Object setName(SetCatNameDto setCatNameDto) {
        try {
            return catService.setName(setCatNameDto.catId(), setCatNameDto.catName());
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "set-cat-birthdate")
    @SendTo
    public Object setBirthdate(SetCatBirthdateDto setCatBirthdateDto) {
        try {
            return catService.setBirthdate(setCatBirthdateDto.catId(), setCatBirthdateDto.catBirthdate().asDate());
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "set-cat-breed")
    @SendTo
    public Object setBreed(SetCatBreedDto setCatBreedDto) {
        try {
            return catService.setBreed(setCatBreedDto.catId(), setCatBreedDto.catBreed());
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "set-cat-color")
    @SendTo
    public Object setColor(SetCatColorDto setCatColorDto) {
        try {
            return catService.setColor(setCatColorDto.catId(), setCatColorDto.catColor());
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "add-cat-friend")
    @SendTo
    public Object addFriend(CatAndFriendDto catAndFriendDto) {
        try {
            catService.addFriend(catAndFriendDto.catId(), catAndFriendDto.FriendId());

            return "Cat was added to the friends.";
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "remove-cat-friend")
    @SendTo
    public Object removeFriend(CatAndFriendDto catAndFriendDto) {
        try {
            catService.removeFriend(catAndFriendDto.catId(), catAndFriendDto.FriendId());

            return "Cat was removed from the friends.";
        } catch (CatException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "delete-cat")
    @SendTo
    public Object deleteCat(Long catId) {
        try {
            catService.deleteCat(catId);

            return "Cat was deleted.";
        } catch (CatException e) {
            return e.getMessage();
        }
    }
}
