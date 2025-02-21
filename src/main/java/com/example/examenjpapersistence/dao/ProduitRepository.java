package com.example.examenjpapersistence.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.examenjpapersistence.model.JPAUtil;
import com.example.examenjpapersistence.model.Produit;
import com.example.examenjpapersistence.model.Categorie;  // Import de la classe Categorie

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.SQLException;

public class ProduitRepository {

    private EntityManager entityManager;

    public ProduitRepository() {
        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }


    public ObservableList<Produit> getAll() throws SQLException {
        ObservableList<Produit> list = FXCollections.observableArrayList();

        try {

            entityManager.getTransaction().begin();

            String jpql = "SELECT p FROM Produit p";
            TypedQuery<Produit> query = entityManager.createQuery(jpql, Produit.class);
            list.addAll(query.getResultList());
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list;
    }


    public void add(Produit produit) throws SQLException {
        try {

            entityManager.getTransaction().begin();

            entityManager.persist(produit);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Produit produit) throws SQLException {
        try {

            entityManager.getTransaction().begin();

            Produit p = entityManager.find(Produit.class, produit.getId());
            p.setLibelle(produit.getLibelle());
            p.setQuantite(produit.getQuantite());
            p.setPrixUnitaire(produit.getPrixUnitaire());
            p.setCategorie(produit.getCategorie());
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int id) throws SQLException {
        try {

            entityManager.getTransaction().begin();

            Produit p = entityManager.find(Produit.class, id);
            entityManager.remove(p);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ObservableList<Produit> getSearch(String data) throws SQLException {
        ObservableList<Produit> list = FXCollections.observableArrayList();

        try {

            entityManager.getTransaction().begin();

            String jpql = "SELECT p FROM Produit p WHERE p.libelle LIKE :data OR p.categorie.libelle LIKE :data";
            TypedQuery<Produit> query = entityManager.createQuery(jpql, Produit.class);
            query.setParameter("data", "%" + data + "%");

            list.addAll(query.getResultList());
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}
