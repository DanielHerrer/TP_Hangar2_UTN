package com.utn.hangar.adminControllers;

import com.utn.hangar.Ventanas;
import entidades.Usuario;
import gestores.GestorRegistros;
import gestores.GestorUsuarios;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ListaRegistrosController {

    @FXML
    private TableView<Usuario> tablaRegistros;

    @FXML
    private TableColumn<Usuario, String> apellidoUsuario;

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<Usuario, Integer> idUsuario;

    @FXML
    private TableColumn<Usuario, String> rolUsuario;

    @FXML
    private TableColumn<Usuario, String> fechaRegistro;

    @FXML
    private TableColumn<Usuario, String> userUsuario;

    // METODO QUE CARGA LA INFORMACION QUE SE VA A MOSTRAR EN LA VENTANA
    @FXML
    public void initialize() {

        //LLAMA A CLASE GESTORA Y TRAE A LOS USUARIOS DEL JSON
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        gestorUsuarios.cargarDesdeArchivo();

        // Convierte el ArrayList a ObservableList
        ObservableList<Usuario> listaObservableRegistros = FXCollections.observableArrayList(gestorUsuarios.getListaUsuarios());
        //ObservableMap<Integer, LocalDateTime> listaObservableRegistros = FXCollections.observableMap(gestorRegistros.getListaRegistros());

        // Enlaza cada columna usando expresiones lambda
        idUsuario.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        apellidoUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreApellido()));
        userUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreUsuario()));

        // Configura la columna rolUsuario para mostrar el texto correspondiente
        rolUsuario.setCellValueFactory(cellData -> {
            int rol = cellData.getValue().getRol();
            String rolTexto;
            switch (rol) {
                case 0: rolTexto = "INVITADO"; break;
                case 1: rolTexto = "OPERADOR"; break;
                case 2: rolTexto = "ADMIN"; break;
                default: rolTexto = "DESCONOCIDO"; break; // Para valores no esperados
            }
            return new SimpleStringProperty(rolTexto);
        });

        // Configura la columna fechaRegistro
        fechaRegistro.setCellValueFactory(cellData -> {

            LocalDateTime fecha = cellData.getValue().getRegistro();

            // Definir el formato deseado: "yyyy-MM-dd HH:mm:ss"
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String fechaStr = fecha.format(formato);

            return new SimpleStringProperty(fechaStr);
        });

        // Establece la lista observable como el elemento de la tabla
        tablaRegistros.setItems(listaObservableRegistros);
    }


    @FXML
    void onClickBtnVolver(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage,"/com/utn/hangar/adminViews/admin-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}
