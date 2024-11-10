package com.utn.hangar.operadorControllers;

import com.utn.hangar.Ventanas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuHangarController {

    @FXML
    private Button btnAsignarVuelo;

    @FXML
    private Button btnCancelarVuelo;

    @FXML
    private Button btnListarHangar;

    @FXML
    private Button btnDespegarAviones;

    @FXML
    private Button btnVolver;

    // BOTON PARA ASIGNAR UN VUELO
    @FXML
    void onClickBtnAsignarVuelo(ActionEvent event) {
        try {
            Stage stage = (Stage) btnAsignarVuelo.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/operadorViews/asignar-vuelo-view.fxml");
        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    // BOTON PARA CANCELAR UN VUELO
    @FXML
    void onClickBtnCancelarVuelo(ActionEvent event) {
        try {
            Stage stage = (Stage) btnCancelarVuelo.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/operadorViews/cancelar-vuelo-view.fxml");
        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnListarHangar(ActionEvent event) {
        try {
            Stage stage = (Stage) btnListarHangar.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/operadorViews/listar-hangar-view.fxml");
        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    // BOTON PARA IR AL MENU DE DESPEGAR AVIONES
    @FXML
    void onClickBtnDespegarAviones(ActionEvent event) {
        try {
            Stage stage = (Stage) btnDespegarAviones.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/operadorViews/despegar-view.fxml");
        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    // BOTON PARA VOLVER AL MENU DE OPERADOR
    @FXML
    void onClickBtnVolver(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/operadorViews/operador-view.fxml");
        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}


