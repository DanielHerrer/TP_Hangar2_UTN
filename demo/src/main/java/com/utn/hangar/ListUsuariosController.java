package com.utn.hangar;

import constantes.Archivos;
import control.ControlUsuarios;
import entidades.Usuario;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class ListUsuariosController {

    @FXML
    private TableView<Usuario> tablaUsuarios;

    @FXML
    private TableColumn<Usuario, Integer> altaUsuario;

    @FXML
    private TableColumn<Usuario, String> apellidoUsuario;

    @FXML
    private TableColumn<Usuario, Integer> idUsuario;

    @FXML
    private TableColumn<Usuario, Integer> rolUsuario;

    @FXML
    private TableColumn<Usuario, String> userUsuario;

    @FXML
    public void initialize() {
        //LLAMA A CLASE GESTORA Y TRAE A LOS USUARIOS DEL JSON
        ControlUsuarios conUsuario = new ControlUsuarios();
        conUsuario.cargarUsuariosDesdeArchivo();

        // Convierte el ArrayList a ObservableList
        ObservableList<Usuario> listaObservableUsuarios = FXCollections.observableArrayList(conUsuario.getListaUsuarios());

        // Enlaza cada columna usando expresiones lambda
        idUsuario.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        apellidoUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreApellido()));
        rolUsuario.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRol()));
        userUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreUsuario()));
        altaUsuario.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAlta()));

        // Establece la lista observable como el elemento de la tabla
        tablaUsuarios.setItems(listaObservableUsuarios);
    }

}
