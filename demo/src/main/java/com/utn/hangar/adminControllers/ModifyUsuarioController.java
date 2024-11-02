package com.utn.hangar.adminControllers;

import com.utn.hangar.Ventanas;
import constantes.Archivos;
import control.ControlUsuarios;
import entidades.Usuario;
import enums.Genero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyUsuarioController {

    @FXML
    private ComboBox<Genero> SelecGen;

    @FXML
    private Button btnSavePerfil;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField inputAnioNac;

    @FXML
    private TextField inputDNI;

    @FXML
    private TextField inputNomApe;

    @FXML
    private PasswordField inputPass;

    @FXML
    private TextField inputUser;

    @FXML
    public void initialize() {
        SelecGen.getItems().setAll(Genero.values());
        // TRAE EL USUARIO SELECCIONADO
        ControlUsuarios conUser = new ControlUsuarios();
        conUser.cargarUsuarioDesdeArchivo();
        Usuario user = conUser.getUsuarioById(Archivos.getIdAux()); // RETORNA EL USUARIO SEGUN EL ID AUX
        // SETEA LA INFORMACION
        inputNomApe.setText(user.getNombreApellido());
        inputAnioNac.setText(Integer.toString(user.getAnioNacimiento()));
        inputDNI.setText(user.getDni());
        SelecGen.setValue(user.getGenero());
        inputUser.setText(user.getNombreUsuario());
        inputPass.setText(user.getContrasenia());
    }

    @FXML
    void onClickBtnSavePerfil(ActionEvent event) {

    }

    @FXML
    void onClickBtnVolver(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/adminViews/lista-usuarios-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}
