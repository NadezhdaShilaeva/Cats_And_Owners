package com.shilaeva.repositories;

import com.shilaeva.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    List<Owner> findByName(String ownerName);
    List<Owner> findByBirthdate(Date ownerBirthdate);
}
