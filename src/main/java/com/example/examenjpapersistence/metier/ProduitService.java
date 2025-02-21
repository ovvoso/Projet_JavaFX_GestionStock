package com.example.examenjpapersistence.metier;

import javafx.collections.ObservableList;
import com.example.examenjpapersistence.dao.ProduitRepository;
import com.example.examenjpapersistence.model.Produit;
import com.example.examenjpapersistence.model.JPAUtil;  // Ajout de l'import
import java.sql.SQLException;
import com.example.examenjpapersistence.ExceptionError.ProduitNotFoundException;

import javax.persistence.EntityManager;

public class ProduitService {

    public final ProduitRepository produitRepository;

    public ProduitService() {
        this.produitRepository = new ProduitRepository();
    }

    // Méthode pour ajouter un produit
    public void addProduit(Produit produit) {
        try {
            produitRepository.add(produit);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur d'ajout d'un produit", e);
        }
    }

    // Méthode pour mettre à jour un produit
    public void updateProduit(Produit produit) {
        try {
            produitRepository.update(produit);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur de mise à jour d'un produit", e);
        }
    }

    // Méthode pour récupérer tous les produits
    public ObservableList<Produit> getAll() {
        try {
            return produitRepository.getAll();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur de récupération des produits", e);
        }
    }

    // Méthode pour supprimer un produit par ID
    public void deleteProduit(Long id) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            
            Produit produit = em.find(Produit.class, id);
            if (produit != null) {
                em.remove(produit);
            }
            
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Méthode pour rechercher des produits par libellé
    public ObservableList<Produit> getSearch(String data) {
        try {
            return produitRepository.getSearch(data);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur de récupération des produits", e);
        }
    }
}
