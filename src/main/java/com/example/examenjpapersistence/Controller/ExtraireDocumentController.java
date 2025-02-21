package com.example.examenjpapersistence.Controller;

import com.example.examenjpapersistence.SceneManager.SceneManager;
import com.example.examenjpapersistence.dao.ProduitRepository;
import com.example.examenjpapersistence.model.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.FileOutputStream;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExtraireDocumentController extends BaseController {

    private static final String PDF_EXPORT_PATH = "C:/Projet-Java/EXAMEN-jpa-Persistence/exports/pdf/produits.pdf";
    private static final String EXCEL_EXPORT_PATH = "C:/Projet-Java/EXAMEN-jpa-Persistence/exports/excel/produits.xlsx";
    
    private ProduitRepository produitRepository;

    // Constructeur
    public ExtraireDocumentController() {
        this.produitRepository = new ProduitRepository();
    }

    // Méthode pour extraire la liste des produits sous format PDF
    @FXML
    public void extractPDF(ActionEvent event) {
        try {
            // Créer le dossier s'il n'existe pas
            new File(PDF_EXPORT_PATH).getParentFile().mkdirs();
            
            List<Produit> produits = produitRepository.getAll();
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(PDF_EXPORT_PATH));

            document.open();
            document.add(new Paragraph("===================================================="));
            document.add(new Paragraph("        EXTRACTION DE LA LISTE DES PRODUITS         "));
            document.add(new Paragraph("===================================================="));
            document.add(new Paragraph("\n\n"));

            // Ajouter l'en-tête du tableau
            document.add(new Paragraph("--------------------------------------------------------------------------"));
            document.add(new Paragraph("|            Nom du Produit              |            Catégorie           "));
            document.add(new Paragraph("--------------------------------------------------------------------------"));

            // Ajouter les produits sous forme de tableau
            for (Produit produit : produits) {
                String ligne = String.format("|  %-40s |  %-50s ",
                        produit.getLibelle(),
                        produit.getCategorie().getLibelle());
                document.add(new Paragraph(ligne));
            }

            // Fin du tableau
            document.add(new Paragraph("--------------------------------------------------------------------------"));

            document.close();

            showAlert("Succès", "Document PDF créé dans : " + PDF_EXPORT_PATH, AlertType.INFORMATION);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors de la création du PDF : " + e.getMessage(), AlertType.ERROR);
        }
    }

    // Méthode pour extraire la liste des produits par catégorie sous format Excel
    @FXML
    public void extractExcel(ActionEvent event) {
        try {
            // Créer le dossier s'il n'existe pas
            new File(EXCEL_EXPORT_PATH).getParentFile().mkdirs();
            
            List<Produit> produits = produitRepository.getAll();
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Produits par catégorie");

            // Créer l'en-tête
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Catégorie");
            headerRow.createCell(1).setCellValue("Nombre de produits");

            // Remplir les données de l'Excel
            int rowNum = 1;
            Map<String, Long> produitsParCategorie = produits.stream()
                    .filter(produit -> produit.getCategorie() != null)
                    .collect(Collectors.groupingBy(
                            produit -> produit.getCategorie().getLibelle(),
                            Collectors.counting()
                    ));

            for (Map.Entry<String, Long> entry : produitsParCategorie.entrySet()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
            }

            FileOutputStream fileOut = new FileOutputStream(EXCEL_EXPORT_PATH);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            showAlert("Succès", "Document Excel créé dans : " + EXCEL_EXPORT_PATH, AlertType.INFORMATION);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors de la création du fichier Excel : " + e.getMessage(), AlertType.ERROR);
        }
    }

    // Méthode pour afficher des alertes
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //========================================= REDIRECTION ENTRE LES PAGES ===============================================

    @FXML
    void sceneDashboard(MouseEvent event) {
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
