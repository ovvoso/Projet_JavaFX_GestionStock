package com.example.examenjpapersistence.SceneManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {
    /*
     * Permet de gérer la logique des différentes scenes (pages) de l'application
     * */

    private static Stage primaryStage;

    //initialisation du stage principal
    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    //Changer la scne dynamiquement en fonction des fichiers FXML
    public static void changeScene(String fxmlFile){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneManager.class.getResource("/com/example/examenjpapersistence/Scene/" + fxmlFile))); //le Dossier : "/Scene" contient tout les fichiers fxml.

            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
