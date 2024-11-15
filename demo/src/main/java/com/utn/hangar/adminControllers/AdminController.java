package com.utn.hangar.adminControllers;

import com.utn.hangar.Ventanas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminController {

    @FXML
    private Button btnListaRegistros;

    @FXML
    private Button btnListaUsuarios;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnModifyPerfil;

    // BOTON PARA LISTAR USUARIOS
    @FXML
    void onClickBtnListaUsuarios(ActionEvent event) {
        try {
            Stage stage = (Stage) btnListaUsuarios.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/adminViews/lista-usuarios-view.fxml");
        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnListaRegistros(ActionEvent event) {
        try {
            Stage stage = (Stage) btnListaRegistros.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/adminViews/lista-registros-view.fxml");
        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    // BOTON MODIFICAR PERFIL
    @FXML
    void onClickBtnModifyPerfil(ActionEvent event) {
        try {
            Stage stage = (Stage) btnModifyPerfil.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/homeViews/modificar-perfil-view.fxml");
        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    // BOTON CERRAR SESIÓN
    @FXML
    void onClickBtnLogout(ActionEvent event) {
        try {
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0", stage, "/com/utn/hangar/home-view.fxml");

        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}

