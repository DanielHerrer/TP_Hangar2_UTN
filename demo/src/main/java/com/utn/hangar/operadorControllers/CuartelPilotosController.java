package com.utn.hangar.operadorControllers;

import com.utn.hangar.Ventanas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CuartelPilotosController {

    @FXML
    private Button btnListarPilotos;

    @FXML
    private Button btnCrearPiloto;

    @FXML
    private Button btnVolver;


    // BOTON PARA LISTAR PILOTOS
    @FXML
    void onClickBtnListaPilotos(ActionEvent event) {
        try {
            Stage stage = (Stage) btnListarPilotos.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/operadorViews/lista-pilotos-view.fxml");
        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    // BOTON CREAR AVION
    @FXML
    void onClickBtnCrearPiloto(ActionEvent event) {
        try {
            Stage stage = (Stage) btnCrearPiloto.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/operadorViews/crear-piloto-view.fxml");
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
