package com.utn.hangar.operadorControllers;

import com.utn.hangar.Ventanas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class TallerAvionesController {

    @FXML
    private Button btnListarAviones;

    @FXML
    private Button btnCargarCombustible;

    @FXML
    private Button btnCrearAvion;

    @FXML
    private Button btnVolver;


    // BOTON PARA LISTAR AVIONES
    @FXML
    void onClickBtnListaAviones(ActionEvent event) {
        try {
            Stage stage = (Stage) btnListarAviones.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/operadorViews/lista-aviones-view.fxml");
        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    // BOTON CARGAR EL COMBUSTIBLE
    @FXML
    void onClickBtnCargarCombustible(ActionEvent event) {
        try {
            Stage stage = (Stage) btnCargarCombustible.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/operadorViews/cargar-combustible-view.fxml");
        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    // BOTON CREAR AVION
    @FXML
    void onClickBtnCrearAvion(ActionEvent event) {
        try {
            Stage stage = (Stage) btnCrearAvion.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/operadorViews/crear-avion-view.fxml");
        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnVolver(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage,"/com/utn/hangar/operadorViews/operador-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }
}
