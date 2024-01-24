package com.shilaeva.repositories;

import com.shilaeva.models.Cat;
import com.shilaeva.models.CatColor;
import com.shilaeva.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    List<Cat> findByName(String catName);
    List<Cat> findByNameAndOwner(String catName, Owner catOwner);
    List<Cat> findByBirthdate(Date catBirthdate);
    List<Cat> findByBirthdateAndOwner(Date catBirthdate, Owner catOwner);
    List<Cat> findByBreed(String catBreed);
    List<Cat> findByBreedAndOwner(String catBreed, Owner owner);
    List<Cat> findByColor(CatColor catColor);
    List<Cat> findByColorAndOwner(CatColor catColor, Owner catOwner);
    List<Cat> findByOwner(Owner catOwner);
}
