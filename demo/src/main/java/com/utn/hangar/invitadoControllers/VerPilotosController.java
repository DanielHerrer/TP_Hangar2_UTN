package com.utn.hangar.invitadoControllers;

import com.utn.hangar.Ventanas;
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

public class VerPilotosController {

    @FXML
    private TableColumn<Piloto, Void> altaPiloto;

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<Piloto, String> dniPiloto;

    @FXML
    private TableColumn<Piloto, Integer> horasVueloPiloto;

    @FXML
    private TableColumn<Piloto, Integer> idPiloto;

    @FXML
    private TableColumn<Piloto, String> licenciaPiloto;

    @FXML
    private TableColumn<Piloto, Integer> nacimientoPiloto;

    @FXML
    private TableColumn<Piloto, String> nombrePiloto;

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
        nombrePiloto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreApellido()));
        dniPiloto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));
        nacimientoPiloto.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAnioNacimiento()));
        licenciaPiloto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeroLicencia()));
        horasVueloPiloto.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getHorasVuelo()));
        rangoPiloto.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRango()));

        // Configuración de la columna para mostrar el estado de alta/baja
        altaPiloto.setCellFactory(new Callback<TableColumn<Piloto, Void>, TableCell<Piloto, Void>>() {
            @Override
            public TableCell<Piloto, Void> call(final TableColumn<Piloto, Void> param) {
                return new TableCell<Piloto, Void>() {
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
                            Piloto piloto = getTableView().getItems().get(getIndex());
                            int estadoActual = piloto.getAlta();

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
        tablaPilotos.setItems(listaObservablePilotos);
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
