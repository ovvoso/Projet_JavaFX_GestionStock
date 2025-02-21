package com.example.examenjpapersistence.Controller;

import com.example.examenjpapersistence.SceneManager.SceneManager;
import com.example.examenjpapersistence.dao.ProduitRepository;
import com.example.examenjpapersistence.model.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class StatistiqueController extends BaseController implements Initializable {

    @FXML private PieChart pieChart;
    @FXML private BarChart<String, Number> barChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    
    private final ProduitRepository produitRepository = new ProduitRepository();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCharts();
    }

    public void loadCharts() {
        // Clear previous data
        pieChart.getData().clear();
        barChart.getData().clear();
        
        loadPieChartData();
        loadBarChartData();
    }

    private void loadPieChartData() {
        try {
            List<Produit> produits = produitRepository.getAll();
            
            Map<String, Long> produitsParCategorie = produits.stream()
                .collect(Collectors.groupingBy(
                    produit -> produit.getCategorie().getLibelle(),
                    Collectors.counting()
                ));

            produitsParCategorie.forEach((categorie, nombre) -> {
                pieChart.getData().add(new PieChart.Data(categorie + " (" + nombre + ")", nombre));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBarChartData() {
        try {
            List<Produit> produits = produitRepository.getAll();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            
            // Utiliser l'année en cours
            int currentYear = LocalDate.now().getYear();
            series.setName("Produits ajoutés en " + currentYear);

            Map<String, Long> produitsParMois = produits.stream()
                .filter(p -> p.getDateCreation() != null && 
                           p.getDateCreation().getYear() == currentYear)
                .collect(Collectors.groupingBy(
                    p -> p.getDateCreation().getMonth().toString(),
                    Collectors.counting()
                ));

            // Ajouter les données au graphique
            produitsParMois.forEach((mois, nombre) -> {
                series.getData().add(new XYChart.Data<>(mois, nombre));
            });

            barChart.getData().add(series);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Navigation
    @FXML void sceneDashboard(MouseEvent event) {
        SceneManager.changeScene("Dashboard.fxml");
    }

    @FXML void sceneCategorie(MouseEvent event) {
        SceneManager.changeScene("Categorie.fxml");
    }

    @FXML void sceneProduit(MouseEvent event) {
        SceneManager.changeScene("Produit.fxml");
    }

    @FXML void sceneExtraireDocument(MouseEvent event) {
        SceneManager.changeScene("ExtraireDocument.fxml");
    }

    @FXML void sceneStatistique(MouseEvent event) {
        SceneManager.changeScene("Statistique.fxml");
        loadCharts(); // Recharger les graphiques
    }
}