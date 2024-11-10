package com.utn.hangar.operadorControllers;

import com.utn.hangar.Ventanas;
import constantes.Data;
import entidades.Avion;
import gestores.GestorAviones;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class CargarCombustibleController {

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<Avion, Integer> idAvion;

    @FXML
    private TableColumn<Avion, Double> llenarTanque;

    @FXML
    private TableColumn<Avion, Double> nivelCombustible;

    @FXML
    private TableColumn<Avion, String> nombreAvion;

    @FXML
    private TableColumn<Avion, Integer> numeracionAvion;

    @FXML
    private TableView<Avion> tablaCombustible;

    // MÉTODO QUE CARGA LA INFORMACIÓN QUE SE VA A MOSTRAR EN LA VENTANA
    @FXML
    public void initialize() {
        // Llama a clase gestora y trae a los aviones del JSON
        GestorAviones gestorAviones = new GestorAviones();
        gestorAviones.cargarAvionDesdeArchivo();

        // Convierte el ArrayList a ObservableList
        ObservableList<Avion> listaObservableAviones = FXCollections.observableArrayList(gestorAviones.getListaAviones());

        // Enlaza cada columna usando expresiones lambda
        idAvion.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        nombreAvion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        numeracionAvion.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNumeracion()));
        nivelCombustible.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().combustibleDouble()));

        // Configura la columna de nivel de combustible con una barra de progreso
        nivelCombustible.setCellFactory(tc -> new TableCell<Avion, Double>() {
            private final ProgressBar progressBar = new ProgressBar();

            {
                progressBar.setMinHeight(20);
                progressBar.setMinWidth(150);
                progressBar.setStyle("-fx-accent: green;");
            }

            @Override
            protected void updateItem(Double nivel, boolean empty) {
                super.updateItem(nivel, empty);
                if (empty || nivel == null) {
                    setGraphic(null);
                } else {
                    progressBar.setProgress(nivel);
                    setGraphic(progressBar);
                }
            }
        });

        // Configura la columna de llenar tanque con un botón
        llenarTanque.setCellFactory(new Callback<TableColumn<Avion, Double>, TableCell<Avion, Double>>() {
            @Override
            public TableCell<Avion, Double> call(final TableColumn<Avion, Double> param) {
                return new TableCell<Avion, Double>() {
                    private final Button btnCargar = new Button("Cargar combustible");

                    {
                        btnCargar.setOnAction((ActionEvent event) -> {
                            Avion avion = getTableView().getItems().get(getIndex());

                            // Aquí puedes implementar la lógica para cargar combustible
                            // Por ejemplo, actualizar el nivel de combustible al máximo (100%)
                            avion.setCombustibleActual(100);
                            gestorAviones.guardarAvionToFile();

                            // Refrescar la tabla para mostrar el nuevo nivel de combustible
                            tablaCombustible.refresh();
                        });
                    }

                    @Override
                    public void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnCargar);
                        }
                    }
                };
            }
        });

        // Establece la lista observable como el elemento de la tabla
        tablaCombustible.setItems(listaObservableAviones);
    }

    @FXML
    void onClickBtnVolver(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0", stage, "/com/utn/hangar/operadorViews/taller-aviones-view.fxml");

        } catch (IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}
