package com.shilaeva.dao;

import com.shilaeva.models.Cat;
import com.shilaeva.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class CatDaoImpl implements CatDao {
    public Cat findById(long catId) {
        return JpaUtil.getEntityManager().find(Cat.class, catId);
    }

    public List<Cat> findAll() {
        EntityManager em = JpaUtil.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cat> cq = cb.createQuery(Cat.class);
        Root<Cat> rootEntry = cq.from(Cat.class);
        CriteriaQuery<Cat> all = cq.select(rootEntry);
        TypedQuery<Cat> allQuery = em.createQuery(all);

        return allQuery.getResultList();
    }

    public void save(Cat cat) {
        JpaUtil.getEntityManager().getTransaction().begin();
        JpaUtil.getEntityManager().persist(cat);
        JpaUtil.getEntityManager().getTransaction().commit();
    }

    public Cat update(Cat cat) {
        JpaUtil.getEntityManager().getTransaction().begin();
        Cat updatedCat = JpaUtil.getEntityManager().merge(cat);
        JpaUtil.getEntityManager().getTransaction().commit();

        return updatedCat;
    }

    public void delete(Cat cat) {
        JpaUtil.getEntityManager().getTransaction().begin();
        JpaUtil.getEntityManager().remove(cat);
        JpaUtil.getEntityManager().getTransaction().commit();
    }
}