package com.shilaeva.services.implementations;

import com.shilaeva.dto.CatDto;
import com.shilaeva.dto.OwnerDto;
import com.shilaeva.exceptions.CatException;
import com.shilaeva.exceptions.OwnerException;
import com.shilaeva.models.Cat;
import com.shilaeva.models.Owner;
import com.shilaeva.repositories.CatRepository;
import com.shilaeva.repositories.OwnerRepository;
import com.shilaeva.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private CatRepository catRepository;

    public OwnerDto getById(Long ownerId) {
        return OwnerDto.asDto(getOwnerById(ownerId));
    }

    public List<OwnerDto> getByName(String ownerName) {
        List<Owner> owners = ownerRepository.findByName(ownerName);
        if (owners == null || owners.isEmpty()) {
            throw OwnerException.ownersNotFoundByName(ownerName);
        }

        return owners.stream().map(OwnerDto::asDto).toList();
    }

    public List<OwnerDto> getByBirthdate(Date ownerBirthdate) {
        List<Owner> owners = ownerRepository.findByBirthdate(ownerBirthdate);
        if (owners == null || owners.isEmpty()) {
            throw OwnerException.ownersNotFoundByBirthdate(ownerBirthdate);
        }

        return owners.stream().map(OwnerDto::asDto).toList();
    }

    public List<OwnerDto> getAll() {
        List<Owner> owners = ownerRepository.findAll();
        if (owners.isEmpty()) {
            throw OwnerException.ownersNotFound();
        }

        return owners.stream().map(OwnerDto::asDto).toList();
    }

    public List<CatDto> getCats(Long ownerId) {
        Owner owner = getOwnerById(ownerId);
        if (owner.getCats().isEmpty()) {
            throw OwnerException.catsNotFound(owner);
        }

        return owner.getCats().stream().map(CatDto::asDto).toList();
    }

    public OwnerDto createOwner(String name, Date birthdate) {
        Owner newOwner = new Owner(name, birthdate);

        return OwnerDto.asDto(ownerRepository.save(newOwner));
    }

    public OwnerDto setName(Long ownerId, String newName) {
        Owner owner = getOwnerById(ownerId);
        owner.setName(newName);

        return OwnerDto.asDto(ownerRepository.save(owner));
    }

    public OwnerDto setBirthdate(Long ownerId, Date newBirthdate) {
        Owner owner = getOwnerById(ownerId);
        owner.setBirthdate(newBirthdate);

        return OwnerDto.asDto(ownerRepository.save(owner));
    }

    public void addCat(Long ownerId, Long catId) {
        Owner owner = getOwnerById(ownerId);
        Cat cat = getCatById(catId);

        if (owner.getCats().contains(cat)) {
            throw OwnerException.catIsAlreadyExist(cat, owner);
        }

        owner.getCats().add(cat);
        cat.setOwner(owner);

        ownerRepository.save(owner);
        catRepository.save(cat);
    }

    public void removeCat(Long ownerId, Long catId) {
        Owner owner = getOwnerById(ownerId);
        Cat cat = getCatById(catId);

        if (!owner.getCats().remove(cat)) {
            throw OwnerException.catNotRemoved(cat);
        }

        cat.setOwner(null);

        ownerRepository.save(owner);
        catRepository.save(cat);
    }

    public void deleteOwner(Long ownerId) {
        ownerRepository.deleteById(ownerId);
    }

    private Owner getOwnerById(Long ownerId) {
        Optional<Owner> owner = ownerRepository.findById(ownerId);
        if (owner.isEmpty()) {
            throw OwnerException.ownerNotExists(ownerId);
        }

        return owner.get();
    }

    private Cat getCatById(Long catId) {
        Optional<Cat> cat = catRepository.findById(catId);
        if (cat.isEmpty()) {
            throw CatException.catNotExists(catId);
        }

        return cat.get();
    }
}
