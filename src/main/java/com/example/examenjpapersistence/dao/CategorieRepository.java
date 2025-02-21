package com.example.examenjpapersistence.dao;

import com.example.examenjpapersistence.model.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.examenjpapersistence.model.JPAUtil;
import com.example.examenjpapersistence.model.Categorie;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.SQLException;

public class CategorieRepository {

    private EntityManager entityManager;

    public CategorieRepository() {
        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }

    //lister
    public ObservableList<Categorie> getAll() throws SQLException {
        ObservableList<Categorie> list = FXCollections.observableArrayList();

        try {

            entityManager.getTransaction().begin();
            String jpql = "SELECT c FROM Categorie c";
            TypedQuery<Categorie> query = entityManager.createQuery(jpql, Categorie.class);
            list.addAll(query.getResultList());
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;
    }



    //ajouter
    public void add(Categorie categorie) throws SQLException {
        try {
            if (!entityManager.isOpen()) {
                entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            }
            entityManager.getTransaction().begin();

            entityManager.persist(categorie);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public void update(Categorie categorie) throws SQLException {
        try {
            if (!entityManager.isOpen()) {
                entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            }
            entityManager.getTransaction().begin();

            Categorie c = entityManager.find(Categorie.class, categorie.getId());
            c.setLibelle(categorie.getLibelle());  // Mise à jour du libelle
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public void delete(int id) throws SQLException {
        try {
            if (!entityManager.isOpen()) {
                entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            }
            entityManager.getTransaction().begin();

            Categorie c = entityManager.find(Categorie.class, id);
            entityManager.remove(c);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    public ObservableList<Categorie> getSearch(String data) throws SQLException {
        ObservableList<Categorie> list = FXCollections.observableArrayList();

        try {
            if (!entityManager.isOpen()) {
                entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            }
            entityManager.getTransaction().begin();

            String jpql = "SELECT c FROM Categorie c WHERE c.libelle LIKE :data";
            TypedQuery<Categorie> query = entityManager.createQuery(jpql, Categorie.class);

            query.setParameter("data", "%" + data + "%");

            list.addAll(query.getResultList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }

        return list;
    }

    public ObservableList<Produit> getProduitsByCategorie(int categorieId) throws SQLException {
        ObservableList<Produit> list = FXCollections.observableArrayList();

        try {
            if (!entityManager.isOpen()) {
                entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            }
            entityManager.getTransaction().begin();

            String jpql = "SELECT p FROM Produit p WHERE p.categorie.id = :categorieId";
            TypedQuery<Produit> query = entityManager.createQuery(jpql, Produit.class);
            query.setParameter("categorieId", categorieId);

            list.addAll(query.getResultList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }

        return list;
    }








}
