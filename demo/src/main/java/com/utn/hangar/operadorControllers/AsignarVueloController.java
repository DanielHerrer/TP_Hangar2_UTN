package com.utn.hangar.operadorControllers;

import com.utn.hangar.Ventanas;
import entidades.Avion;
import entidades.Piloto;
import enums.Genero;
import excepciones.CombustibleInsuficienteExcepcion;
import excepciones.NoDisponibleException;
import gestores.GestorAviones;
import gestores.GestorHangar;
import gestores.GestorPilotos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AsignarVueloController {

    @FXML
    private Button btnAsignar;

    @FXML
    private ComboBox<Avion> btnSelecAvion;

    @FXML
    private ComboBox<Piloto> btnSelecPiloto;

    @FXML
    private Button btnVolver;


    @FXML
    public void initialize() {
        GestorPilotos gestorPilotos = new GestorPilotos();
        gestorPilotos.cargarPilotoDesdeArchivo();
        btnSelecPiloto.getItems().setAll(gestorPilotos.getListaPilotos()); // GUARDO A LOS PILOTOS EN EL COMBOBOX

        GestorAviones gestorAviones = new GestorAviones();
        gestorAviones.cargarAvionDesdeArchivo();
        btnSelecAvion.getItems().setAll(gestorAviones.getListaAviones()); // GUARDO LOS AVIONES EN EL COMBOBOX
    }

    @FXML
    void onClickBtnAsignar(ActionEvent event) {
        try {
            Piloto pilotoSeleccionado = btnSelecPiloto.getSelectionModel().getSelectedItem();
            Avion avionSeleccionado = btnSelecAvion.getSelectionModel().getSelectedItem();

            // METO VERIFICACIONES
            if (!pilotoSeleccionado.isDisponible()) {
                throw new NoDisponibleException("El piloto ya esta asignado a un avion");
            }
            if (avionSeleccionado.getCombustibleActual() < 80) {
                throw new CombustibleInsuficienteExcepcion("El combustible del avion esta por debajo del requerido");
            }
            if (avionSeleccionado.getPiloto() != null) {
                throw new NoDisponibleException("El avion ya posee un piloto asignado");
            }

            pilotoSeleccionado.setDisponible(false);

            // LE AGREGO EL PILOTO AL AVION
            avionSeleccionado.setPiloto(pilotoSeleccionado);

            //LLAMO A LA GESTORA DE HANGAR Y GUARDO EL VUELO SELECCIONADO
            GestorHangar gestorHangar = new GestorHangar();
            gestorHangar.cargarHangarDesdeArchivo();
            gestorHangar.agregar(avionSeleccionado);
            gestorHangar.guardarHangarToFile(); //GUARDO EL VUELO EN EL ARCHIVO JSON DE HANGAR

            //TAMBIEN GUARDO LA INFORMACION EN AVION Y EN PILOTO
            GestorPilotos gestorPilotos = new GestorPilotos();
            gestorPilotos.cargarPilotoDesdeArchivo();
            gestorPilotos.guardarPilotoToFile();
            GestorAviones gestorAviones = new GestorAviones();
            gestorPilotos.cargarPilotoDesdeArchivo();
            gestorAviones.guardarAvionToFile();

            Stage stage = (Stage) btnAsignar.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/operadorViews/menu-hangar-view.fxml");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Vuelo asignado!");
            alert.setHeaderText(null);
            alert.setContentText("Vuelo asignado exitosamente. Ya puede verlo en el Hangar");
            alert.showAndWait();

        }
        catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }

    }

    @FXML
    void onClickBtnVolver(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage,"/com/utn/hangar/operadorViews/menu-hangar-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}

