package com.example.examenjpapersistence.Controller;

import com.example.examenjpapersistence.SceneManager.SceneManager;
import com.example.examenjpapersistence.metier.CategorieService;
import com.example.examenjpapersistence.metier.ProduitService;
import com.example.examenjpapersistence.model.Categorie;
import com.example.examenjpapersistence.model.Produit;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class ProduitController extends BaseController implements Initializable {
    @FXML private ComboBox<Categorie> c_categorie;
    @FXML private TextField c_id;
    @FXML private TextField c_libelle;
    @FXML private TextField c_prixUnitaire;
    @FXML private TextField c_quantite;
    @FXML private TextField c_search;
    @FXML private TableColumn<Produit, String> t_categorie;
    @FXML private TableColumn<Produit, Integer> t_id;
    @FXML private TableColumn<Produit, String> t_libelle;
    @FXML private TableColumn<Produit, Double> t_prixUnitaire;
    @FXML private TableColumn<Produit, Integer> t_quantite;
    @FXML private TableView<Produit> table_produit;

    private ProduitService produitService;
    private CategorieService categorieService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        produitService = new ProduitService();
        categorieService = new CategorieService();
        
        // Configuration de la TableView
        t_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        t_libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        t_quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        t_prixUnitaire.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        
        // Afficher le libellé de la catégorie au lieu de l'ID
        t_categorie.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            Categorie categorie = cellData.getValue().getCategorie();
            property.setValue(categorie != null ? categorie.getLibelle() : "");
            return property;
        });

        // Configuration de la ComboBox
        c_categorie.setItems(FXCollections.observableArrayList(categorieService.getAll()));
        c_categorie.setConverter(new StringConverter<>() {
            @Override
            public String toString(Categorie c) {
                return (c != null) ? c.getLibelle() : "";
            }

            @Override
            public Categorie fromString(String s) {
                return null;
            }
        });

        refreshTable();
    }

    private void refreshTable() {
        try {
            ObservableList<Produit> produits = produitService.getAll();
            table_produit.setItems(produits);
        } catch (Exception e) {
            showError("Erreur de rafraîchissement", e.getMessage());
        }
    }

    private void clearFields() {
        c_id.clear();
        c_libelle.clear();
        c_quantite.clear();
        c_prixUnitaire.clear();
        c_categorie.setValue(null);
    }

    @FXML
    void clearChamps(ActionEvent event) {
        clearFields();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void addProduit(ActionEvent event) {
        try {
            Produit produit = new Produit();
            produit.setLibelle(c_libelle.getText());
            produit.setQuantite(Integer.parseInt(c_quantite.getText()));
            produit.setPrixUnitaire(Double.parseDouble(c_prixUnitaire.getText()));
            produit.setCategorie(c_categorie.getValue());
            produitService.addProduit(produit);
            refreshTable();
            clearFields();
        } catch (Exception e) {
            showError("Erreur d'ajout du produit", e.getMessage());
        }
    }

    @FXML
    void chargementChamps(MouseEvent event) {
        Produit p = table_produit.getSelectionModel().getSelectedItem();
        if (p != null) {
            c_id.setText(String.valueOf(p.getId()));
            c_libelle.setText(p.getLibelle());
            c_quantite.setText(String.valueOf(p.getQuantite()));
            c_prixUnitaire.setText(String.valueOf(p.getPrixUnitaire()));
            c_categorie.setValue(p.getCategorie());
        }
    }

    @FXML
    void deleteProduit(ActionEvent event) {
        try {
            Produit selectedProduit = table_produit.getSelectionModel().getSelectedItem();
            if (selectedProduit == null) {
                showError("Erreur", "Veuillez sélectionner un produit à supprimer");
                return;
            }

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation de suppression");
            confirmation.setContentText("Voulez-vous vraiment supprimer ce produit ?");
            
            if (confirmation.showAndWait().get() == ButtonType.OK) {
                produitService.deleteProduit(selectedProduit.getId());
                refreshTable();
                clearFields();
            }
        } catch (Exception e) {
            showError("Erreur de suppression", e.getMessage());
        }
    }

    @FXML
    void search(KeyEvent event) {
        try {
            String data = c_search.getText();
            ObservableList<Produit> produits = produitService.getSearch(data);
            table_produit.setItems(produits);
        } catch (Exception e) {
            showError("Erreur de recherche", e.getMessage());
        }
    }

    @FXML
    void updateProduit(ActionEvent event) {
        try {
            Produit produit = new Produit();
            produit.setId(Long.parseLong(c_id.getText()));
            produit.setLibelle(c_libelle.getText());
            produit.setQuantite(Integer.parseInt(c_quantite.getText()));
            produit.setPrixUnitaire(Double.parseDouble(c_prixUnitaire.getText()));
            produit.setCategorie(c_categorie.getValue());
            produitService.updateProduit(produit);
            refreshTable();
            clearFields();
        } catch (Exception e) {
            showError("Erreur de mise à jour du produit", e.getMessage());
        }
    }

    // Navigation methods
    @FXML void sceneDashboard(MouseEvent event) {
        SceneManager.changeScene("Dashboard.fxml");
    }

    @FXML
    void sceneCategorie(MouseEvent event) {
        SceneManager.changeScene("Categorie.fxml");
    }

    @FXML
    void sceneProduit(MouseEvent event) {
        SceneManager.changeScene("Produit.fxml");
    }

    @FXML
    void sceneStatistique(MouseEvent event) {
        SceneManager.changeScene("Statistique.fxml");
    }

    @FXML
    void sceneExtraireDocument(MouseEvent event) {
        SceneManager.changeScene("ExtraireDocument.fxml");
    }
}
