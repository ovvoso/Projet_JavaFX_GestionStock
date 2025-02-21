package com.example.examenjpapersistence.Controller.login;

import com.example.examenjpapersistence.SceneManager.SceneManager;
import com.example.examenjpapersistence.model.JPAUtil;
import com.example.examenjpapersistence.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;

public class InscriptionController {
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField telephoneField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;

    @FXML
    private void handleInscription() {
        if (!validateFields()) return;
        
        User user = new User();
        user.setNom(nomField.getText().trim());
        user.setPrenom(prenomField.getText().trim());
        user.setTelephone(telephoneField.getText().trim());
        user.setEmail(emailField.getText().trim().toLowerCase());
        user.setPassword(passwordField.getText());

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            showAlert("Succès", "Inscription réussie!", Alert.AlertType.INFORMATION);
            SceneManager.changeScene("Connexion.fxml");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            showAlert("Erreur", "Erreur lors de l'inscription", Alert.AlertType.ERROR);
        } finally {
            em.close();
        }
    }

    private boolean validateFields() {
        if (nomField.getText().isEmpty() || prenomField.getText().isEmpty() ||
            telephoneField.getText().isEmpty() || emailField.getText().isEmpty() ||
            passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()) {
            showAlert("Erreur", "Tous les champs sont obligatoires", Alert.AlertType.ERROR);
            return false;
        }

        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showAlert("Erreur", "Les mots de passe ne correspondent pas", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void sceneConnexion() {
        SceneManager.changeScene("Connexion.fxml");
    }
}
