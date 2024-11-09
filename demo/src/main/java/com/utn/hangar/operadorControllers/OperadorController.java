package com.utn.hangar.operadorControllers;

import com.utn.hangar.Ventanas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OperadorController {

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnTallerAviones;

    @FXML
    private Button btnCuartelPilotos;

    @FXML
    private Button btnModificarPerfil;

    @FXML
    void onBtnLogoutButtonClick(ActionEvent event) {
        try {
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/homeViews/login-view.fxml");
        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    @FXML
    void onBtnTallerAvionesButtonClick(ActionEvent event) {
        try {
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/operadorViews/taller-aviones-view.fxml");
        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    @FXML
    void onBtnCuartelPilotosButtonClick(ActionEvent event) {
        try {
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/operadorViews/cuartel-pilotos-view.fxml");
        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    @FXML
    void onBtnModificarPerfilButtonClick(ActionEvent event) {
        try {
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/homeViews/modificar-perfil-view.fxml");
        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}
