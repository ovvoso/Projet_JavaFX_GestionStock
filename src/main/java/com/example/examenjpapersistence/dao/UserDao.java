package com.example.examenjpapersistence.dao;

import com.example.examenjpapersistence.model.JPAUtil;
import com.example.examenjpapersistence.model.User;
import javax.persistence.EntityManager;  // Retour à javax.persistence
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class UserDao {
    public void save(User user) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    public User findByEmailAndPassword(String email, String password) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password");
            query.setParameter("email", email);
            query.setParameter("password", password);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
