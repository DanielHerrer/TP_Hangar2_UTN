package com.utn.hangar.operadorControllers;

import com.utn.hangar.Ventanas;
import entidades.Avion;
import enums.Rango;
import gestores.GestorAviones;
import gestores.GestorHangar;
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

public class ListaHangarController {

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<Avion, String> nombreAvion;

    @FXML
    private TableColumn<Avion, String> nombrePiloto;

    @FXML
    private TableColumn<Avion, String> nroLicencia;

    @FXML
    private TableColumn<Avion, Integer> numeracionAvion;

    @FXML
    private TableColumn<Avion, Integer> pasajerosAvion;

    @FXML
    private TableColumn<Avion, Rango> rangoPiloto;

    @FXML
    private TableView<Avion> tablaHangar;

    // METODO QUE CARGA LA INFORMACION QUE SE VA A MOSTRAR EN LA VENTANA
    @FXML
    public void initialize() {
        //LLAMA A CLASE GESTORA Y TRAE A LOS AVIONES DEL JSON
        GestorHangar gestorHangar = new GestorHangar();
        gestorHangar.cargarHangarDesdeArchivo();

        // Convierte el ArrayList a ObservableList
        ObservableList<Avion> listaObservableAviones = FXCollections.observableArrayList(gestorHangar.getListaHangar());

        // Enlaza cada columna usando expresiones lambda
        nombrePiloto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPiloto().getNombreApellido()));
        nroLicencia.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPiloto().getNumeroLicencia()));
        rangoPiloto.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPiloto().getRango()));
        nombreAvion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        numeracionAvion.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNumeracion()));
        pasajerosAvion.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCapacidadPasajeros()));


        // Establece la lista observable como el elemento de la tabla
        tablaHangar.setItems(listaObservableAviones);
    }

    @FXML
    void onClickBtnVolver(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage,"/com/utn/hangar/operadorViews/menu-hangar-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}

