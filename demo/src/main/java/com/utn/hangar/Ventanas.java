package com.utn.hangar;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Ventanas {

    private static Stage stage;
    private static final Image icon = new Image(Ventanas.class.getResource("/images/icon.png").toString()); // Icono estático
    public static void cambioEscena(String titulo, Stage stage, String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(titulo);

        // Añadir el icono a la ventana si no está añadido aún
        if (stage.getIcons().isEmpty()) {
            stage.getIcons().add(icon);
        }

        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void exceptionError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Sistema Hangar 2.0 (Error)");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
