package com.shilaeva.dao;

import com.shilaeva.models.Owner;
import com.shilaeva.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class OwnerDaoImpl implements OwnerDao {
    public Owner findById(long ownerId) {
        return JpaUtil.getEntityManager().find(Owner.class, ownerId);
    }

    public List<Owner> findAll() {
        EntityManager em = JpaUtil.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Owner> cq = cb.createQuery(Owner.class);
        Root<Owner> rootEntry = cq.from(Owner.class);
        CriteriaQuery<Owner> all = cq.select(rootEntry);
        TypedQuery<Owner> allQuery = em.createQuery(all);

        return allQuery.getResultList();
    }

    public void save(Owner owner) {
        JpaUtil.getEntityManager().getTransaction().begin();
        JpaUtil.getEntityManager().persist(owner);
        JpaUtil.getEntityManager().getTransaction().commit();
    }

    public Owner update(Owner owner) {
        JpaUtil.getEntityManager().getTransaction().begin();
        Owner updatedOwner = JpaUtil.getEntityManager().merge(owner);
        JpaUtil.getEntityManager().getTransaction().commit();

        return updatedOwner;
    }

    public void delete(Owner owner) {
        JpaUtil.getEntityManager().getTransaction().begin();
        JpaUtil.getEntityManager().remove(owner);
        JpaUtil.getEntityManager().getTransaction().commit();
    }
}
