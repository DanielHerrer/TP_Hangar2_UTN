package com.utn.hangar.invitadoControllers;

import com.utn.hangar.Ventanas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class InvitadoController {


    @FXML
    private Button btnVerAviones;

    @FXML
    private Button btnVerPiloto;

    @FXML
    private Button btnModifyPerfil;

    @FXML
    private Button btnLogout;

    @FXML
    void onClickBtnVerAviones(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVerAviones.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage,"/com/utn/hangar/invitadoViews/ver-aviones-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnVerPiloto(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVerPiloto.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage,"/com/utn/hangar/invitadoViews/ver-pilotos-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnModifyPerfil(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVerPiloto.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage,"/com/utn/hangar/homeViews/modificar-perfil-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    @FXML
    void onBtnLogoutButtonClick(ActionEvent event) {
        try {
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage,"/com/utn/hangar/homeViews/login-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}