package com.utn.hangar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminController {

    @FXML
    private Button btnListaUsuario;

    @FXML
    private Button btnLogout;

    @FXML
    void onBtnListaUsuariosClick(ActionEvent event) {
        try {
            Stage stage = (Stage) btnListaUsuario.getScene().getWindow();
            Ventanas.cambioEscena("Lista de usuarios",stage, "/com/utn/hangar/list-usuario-view.fxml");

        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    @FXML
    void onBtnLogoutButtonClick(ActionEvent event) {
        try {
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0", stage, "/com/utn/hangar/login-view.fxml");

        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}

