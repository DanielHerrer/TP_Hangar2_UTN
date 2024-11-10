package com.utn.hangar.invitadoControllers;

import com.utn.hangar.Ventanas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class InvitadoController {

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnVerAviones;

    @FXML
    private Button btnVerPiloto;

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
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage,"/com/utn/hangar/invitadoViews/invitado-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    @FXML
    void onBtnLogoutButtonClick(ActionEvent event) {
        try {
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage,"/com/utn/hangar/invitadoViews/invitado-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}