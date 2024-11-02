package com.utn.hangar.homeControllers;

import com.utn.hangar.Ventanas;
import constantes.Archivos;
import entidades.Usuario;
import enums.Genero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyPerfilController {

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
    private Hyperlink linkCambiarUserPass;

    @FXML
    public void initialize() {
        SelecGen.getItems().setAll(Genero.values());
        // TRAE EL USUARIO LOGEADO ACTUALMENTE
        Usuario userLog = Archivos.getUserLogueado();
        // SETEA LA INFORMACION
        inputNomApe.setText(userLog.getNombreApellido());
        inputAnioNac.setText(Integer.toString(userLog.getAnioNacimiento()));
        inputDNI.setText(userLog.getDni());
        SelecGen.setValue(userLog.getGenero());
    }

    // BOTON GUARDAR PERFIL
    @FXML
    void onClickBtnSavePerfil(ActionEvent event) {

    }

    @FXML
    void onClickLinkCambiarUserPass(ActionEvent event) {
        try {
            Stage stage = (Stage) linkCambiarUserPass.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/homeViews/modificar-pass-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnVolver(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            // TRAE EL USUARIO LOGEADO ACTUALMENTE
            Usuario userLog = Archivos.getUserLogueado();
            // COMPARA EL USUARIO LOGUEADO ACTUALMENTE PARA VOLVER A SU MENU CORRESPONDIENTE
            if (userLog.getRol() < 1) {
                Ventanas.cambioEscena("Sistema Hangar 2.0 (Invitado)",stage, "/com/utn/hangar/invitadoViews/invitado-view.fxml");
            } else if (userLog.getRol() == 1) {
                Ventanas.cambioEscena("Sistema Hangar 2.0 (Operador)",stage, "/com/utn/hangar/operadorViews/operador-view.fxml");
            } else if (userLog.getRol() > 1) {
                Ventanas.cambioEscena("Sistema Hangar 2.0 (Admin)",stage, "/com/utn/hangar/adminViews/admin-view.fxml");
            }

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}