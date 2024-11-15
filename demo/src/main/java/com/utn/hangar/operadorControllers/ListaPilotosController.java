package com.utn.hangar.operadorControllers;

import com.utn.hangar.Ventanas;
import constantes.Data;
import entidades.Piloto;
import enums.Rango;
import gestores.GestorPilotos;
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

public class ListaPilotosController {

    @FXML
    private TableColumn<Piloto, Void> altaPiloto;

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<Piloto, String> dni;

    @FXML
    private TableColumn<Piloto, Integer> idPiloto;

    @FXML
    private TableColumn<Piloto, Integer> licencia;

    @FXML
    private TableColumn<Piloto, Void> modifyPiloto;

    @FXML
    private TableColumn<Piloto, String> nombreApellido;

    @FXML
    private TableColumn<Piloto, Rango> rangoPiloto;

    @FXML
    private TableView<Piloto> tablaPilotos;


    // METODO QUE CARGA LA INFORMACION QUE SE VA A MOSTRAR EN LA VENTANA
    @FXML
    public void initialize() {
        //LLAMA A CLASE GESTORA Y TRAE A LOS PILOTOS DEL JSON
        GestorPilotos gestorPilotos = new GestorPilotos();
        gestorPilotos.cargarDesdeArchivo();

        // Convierte el ArrayList a ObservableList
        ObservableList<Piloto> listaObservablePilotos = FXCollections.observableArrayList(gestorPilotos.getListaPilotos());

        // Enlaza cada columna usando expresiones lambda
        idPiloto.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        nombreApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreApellido()));
        dni.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDni()));
        licencia.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getNumeroLicencia()));
        rangoPiloto.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRango()));


        // Agrega el botón de "Estado" en cada fila de la columna Estado
        altaPiloto.setCellFactory(new Callback<TableColumn<Piloto, Void>, TableCell<Piloto, Void>>() {
            @Override
            public TableCell<Piloto, Void> call(final TableColumn<Piloto, Void> param) {
                return new TableCell<Piloto, Void>() {
                    private final Button btnEstado = new Button();

                    {
                        btnEstado.setMaxWidth(Double.MAX_VALUE);
                        btnEstado.setAlignment(Pos.CENTER);
                        // Define la acción del botón para alternar entre Alta y Baja
                        btnEstado.setOnAction((ActionEvent event) -> {
                            Piloto piloto = getTableView().getItems().get(getIndex());

                            // Alterna el estado entre 0 y 1
                            int nuevoEstado = piloto.getAlta() == 1 ? 0 : 1;
                            piloto.setAlta(nuevoEstado);
                            //GUARDO LA MODIFICACION DEL ALTA EN EL ARCHIVO
                            gestorPilotos.guardarEnArchivo();
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
                            Piloto piloto = getTableView().getItems().get(getIndex());
                            int estadoActual = piloto.getAlta();

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
        modifyPiloto.setCellFactory(new Callback<TableColumn<Piloto, Void>, TableCell<Piloto, Void>>() {
            @Override
            public TableCell<Piloto, Void> call(final TableColumn<Piloto, Void> param) {
                return new TableCell<Piloto, Void>() {
                    private final Button btnModificar = new Button("Modificar");
                    {
                        btnModificar.setMaxWidth(Double.MAX_VALUE);
                        btnModificar.setAlignment(Pos.CENTER);
                        btnModificar.setOnAction((ActionEvent event) -> {
                            try {
                                // OBTENER EL ID DEL PILOTO DE LA FILA
                                Piloto piloto = getTableView().getItems().get(getIndex());
                                Data.setIdAux(piloto.getId()); // SETEA EL ID AUX DE DATA, PARA LUEGO TENER LA INFO DEL PILOTO ELEGIDO
                                // ABRIR VENTANA DE MODIFICACION
                                Stage stage = (Stage) btnVolver.getScene().getWindow();
                                Ventanas.cambioEscena("Sistema Hangar 2.0",stage,"/com/utn/hangar/operadorViews/modificar-piloto-view.fxml");
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
        tablaPilotos.setItems(listaObservablePilotos);
    }

    @FXML
    void onClickBtnVolver(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage,"/com/utn/hangar/operadorViews/cuartel-pilotos-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}

