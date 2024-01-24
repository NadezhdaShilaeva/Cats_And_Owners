package com.shilaeva.controllers;

import com.shilaeva.models.BirthdateModel;
import com.shilaeva.models.CreateCatModel;
import com.shilaeva.security.UserDetailsImpl;
import com.shilaeva.services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/api/cats")
public class CatController {
    @Autowired
    private CatService catService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasThisCat(principal, #id)")
    @GetMapping("/get-by-id")
    public ResponseEntity<?> getCatById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(catService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<?> getCatsByName(@AuthenticationPrincipal UserDetailsImpl user, @RequestParam String name) {
        try {
            if (user.getRole().getAuthority().equals("ROLE_ADMIN")) {
                return ResponseEntity.ok(catService.getByName(name));
            }
            else {
                return ResponseEntity.ok(catService.getByNameAndOwner(name, user.getOwnerId()));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-by-birthdate")
    public ResponseEntity<?> getCatsByBirthdate(@AuthenticationPrincipal UserDetailsImpl user, @RequestBody BirthdateModel birthdate) {
        try {
            if (user.getRole().getAuthority().equals("ROLE_ADMIN")) {
                return ResponseEntity.ok(catService.getByBirthdate(getDateFromBirthdateModel(birthdate)));
            }
            else {
                return ResponseEntity.ok(catService.getByBirthdateAndOwner(getDateFromBirthdateModel(birthdate),
                        user.getOwnerId()));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-by-breed")
    public ResponseEntity<?> getCatsByBreed(@AuthenticationPrincipal UserDetailsImpl user, @RequestParam String breed) {
        try {
            if (user.getRole().getAuthority().equals("ROLE_ADMIN")) {
                return ResponseEntity.ok(catService.getByBreed(breed));
            }
            else {
                return ResponseEntity.ok(catService.getByBreedAndOwner(breed, user.getOwnerId()));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-by-color")
    public ResponseEntity<?> getCatsByColor(@AuthenticationPrincipal UserDetailsImpl user, @RequestParam String color) {
        try {
            if (user.getRole().getAuthority().equals("ROLE_ADMIN")) {
                return ResponseEntity.ok(catService.getByColor(color));
            }
            else {
                return ResponseEntity.ok(catService.getByColorAndOwner(color, user.getOwnerId()));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasOwnerId(principal, #ownerId)")
    @GetMapping("/get-by-owner")
    public ResponseEntity<?> getCatsByOwner(@RequestParam Long ownerId) {
        try {
            return ResponseEntity.ok(catService.getByOwner(ownerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCats(@AuthenticationPrincipal UserDetailsImpl user) {
        try {
            if (user.getRole().getAuthority().equals("ROLE_ADMIN")) {
                return ResponseEntity.ok(catService.getAll());
            }
            else {
                return ResponseEntity.ok(catService.getByOwner(user.getOwnerId()));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-owner/{catId}")
    public ResponseEntity<?> getCatOwner(@PathVariable Long catId) {
        try {
            return ResponseEntity.ok(catService.getOwner(catId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasThisCat(principal, #catId)")
    @GetMapping("/get-friends/{catId}")
    public ResponseEntity<?> getCatFriends(@PathVariable Long catId) {
        try {
            return ResponseEntity.ok(catService.getFriends(catId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCat(@RequestBody CreateCatModel catModel) {
        try {
            return ResponseEntity.ok(catService.createCat(catModel.name(),
                    getDateFromBirthdateModel(catModel.birthdate()), catModel.breed(), catModel.color()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasThisCat(principal, #catId)")
    @PutMapping("/set-name/{catId}")
    public ResponseEntity<?> setCatName(@PathVariable Long catId, String newName) {
        try {
            return ResponseEntity.ok(catService.setName(catId, newName));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasThisCat(principal, #catId)")
    @PutMapping("/set-birthdate/{catId}")
    public ResponseEntity<?> setCatBirthdate(@PathVariable Long catId, @RequestBody BirthdateModel newBirthdate) {
        try {
            return ResponseEntity.ok(catService.setBirthdate(catId, getDateFromBirthdateModel(newBirthdate)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasThisCat(principal, #catId)")
    @PutMapping("/set-breed/{catId}")
    public ResponseEntity<?> setCatBreed(@PathVariable Long catId, String newBreed) {
        try {
            return ResponseEntity.ok(catService.setBreed(catId, newBreed));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasThisCat(principal, #catId)")
    @PutMapping("/set-color/{catId}")
    public ResponseEntity<?> setCatColor(@PathVariable Long catId, String newColor) {
        try {
            return ResponseEntity.ok(catService.setColor(catId, newColor));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasThisCat(principal, #catId) and " +
            "@SecurityService.hasThisCat(principal, #friendId)")
    @PutMapping("/add-friend/{catId}")
    public ResponseEntity<?> addCatFriend(@PathVariable Long catId, Long friendId) {
        try {
            catService.addFriend(catId, friendId);

            return ResponseEntity
                    .ok("Cat with id " + catId + " was added to the friends of the cat with id " + friendId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasThisCat(principal, #catId) and " +
            "@SecurityService.hasThisCat(authentication, #friendId)")
    @PutMapping("/remove-friend/{catId}")
    public ResponseEntity<?> removeCatFriend(@PathVariable Long catId, Long friendId) {
        try {
            catService.removeFriend(catId, friendId);

            return ResponseEntity
                    .ok("Cat with id " + catId + " was removed from friends of the cat with id " + friendId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasThisCat(principal, #catId)")
    @DeleteMapping("/delete-cat/{catId}")
    public ResponseEntity<?> deleteCat(@PathVariable Long catId) {
        try {
            catService.deleteCat(catId);

            return ResponseEntity.ok("Cat with id " + catId + " was deleted.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Date getDateFromBirthdateModel(BirthdateModel birthdate) {
        Calendar catBirthdate = new Calendar.Builder()
                .set(Calendar.YEAR, birthdate.year())
                .set(Calendar.MONTH, birthdate.month() - 1)
                .set(Calendar.DATE, birthdate.day())
                .build();

        return catBirthdate.getTime();
    }
}
