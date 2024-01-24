package com.shilaeva.controllers;

import com.shilaeva.models.BirthdateModel;
import com.shilaeva.models.CreateOwnerModel;
import com.shilaeva.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    @Autowired
    OwnerService ownerService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasOwnerId(principal, #id)")
    @GetMapping("/get-by-id")
    public ResponseEntity<?> getOwnerById(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(ownerService.getById(id));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-by-name")
    public ResponseEntity<?> getOwnersByName(@RequestParam String name) {
        try {
            return ResponseEntity.ok(ownerService.getByName(name));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-by-birthdate")
    public ResponseEntity<?> getOwnersByBirthdate(@RequestBody BirthdateModel birthdate) {
        try {
            return ResponseEntity.ok(ownerService.getByBirthdate(getDateFromBirthdateModel(birthdate)));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllOwners() {
        try {
            return ResponseEntity.ok(ownerService.getAll());
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasOwnerId(principal, #ownerId)")
    @GetMapping("/get-cats/{ownerId}")
    public ResponseEntity<?> getOwnerCats(@PathVariable Long ownerId) {
        try {
            return ResponseEntity.ok(ownerService.getCats(ownerId));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createOwner(@RequestBody CreateOwnerModel ownerModel) {
        try {
            return ResponseEntity
                    .ok(ownerService.createOwner(ownerModel.name(), getDateFromBirthdateModel(ownerModel.birthdate())));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasOwnerId(principal, #ownerId)")
    @PutMapping("/set-name/{ownerId}")
    public ResponseEntity<?> setOwnerName(@PathVariable Long ownerId, String newName) {
        try {
            return ResponseEntity.ok(ownerService.setName(ownerId, newName));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasOwnerId(principal, #ownerId)")
    @PutMapping("/set-birthdate/{ownerId}")
    public ResponseEntity<?> setOwnerBirthdate(@PathVariable Long ownerId, @RequestBody BirthdateModel newBirthdate) {
        try {
            return ResponseEntity.ok(ownerService.setBirthdate(ownerId, getDateFromBirthdateModel(newBirthdate)));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasOwnerId(principal, #ownerId)")
    @PutMapping("/add-cat/{ownerId}")
    public ResponseEntity<?> addCat(@PathVariable Long ownerId, Long catId) {
        try {
            ownerService.addCat(ownerId, catId);

            return ResponseEntity.ok("Cat with id " + catId + " was added to the owner with id " + ownerId);
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @SecurityService.hasOwnerId(principal, #ownerId)")
    @PutMapping("/remove-cat/{ownerId}")
    public ResponseEntity<?> removeCat(@PathVariable Long ownerId, Long catId) {
        try {
            ownerService.removeCat(ownerId, catId);

            return ResponseEntity.ok("Cat with id " + catId + " was removed from the owner with id " + ownerId);
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{ownerId}")
    public ResponseEntity<?> deleteOwner(@PathVariable Long ownerId) {
        try {
            ownerService.deleteOwner(ownerId);

            return ResponseEntity.ok("Owner with id " + ownerId + " was deleted.");
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
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
