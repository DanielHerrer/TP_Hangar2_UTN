package com.utn.hangar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static final Image icon = new Image(Ventanas.class.getResource("/images/icon.png").toString()); // Icono estático

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Sistema Hangar 2.0 (Inicio)");
        // Añadir el icono a la ventana si no está añadido aún
        if (stage.getIcons().isEmpty()) {
            stage.getIcons().add(icon);
        }
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
