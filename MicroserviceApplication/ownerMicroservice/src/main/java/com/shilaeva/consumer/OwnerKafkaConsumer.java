package com.shilaeva.consumer;

import com.shilaeva.dto.*;
import com.shilaeva.exceptions.OwnerException;
import com.shilaeva.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class OwnerKafkaConsumer {
    @Autowired
    private OwnerService ownerService;

    @KafkaListener(topics = "get-owner-by-id")
    @SendTo
    public Object getOwnerById(Long ownerId) {
        try {
            return ownerService.getById(ownerId);
        } catch (OwnerException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-owner-by-name")
    @SendTo
    public Object getByName(String ownerName) {
        try {
            return ownerService.getByName(ownerName).toArray(new OwnerDto[0]);
        } catch (OwnerException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-owner-by-birthdate")
    @SendTo
    public Object getByBirthdate(BirthdateDto ownerBirthdate) {
        try {
            return ownerService.getByBirthdate(ownerBirthdate.asDate()).toArray(new OwnerDto[0]);
        } catch (OwnerException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-all-owners")
    @SendTo
    public Object getAll() {
        try {
            return ownerService.getAll().toArray(new OwnerDto[0]);
        } catch (OwnerException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "get-owner-cats")
    @SendTo
    public Object getCats(Long ownerId) {
        try {
            return ownerService.getCats(ownerId).toArray(new CatDto[0]);
        } catch (OwnerException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "create-owner")
    @SendTo
    public Object createOwner(CreateOwnerDto createOwnerDto) {
        try {
            return ownerService.createOwner(createOwnerDto.name(), createOwnerDto.birthdate().asDate());
        } catch (OwnerException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "set-owner-name")
    @SendTo
    public Object setName(SetOwnerNameDto setOwnerNameDto) {
        try {
            return ownerService.setName(setOwnerNameDto.ownerId(), setOwnerNameDto.ownerName());
        } catch (OwnerException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "set-owner-birthdate")
    @SendTo
    public Object setBirthdate(SetOwnerBirthdateDto setOwnerBirthdateDto) {
        try {
            return ownerService
                    .setBirthdate(setOwnerBirthdateDto.ownerId(), setOwnerBirthdateDto.ownerBirthdate().asDate());
        } catch (OwnerException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "add-owner-cat")
    @SendTo
    public Object addCat(OwnerAndCatDto ownerAndCatDto) {
        try {
            ownerService.addCat(ownerAndCatDto.ownerId(), ownerAndCatDto.catId());

            return "Cat was added to the owner.";
        } catch (OwnerException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "remove-owner-cat")
    @SendTo
    public Object removeCat(OwnerAndCatDto ownerAndCatDto) {
        try {
            ownerService.removeCat(ownerAndCatDto.ownerId(), ownerAndCatDto.catId());

            return "Cat was removed from the owner.";
        } catch (OwnerException e) {
            return e.getMessage();
        }
    }

    @KafkaListener(topics = "delete-owner")
    @SendTo
    public Object deleteOwner(Long ownerId) {
        try {
            ownerService.deleteOwner(ownerId);

            return "Owner was deleted.";
        } catch (OwnerException e) {
            return e.getMessage();
        }
    }
}
