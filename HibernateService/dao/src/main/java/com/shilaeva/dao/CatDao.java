package com.shilaeva.dao;

import com.shilaeva.models.Cat;

import java.util.List;

public interface CatDao {
    Cat findById(long id);

    List<Cat> findAll();

    void save(Cat cat);

    Cat update(Cat cat);

    void delete(Cat cat);
}
