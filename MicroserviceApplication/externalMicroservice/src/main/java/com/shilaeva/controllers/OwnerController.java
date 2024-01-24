package com.shilaeva.controllers;

import com.shilaeva.dto.*;
import com.shilaeva.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    @Autowired
    private KafkaProducer kafkaProducer;

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasOwnerId(principal, #id)")
    @GetMapping("/get-by-id")
    public ResponseEntity<?> getOwnerById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(kafkaProducer
                    .sendAndReceive("get-owner-by-id", "reply-owner-by-id", id));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-by-name")
    public ResponseEntity<?> getOwnersByName(@RequestParam String name) {
        try {
            return ResponseEntity.ok(kafkaProducer
                    .sendAndReceive("get-owner-by-name", "reply-owner-by-name", name));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-by-birthdate")
    public ResponseEntity<?> getOwnersByBirthdate(@RequestBody BirthdateDto birthdate) {
        try {
            return ResponseEntity.ok(kafkaProducer
                    .sendAndReceive("get-owner-by-birthdate", "reply-owner-by-birthdate", birthdate));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllOwners() {
        try {
            return ResponseEntity.ok(kafkaProducer
                    .sendAndReceive("get-all-owners", "reply-all-owners", null));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasOwnerId(principal, #ownerId)")
    @GetMapping("/get-cats/{ownerId}")
    public ResponseEntity<?> getOwnerCats(@PathVariable Long ownerId) {
        try {
            return ResponseEntity.ok(kafkaProducer
                    .sendAndReceive("get-owner-cats", "reply-owner-cats", ownerId));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createOwner(@RequestBody CreateOwnerDto ownerModel) {
        try {
            return ResponseEntity.ok(kafkaProducer
                    .sendAndReceive("create-owner", "reply-create-owner", ownerModel));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasOwnerId(principal, #ownerId)")
    @PutMapping("/set-name/{ownerId}")
    public ResponseEntity<?> setOwnerName(@PathVariable Long ownerId, String newName) {
        try {
            return ResponseEntity.ok(kafkaProducer.sendAndReceive("set-owner-name",
                    "reply-set-owner-name", new SetOwnerNameDto(ownerId, newName)));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasOwnerId(principal, #ownerId)")
    @PutMapping("/set-birthdate/{ownerId}")
    public ResponseEntity<?> setOwnerBirthdate(@PathVariable Long ownerId, @RequestBody BirthdateDto newBirthdate) {
        try {
            return ResponseEntity.ok(kafkaProducer.sendAndReceive("set-owner-birthdate",
                    "reply-set-owner-birthdate", new SetOwnerBirthdateDto(ownerId, newBirthdate)));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasOwnerId(principal, #ownerId)")
    @PutMapping("/add-cat/{ownerId}")
    public ResponseEntity<?> addCat(@PathVariable Long ownerId, Long catId) {
        try {
            return ResponseEntity.ok(kafkaProducer.sendAndReceive("add-owner-cat",
                    "reply-add-owner-cat", new OwnerAndCatDto(ownerId, catId)));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasOwnerId(principal, #ownerId)")
    @PutMapping("/remove-cat/{ownerId}")
    public ResponseEntity<?> removeCat(@PathVariable Long ownerId, Long catId) {
        try {
            return ResponseEntity.ok(kafkaProducer.sendAndReceive("remove-owner-cat",
                    "reply-remove-owner-cat", new OwnerAndCatDto(ownerId, catId)));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{ownerId}")
    public ResponseEntity<?> deleteOwner(@PathVariable Long ownerId) {
        try {
            return ResponseEntity
                    .ok(kafkaProducer.sendAndReceive("delete-owner", "reply-delete-owner", ownerId));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
