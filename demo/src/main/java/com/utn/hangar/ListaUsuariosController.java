package com.utn.hangar;

import control.ControlUsuarios;
import entidades.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ListaUsuariosController {

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Usuario> tablaLista;

    @FXML
    private TableColumn<Usuario, String> colNombre; // Columna para el nombre

    @FXML
    private TableColumn<Usuario, Integer> colID; // Columna para el ID

    private ControlUsuarios controlUsuarios = new ControlUsuarios();

    @FXML
    public void initialize() {
        // Configuración de las columnas
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Cargar datos en el TableView
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        controlUsuarios.cargarUsuariosDesdeArchivo();
        List<Usuario> usuarios = controlUsuarios.listaUsuarios; // Método que debes implementar en ControlUsuarios
        ObservableList<Usuario> observableUsuarios = FXCollections.observableArrayList(usuarios);
        tablaLista.setItems(observableUsuarios);
    }

    @FXML
    void onClickBtnVolver(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0", stage, "/com/utn/hangar/admin-view.fxml");
        } catch (IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }
}
