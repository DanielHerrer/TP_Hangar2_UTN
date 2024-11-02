package com.utn.hangar.homeControllers;
import com.utn.hangar.Ventanas;
import constantes.Archivos;
import entidades.Usuario;
import enums.Genero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyPassController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSavePass;

    @FXML
    private PasswordField inputPass;

    @FXML
    private PasswordField inputPassConfirm;

    @FXML
    private TextField inputUser;

    @FXML
    public void initialize() {
        // TRAE EL USUARIO LOGEADO ACTUALMENTE
        Usuario userLog = Archivos.getUserLogueado();
        // SETEA LA INFORMACION
        inputUser.setText(userLog.getNombreUsuario());
        inputPass.setText(userLog.getContrasenia());
        inputPassConfirm.setText(userLog.getContrasenia());
    }

    // BOTON GUARDAR USUARIO Y CONTRASENIA NUEVAS
    @FXML
    void onClickBtnSavePass(ActionEvent event) {

    }

    @FXML
    void onClickBtnCancel(ActionEvent event) {
        try {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/homeViews/modificar-perfil-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}
