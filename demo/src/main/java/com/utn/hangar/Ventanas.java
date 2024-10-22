package com.utn.hangar;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class Ventanas {

    private Stage stage;

    public static void cambioEscena(String titulo, Stage stage, String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void exceptionError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error - Sistema Hangar 2.0");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
