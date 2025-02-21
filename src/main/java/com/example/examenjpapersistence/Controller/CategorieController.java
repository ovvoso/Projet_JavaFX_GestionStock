package com.example.examenjpapersistence.Controller;

import com.example.examenjpapersistence.SceneManager.SceneManager;
import com.example.examenjpapersistence.metier.CategorieService;
import com.example.examenjpapersistence.model.Categorie;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;

public class CategorieController extends BaseController implements Initializable {

    @FXML
    private TextField c_libelle;

    @FXML
    private TextField c_search;

    @FXML
    private TextField c_id;


    @FXML
    private TableColumn<Categorie, Integer> t_id;

    @FXML
    private TableColumn<Categorie, String> t_libelle;

    @FXML
    private TableView<Categorie> table_categorie;


    private CategorieService categorieService;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.categorieService = new CategorieService();
        refreshTable();

    }

    private void refreshTable() {
        try {
            ObservableList<Categorie> categories = categorieService.getAll();
            defineColonne();
            table_categorie.setItems(categories);

        } catch (Exception e){
            showError("erreur de rafraichissement de la table", e.getMessage());
        }
    }

    private void clearFields() {
        c_id.clear();
        c_libelle.clear();
    }

    @FXML
    void clearChamps(ActionEvent event) {
        clearFields();
    }

    private void defineColonne(){
        t_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        t_libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    void addCategorie(ActionEvent event) {
        try {
            Categorie categorie = new Categorie();
            categorie.setLibelle(c_libelle.getText());

            categorieService.addCategorie(categorie);

            refreshTable();
            clearFields();
        }catch (Exception e){
            showError("Erreur d'ajout", e.getMessage());
        }
    }

    @FXML
    void chargementChamps(MouseEvent event) {
        Categorie c = table_categorie.getSelectionModel().getSelectedItem();
        if(c != null){
            c_id.setText(valueOf(c.getId()));
            c_libelle.setText(c.getLibelle());
        }
    }


    @FXML
    void deleteCategorie(ActionEvent event) {
        try {
            int id = Integer.parseInt(c_id.getText());
            categorieService.deleteCategorie(id);
            refreshTable();
            clearFields();

        } catch (Exception e){
            showError("Erreur de suppression", e.getMessage());
        }
    }

    @FXML
    void search(KeyEvent event) {
        try {
            String data = c_search.getText();
            ObservableList<Categorie> categories = categorieService.getSearch(data);
            defineColonne();
            table_categorie.setItems(categories);

        }catch (Exception e){
            showError("Erreur de recherche", e.getMessage());
        }
    }

    @FXML
    void updateCategorie(ActionEvent event) {
        try {
            Categorie categorie = new Categorie();
            categorie.setId(Integer.parseInt(c_id.getText()));
            categorie.setLibelle(c_libelle.getText());

            categorieService.updateCategorie(categorie);
            refreshTable();
            clearFields();

        }catch (Exception e){
            showError("Erreur de mise à jour", e.getMessage());
        }
    }






    //========================================= REDIRECTION ENTRE LES PAGES ===============================================

    @FXML
    private void sceneDashboard() {
        SceneManager.changeScene("Dashboard.fxml");
    }

    @FXML
    private void sceneCategorie(){
        SceneManager.changeScene("Categorie.fxml");
    }

    @FXML
    private void sceneProduit(){
        SceneManager.changeScene("Produit.fxml");
    }

    @FXML
    private void sceneStatistique(){
        SceneManager.changeScene("Statistique.fxml");
    }

    @FXML
    private void sceneExtraireDocument(){
        SceneManager.changeScene("ExtraireDocument.fxml");
    }




}
