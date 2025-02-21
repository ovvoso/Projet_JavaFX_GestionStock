package com.example.examenjpapersistence.Controller.login;

import com.example.examenjpapersistence.SceneManager.SceneManager;
import com.example.examenjpapersistence.model.JPAUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class ConnexionController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    private void sceneDashboard() {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password");
            query.setParameter("email", email);
            query.setParameter("password", password);
            query.getSingleResult();
            // Si on arrive ici, l'utilisateur existe
            SceneManager.changeScene("Dashboard.fxml");
        } catch (NoResultException e) {
            showAlert("Erreur", "Email ou mot de passe incorrect", Alert.AlertType.ERROR);
        } finally {
            em.close();
        }
    }

    @FXML
    private void sceneInscription() {
        SceneManager.changeScene("inscription.fxml");
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
