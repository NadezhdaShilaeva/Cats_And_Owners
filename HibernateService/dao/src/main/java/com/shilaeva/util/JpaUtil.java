package com.shilaeva.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static EntityManager entityManager;

    private JpaUtil() {
    }

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory("lab2");
            entityManager = sessionFactory.createEntityManager();
        }

        return entityManager;
    }
}