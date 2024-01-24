package com.shilaeva.services.implementations;

import com.shilaeva.dao.CatDao;
import com.shilaeva.dao.OwnerDao;
import com.shilaeva.dto.CatDto;
import com.shilaeva.dto.OwnerDto;
import com.shilaeva.exceptions.OwnerException;
import com.shilaeva.mapping.CatMapping;
import com.shilaeva.mapping.OwnerMapping;
import com.shilaeva.models.Cat;
import com.shilaeva.models.Owner;
import com.shilaeva.services.OwnerService;

import java.util.Date;
import java.util.List;

public class OwnerServiceImpl implements OwnerService {
    private final OwnerDao ownerDao;
    private final CatDao catDao;

    public OwnerServiceImpl(OwnerDao ownerDao, CatDao catDao) {
        this.ownerDao = ownerDao;
        this.catDao = catDao;
    }

    public OwnerDto findById(long ownerId) {
        return OwnerMapping.asDto(ownerDao.findById(ownerId));
    }

    public List<OwnerDto> findAll() {
        return ownerDao.findAll().stream().map(OwnerMapping::asDto).toList();
    }

    public List<CatDto> findCats(long ownerId) {
        return ownerDao.findById(ownerId).getCats().stream().map(CatMapping::asDto).toList();
    }

    public OwnerDto createOwner(String name, Date birthdate) {
        Owner newOwner = new Owner(name, birthdate);
        ownerDao.save(newOwner);

        return OwnerMapping.asDto(newOwner);
    }

    public OwnerDto setName(long ownerId, String newName) {
        Owner owner = ownerDao.findById(ownerId);
        owner.setName(newName);

        return OwnerMapping.asDto(ownerDao.update(owner));
    }

    public OwnerDto setBirthdate(long ownerId, Date newBirthdate) {
        Owner owner = ownerDao.findById(ownerId);
        owner.setBirthdate(newBirthdate);

        return OwnerMapping.asDto(ownerDao.update(owner));
    }

    public void addCat(long ownerId, long catId) {
        Owner owner = ownerDao.findById(ownerId);
        Cat cat = catDao.findById(catId);

        if (owner.getCats().contains(cat)) {
            throw OwnerException.catIsAlreadyExist(cat, owner);
        }

        owner.getCats().add(cat);
        cat.setOwner(owner);

        ownerDao.update(owner);
        catDao.update(cat);
    }

    public void removeCat(long ownerId, long catId) {
        Owner owner = ownerDao.findById(ownerId);
        Cat cat = catDao.findById(catId);

        if (!owner.getCats().remove(cat)) {
            throw OwnerException.catNotRemoved(cat);
        }

        cat.setOwner(null);

        ownerDao.update(owner);
        catDao.update(cat);
    }

    public void deleteOwner(long ownerId) {
        Owner owner = ownerDao.findById(ownerId);
        ownerDao.delete(owner);
    }
}
