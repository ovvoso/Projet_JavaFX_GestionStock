module com.example.examenjpapersistence {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires lombok;
    requires java.persistence;
    
    // Ajout des requires pour PDF et Excel
    requires itextpdf;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    
    // Configuration des opens existants
    opens com.example.examenjpapersistence.model to javafx.base, org.hibernate.orm.core;
    opens com.example.examenjpapersistence.Controller to javafx.fxml;
    opens com.example.examenjpapersistence.Controller.login to javafx.fxml;

    exports com.example.examenjpapersistence;
}
