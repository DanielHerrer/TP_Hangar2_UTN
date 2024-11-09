package com.utn.hangar.operadorControllers;

import com.utn.hangar.Ventanas;
import constantes.Data;
import entidades.Avion;
import entidades.Usuario;
import gestores.GestorAviones;
import gestores.GestorUsuarios;
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

public class ListaAvionesController {

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

    @FXML
    private TableColumn<Avion, Void> modifyAvion;


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


        // Agrega el botón de "Estado" en cada fila de la columna estadoUsuario
        altaAvion.setCellFactory(new Callback<TableColumn<Avion, Void>, TableCell<Avion, Void>>() {
            @Override
            public TableCell<Avion, Void> call(final TableColumn<Avion, Void> param) {
                return new TableCell<Avion, Void>() {
                    private final Button btnEstado = new Button();

                    {
                        btnEstado.setMaxWidth(Double.MAX_VALUE);
                        btnEstado.setAlignment(Pos.CENTER);
                        // Define la acción del botón para alternar entre Alta y Baja
                        btnEstado.setOnAction((ActionEvent event) -> {
                            Avion avion = getTableView().getItems().get(getIndex());

                            // Alterna el estado entre 0 y 1
                            int nuevoEstado = avion.getAlta() == 1 ? 0 : 1;
                            avion.setAlta(nuevoEstado);
                            //GUARDO LA MODIFICACION DEL ALTA EN EL ARCHIVO
                            gestorAviones.guardarAvionToFile();
                            // Actualiza el botón en la interfaz
                            actualizarBotonEstado(btnEstado, nuevoEstado);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Avion avion = getTableView().getItems().get(getIndex());
                            int estadoActual = avion.getAlta();

                            // Actualiza el botón según el estado actual del usuario
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

        // Agrega el botón "Modificar" en cada fila de la columna modifyUsuario
        modifyAvion.setCellFactory(new Callback<TableColumn<Avion, Void>, TableCell<Avion, Void>>() {
            @Override
            public TableCell<Avion, Void> call(final TableColumn<Avion, Void> param) {
                return new TableCell<Avion, Void>() {
                    private final Button btnModificar = new Button("Modificar");
                    {
                        btnModificar.setMaxWidth(Double.MAX_VALUE);
                        btnModificar.setAlignment(Pos.CENTER);
                        btnModificar.setOnAction((ActionEvent event) -> {
                            try {
                                // OBTENER EL ID DEL AVION DE LA FILA
                                Avion avion = getTableView().getItems().get(getIndex());
                                Data.setIdAux(avion.getId()); // SETEA EL ID AUX DE DATA, PARA LUEGO TENER LA INFO DEL AVION ELEGIDO
                                // ABRIR VENTANA DE MODIFICACION
                                Stage stage = (Stage) btnVolver.getScene().getWindow();
                                Ventanas.cambioEscena("Sistema Hangar 2.0",stage,"/com/utn/hangar/operadorViews/modificar-avion-view.fxml");
                            } catch(IOException e) {
                                Ventanas.exceptionError(e);
                                e.printStackTrace();
                            }
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
        tablaAviones.setItems(listaObservableAviones);
    }

    @FXML
    void onClickBtnVolver(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage,"/com/utn/hangar/operadorViews/taller-aviones-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}

