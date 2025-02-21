package com.example.examenjpapersistence;

import com.example.examenjpapersistence.model.Categorie;
import com.example.examenjpapersistence.model.Produit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        // Créer une EntityManagerFactory à partir du fichier persistence.xml
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PERSISTENCE");

        // Créer un EntityManager
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // Début de transaction
        entityManager.getTransaction().begin();

        // Création et insertion d'une catégorie
        Categorie categorie = new Categorie();
        categorie.setLibelle("Cosmetique");
        entityManager.persist(categorie); // Persiste la catégorie

        // Création et insertion d'un produit lié à la catégorie
        Produit produit = new Produit();
        produit.setLibelle("Parfum");
        produit.setQuantite(3);
        produit.setPrixUnitaire(750.00); // Prix en double
        produit.setCategorie(categorie); // Liaison avec la catégorie

        entityManager.persist(produit); // Persiste le produit

        // Commit et fermeture
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();

        System.out.println("Catégorie et produit ajoutés avec succès !");
    }
}
