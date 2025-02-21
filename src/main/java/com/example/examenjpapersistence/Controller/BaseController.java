package com.example.examenjpapersistence.Controller;

import com.example.examenjpapersistence.SceneManager.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class BaseController {
    @FXML
    protected void handleDeconnexion() {
        SceneManager.changeScene("Connexion.fxml");
    }
}
