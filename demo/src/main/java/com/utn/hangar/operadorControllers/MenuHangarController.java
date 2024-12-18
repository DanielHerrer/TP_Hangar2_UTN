package com.utn.hangar.operadorControllers;

import com.utn.hangar.Ventanas;
import com.utn.hangar.radar.RadarAviones;
import entidades.Avion;
import entidades.Piloto;
import gestores.GestorAviones;
import gestores.GestorHangar;
import gestores.GestorPilotos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Iterator;

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
            RadarAviones radarAviones = new RadarAviones();
            radarAviones.mostrarRadar();

            GestorHangar gestorHangar = new GestorHangar();
            gestorHangar.cargarDesdeArchivo();

            GestorAviones gestorAviones = new GestorAviones();
            gestorAviones.cargarDesdeArchivo();

            GestorPilotos gestorPilotos = new GestorPilotos();
            gestorPilotos.cargarDesdeArchivo();


            Iterator<Avion> iterator = gestorHangar.getListaHangar().iterator();
            while (iterator.hasNext()) {
                Avion avi = iterator.next();

                // Borra el avión de la lista del hangar
                iterator.remove();

                // Actualiza el estado y los atributos del avión
                gestorAviones.eliminarPilotoDeAvion(avi);
                Avion avionActualizado = gestorAviones.getAvionPorID(avi.getId());
                avionActualizado.consumirCombustible();
                avionActualizado.setVuelosRealizados(avionActualizado.getVuelosRealizados() + 1);

                // Actualiza el estado y los atributos del piloto
                gestorPilotos.actualizarEstadoPiloto(avi.getPiloto());
                Piloto pilotoActualizado = gestorPilotos.getPilotoPorID(avi.getPiloto().getId());
                pilotoActualizado.aumentarHorasVuelo();
            }


            gestorHangar.guardarEnArchivo();
            gestorAviones.guardarEnArchivo();
            gestorPilotos.guardarEnArchivo();

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


