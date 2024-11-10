package com.utn.hangar.invitadoControllers;

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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class VerAvionesController {
    @FXML
    private TableColumn<Avion, Void> altaAvion;

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<Avion, Integer> capacidadPasajeros;

    @FXML
    private TableColumn<Avion, Integer> idAvion;

    @FXML
    private TableColumn<Avion, String> modeloAvion;

    @FXML
    private TableColumn<Avion, String> aerolineaAvion;

    @FXML
    private TableColumn<Avion, String> nombreAvion;

    @FXML
    private TableColumn<Avion, Integer> numearcionAvion;

    @FXML
    private TableColumn<Avion, Integer> numeroVuelos;

    @FXML
    private TableView<Avion> tablaAviones;


    // METODO QUE CARGA LA INFORMACION QUE SE VA A MOSTRAR EN LA VENTANA
    @FXML
    public void initialize() {
        //LLAMA A CLASE GESTORA Y TRAE A LOS AVIONES DEL JSON
        GestorAviones gestorAviones = new GestorAviones();
        gestorAviones.cargarAvionDesdeArchivo();

        // Convierte el ArrayList a ObservableList
        ObservableList<Avion> listaObservableAviones = FXCollections.observableArrayList(gestorAviones.getListaAviones());

        // Enlaza cada columna usando expresiones lambda
        idAvion.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        nombreAvion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        numearcionAvion.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNumeracion()));
        modeloAvion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModelo()));
        aerolineaAvion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAerolinea()));
        capacidadPasajeros.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCapacidadPasajeros()));
        numeroVuelos.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getVuelosRealizados()));

        // Configuración de la columna para mostrar el estado de alta/baja
        altaAvion.setCellFactory(new Callback<TableColumn<Avion, Void>, TableCell<Avion, Void>>() {
            @Override
            public TableCell<Avion, Void> call(final TableColumn<Avion, Void> param) {
                return new TableCell<Avion, Void>() {
                    private final Button btnEstado = new Button();

                    {
                        btnEstado.setMaxWidth(Double.MAX_VALUE);
                        btnEstado.setAlignment(Pos.CENTER);
                        btnEstado.setDisable(true);  // Deshabilita la interacción con el botón
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Avion avion = getTableView().getItems().get(getIndex());
                            int estadoActual = avion.getAlta();

                            // Actualiza el botón para reflejar el estado actual de alta/baja
                            actualizarBotonEstado(btnEstado, estadoActual);
                            setGraphic(btnEstado);
                        }
                    }

                    // Método para configurar el botón de estado según el valor
                    private void actualizarBotonEstado(Button btn, int estado) {
                        if (estado == 1) {
                            btn.setText("Alta");
                            btn.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                        } else {
                            btn.setText("Baja");
                            btn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                        }
                    }
                };
            }
        });

        // Establece la lista observable como el elemento de la tabla
        tablaAviones.setItems(listaObservableAviones);
    }

    @FXML
    void onClickBtnVolver(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage,"/com/utn/hangar/invitadoViews/invitado-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }
}
