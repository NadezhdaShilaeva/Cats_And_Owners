package com.shilaeva.dao;

import com.shilaeva.models.Owner;

import java.util.List;

public interface OwnerDao {
    Owner findById(long id);

    List<Owner> findAll();

    void save(Owner owner);

    Owner update(Owner owner);

    void delete(Owner owner);
}
