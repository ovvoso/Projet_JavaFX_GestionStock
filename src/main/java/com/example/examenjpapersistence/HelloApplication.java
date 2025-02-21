package com.example.examenjpapersistence;

import com.example.examenjpapersistence.SceneManager.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
/*
 * Configuration SceneManager
 * */
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        // definir le stage principale pour SceneManager
        SceneManager.setStage(stage);

        stage.setResizable(false);

        //lancer l'appli sur la page principale
        SceneManager.changeScene("Connexion.fxml");

        //titre
        stage.setTitle("Management Product");
    }

    public static void main(String[] args) {
        launch();
    }
}