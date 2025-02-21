package com.example.examenjpapersistence.metier;

import javafx.collections.ObservableList;
import com.example.examenjpapersistence.dao.CategorieRepository;
import com.example.examenjpapersistence.model.Categorie;
import java.sql.SQLException;

public class CategorieService {

    public final CategorieRepository categorieRepository;

    public CategorieService() {
        this.categorieRepository = new CategorieRepository();
    }

    // Méthode pour ajouter une catégorie
    public void addCategorie(Categorie categorie) {
        try {
            categorieRepository.add(categorie);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur d'ajout d'une catégorie", e);
        }
    }

    // Méthode pour mettre à jour une catégorie
    public void updateCategorie(Categorie categorie) {
        try {
            categorieRepository.update(categorie);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur de mise à jour d'une catégorie", e);
        }
    }

    // Méthode pour récupérer toutes les catégories
    public ObservableList<Categorie> getAll() {
        try {
            return categorieRepository.getAll();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur de récupération des catégories", e);
        }
    }

    // Méthode pour supprimer une catégorie par ID
    public void deleteCategorie(int id) {
        try {
            categorieRepository.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur de suppression d'une catégorie", e);
        }
    }

    // Méthode pour rechercher des catégories par libellé
    public ObservableList<Categorie> getSearch(String data) {
        try {
            return categorieRepository.getSearch(data);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur de récupération des catégories", e);
        }
    }


    //=======================================


}
