package com.shilaeva.controllers;

import com.shilaeva.dto.*;
import com.shilaeva.dto.BirthdateDto;
import com.shilaeva.dto.CreateCatDto;
import com.shilaeva.producer.KafkaProducer;
import com.shilaeva.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cats")
public class CatController {
    @Autowired
    private KafkaProducer kafkaProducer;

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasThisCat(principal, #id)")
    @GetMapping("/get-by-id")
    public ResponseEntity<?> getCatById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(kafkaProducer
                    .sendAndReceive("get-cat-by-id", "reply-cat-by-id", id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<?> getCatsByName(@AuthenticationPrincipal UserDetailsImpl user, @RequestParam String name) {
        try {
            if (user.getRole().getAuthority().equals("ROLE_ADMIN")) {
                return ResponseEntity.ok(kafkaProducer
                        .sendAndReceive("get-cats-by-name", "reply-cats-by-name", name));
            } else {
                return ResponseEntity.ok(kafkaProducer.sendAndReceive("get-cats-by-name-and-owner",
                        "reply-cats-by-name-and-owner", new CatNameAndOwnerDto(name, user.getOwnerId())));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-by-birthdate")
    public ResponseEntity<?> getCatsByBirthdate(@AuthenticationPrincipal UserDetailsImpl user, @RequestBody BirthdateDto birthdate) {
        try {
            if (user.getRole().getAuthority().equals("ROLE_ADMIN")) {
                return ResponseEntity.ok(kafkaProducer
                        .sendAndReceive("get-cats-by-birthdate", "reply-cats-by-birthdate", birthdate));
            } else {
                return ResponseEntity.ok(kafkaProducer.sendAndReceive("get-cats-by-birthdate-and-owner",
                        "reply-cats-by-birthdate-and-owner",
                        new CatBirthdateAndOwnerDto(birthdate, user.getOwnerId())));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-by-breed")
    public ResponseEntity<?> getCatsByBreed(@AuthenticationPrincipal UserDetailsImpl user, @RequestParam String breed) {
        try {
            if (user.getRole().getAuthority().equals("ROLE_ADMIN")) {
                return ResponseEntity.ok(kafkaProducer
                        .sendAndReceive("get-cats-by-breed", "reply-cats-by-breed", breed));
            } else {
                return ResponseEntity.ok(kafkaProducer.sendAndReceive("get-cats-by-breed-and-owner",
                        "reply-cats-by-breed-and-owner", new CatBreedAndOwnerDto(breed, user.getOwnerId())));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-by-color")
    public ResponseEntity<?> getCatsByColor(@AuthenticationPrincipal UserDetailsImpl user, @RequestParam String color) {
        try {
            if (user.getRole().getAuthority().equals("ROLE_ADMIN")) {
                return ResponseEntity.ok(kafkaProducer
                        .sendAndReceive("get-cats-by-color", "reply-cats-by-color", color));
            } else {
                return ResponseEntity.ok(kafkaProducer.sendAndReceive("get-cats-by-color-and-owner",
                        "reply-cats-by-color-and-owner", new CatColorAndOwnerDto(color, user.getOwnerId())));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasOwnerId(principal, #ownerId)")
    @GetMapping("/get-by-owner")
    public ResponseEntity<?> getCatsByOwner(@RequestParam Long ownerId) {
        try {
            return ResponseEntity.ok(kafkaProducer
                    .sendAndReceive("get-cats-by-owner", "reply-cats-by-owner", ownerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCats(@AuthenticationPrincipal UserDetailsImpl user) {
        try {
            if (user.getRole().getAuthority().equals("ROLE_ADMIN")) {
                return ResponseEntity.ok(kafkaProducer
                        .sendAndReceive("get-all-cats", "reply-all-cats", null));
            } else {
                return ResponseEntity.ok(kafkaProducer
                        .sendAndReceive("get-cats-by-owner", "reply-cats-by-owner", user.getOwnerId()));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-owner/{catId}")
    public ResponseEntity<?> getCatOwner(@PathVariable Long catId) {
        try {
            return ResponseEntity.ok(kafkaProducer
                    .sendAndReceive("get-cat-owner", "reply-cat-owner", catId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasThisCat(principal, #catId)")
    @GetMapping("/get-friends/{catId}")
    public ResponseEntity<?> getCatFriends(@PathVariable Long catId) {
        try {
            return ResponseEntity.ok(kafkaProducer
                    .sendAndReceive("get-cat-friends", "reply-cat-friends", catId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCat(@RequestBody CreateCatDto catModel) {
        try {
            return ResponseEntity
                    .ok(kafkaProducer.sendAndReceive("create-cat", "reply-create-cat", catModel));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasThisCat(principal, #catId)")
    @PutMapping("/set-name/{catId}")
    public ResponseEntity<?> setCatName(@PathVariable Long catId, String newName) {
        try {
            return ResponseEntity.ok(kafkaProducer.sendAndReceive("set-cat-name",
                    "reply-set-cat-name", new SetCatNameDto(catId, newName)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasThisCat(principal, #catId)")
    @PutMapping("/set-birthdate/{catId}")
    public ResponseEntity<?> setCatBirthdate(@PathVariable Long catId, @RequestBody BirthdateDto newBirthdate) {
        try {
            return ResponseEntity.ok(kafkaProducer.sendAndReceive("set-cat-birthdate",
                    "reply-set-cat-birthdate", new SetCatBirthdateDto(catId, newBirthdate)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasThisCat(principal, #catId)")
    @PutMapping("/set-breed/{catId}")
    public ResponseEntity<?> setCatBreed(@PathVariable Long catId, String newBreed) {
        try {
            return ResponseEntity.ok(kafkaProducer.sendAndReceive("set-cat-breed",
                    "reply-set-cat-breed", new SetCatBreedDto(catId, newBreed)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasThisCat(principal, #catId)")
    @PutMapping("/set-color/{catId}")
    public ResponseEntity<?> setCatColor(@PathVariable Long catId, String newColor) {
        try {
            return ResponseEntity.ok(kafkaProducer.sendAndReceive("set-cat-color",
                    "reply-set-cat-color", new SetCatColorDto(catId, newColor)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasThisCat(principal, #catId) and " +
            "@SecurityService.hasThisCat(principal, #friendId)")
    @PutMapping("/add-friend/{catId}")
    public ResponseEntity<?> addCatFriend(@PathVariable Long catId, Long friendId) {
        try {
            return ResponseEntity.ok(kafkaProducer.sendAndReceive("add-cat-friend",
                    "reply-add-cat-friend", new CatAndFriendDto(catId, friendId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasThisCat(principal, #catId) and " +
            "@SecurityService.hasThisCat(authentication, #friendId)")
    @PutMapping("/remove-friend/{catId}")
    public ResponseEntity<?> removeCatFriend(@PathVariable Long catId, Long friendId) {
        try {
            return ResponseEntity.ok(kafkaProducer.sendAndReceive("remove-cat-friend",
                    "reply-remove-cat-friend", new CatAndFriendDto(catId, friendId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasThisCat(principal, #catId)")
    @DeleteMapping("/delete-cat/{catId}")
    public ResponseEntity<?> deleteCat(@PathVariable Long catId) {
        try {
            return ResponseEntity
                    .ok(kafkaProducer.sendAndReceive("delete-cat", "reply-delete-cat", catId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
