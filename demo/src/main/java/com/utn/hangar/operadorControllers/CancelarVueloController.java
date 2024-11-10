package com.utn.hangar.operadorControllers;

import com.utn.hangar.Ventanas;
import constantes.Data;
import entidades.Avion;
import entidades.Usuario;
import gestores.*;
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

public class CancelarVueloController {

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<Avion, Void> cancelarVuelo;

    @FXML
    private TableColumn<Avion, String> nombreAvion;

    @FXML
    private TableColumn<Avion, String> nombrePiloto;

    @FXML
    private TableColumn<Avion, String> nroLicencia;

    @FXML
    private TableColumn<Avion, String> nroMatricula;

    @FXML
    private TableView<Avion> tablaVuelos;

    // METODO QUE CARGA LA INFORMACION QUE SE VA A MOSTRAR EN LA VENTANA
    @FXML
    public void initialize() {
        //LLAMA A CLASE GESTORA Y TRAE A HANGARES DEL JSON
        GestorHangar gestorHangar = new GestorHangar();
        gestorHangar.cargarHangarDesdeArchivo();

        // Convierte el HASHSET a ObservableList
        ObservableList<Avion> listaObservableHangar = FXCollections.observableArrayList(gestorHangar.getListaHangar());

        // Enlaza cada columna usando expresiones lambda
        nroLicencia.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPiloto().getNumeroLicencia()));
        nombrePiloto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPiloto().getNombreApellido()));
        nroMatricula.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNumeracion())));
        nombreAvion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));



        // AGREGO EL BOTON "CANCELAR" EN CADA FILA DE LA TABLA DE VUELOS
        cancelarVuelo.setCellFactory(new Callback<TableColumn<Avion, Void>, TableCell<Avion, Void>>() {
            @Override
            public TableCell<Avion, Void> call(final TableColumn<Avion, Void> param) {
                return new TableCell<Avion, Void>() {
                    private final Button btnModificar = new Button("Cancelar");
                    {
                        btnModificar.setMaxWidth(Double.MAX_VALUE);
                        btnModificar.setAlignment(Pos.CENTER);
                        btnModificar.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                        btnModificar.setOnAction((ActionEvent event) -> {
                            Avion vueloCancelado = getTableView().getItems().get(getIndex());
                            // BORRO EL AVION DEL ARCHIVO DE HANGAR.JSON
                            gestorHangar.getListaHangar().remove(vueloCancelado);
                            gestorHangar.guardarHangarToFile();

                            // ACTUALIZO EL AVION Y PONGO SU PILOTO EN NULL
                            GestorAviones gestorAviones = new GestorAviones();
                            gestorAviones.cargarAvionDesdeArchivo();
                            gestorAviones.eliminarPilotoDeAvion(vueloCancelado);
                            gestorAviones.guardarAvionToFile();

                            // ACTUALIZO EL PILOTO Y SETEO SU ESTADO EN TRUE
                            GestorPilotos gestorPilotos = new GestorPilotos();
                            gestorPilotos.cargarPilotoDesdeArchivo();
                            gestorPilotos.actualizarEstadoPiloto(vueloCancelado.getPiloto());
                            gestorPilotos.guardarPilotoToFile();


                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Vuelo cancelado");
                            alert.setHeaderText(null);
                            alert.setContentText("Se ha cancelado el vuelo exitosamente.");
                            alert.showAndWait();

                            listaObservableHangar.remove(vueloCancelado); //ELIMINO EN TIEMPO REAL EL VUELO CANCELADO DE LA TABLA

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnModificar);
                        }
                    }
                };
            }
        });

        // Establece la lista observable como el elemento de la tabla
        tablaVuelos.setItems(listaObservableHangar);
    }

    @FXML
    void onClickBtnVolver(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0", stage, "/com/utn/hangar/operadorViews/menu-hangar-view.fxml");

        } catch (IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}

