package com.example.examenjpapersistence.Controller;

import com.example.examenjpapersistence.SceneManager.SceneManager;
import javafx.fxml.FXML;

public class DashboardController extends BaseController {

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
