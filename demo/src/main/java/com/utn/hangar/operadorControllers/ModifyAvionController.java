package com.utn.hangar.operadorControllers;

import com.utn.hangar.Ventanas;
import constantes.Data;
import entidades.Avion;
import entidades.Usuario;
import enums.Genero;
import gestores.GestorAviones;
import gestores.GestorHangar;
import gestores.GestorUsuarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.InputMismatchException;

public class ModifyAvionController {

    @FXML
    private TextField btnAerolinea;

    @FXML
    private TextField btnModelo;

    @FXML
    private Button btnModify;

    @FXML
    private TextField btnNombre;

    @FXML
    private TextField btnNumeracion;

    @FXML
    private TextField btnPasajeros;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField btnVuelosRealizados;

    @FXML
    public void initialize() {
        // TRAE EL AVION SELECCIONADO
        GestorAviones gestorAviones = new GestorAviones();
        gestorAviones.cargarAvionDesdeArchivo();
        Avion avion = gestorAviones.buscarAvionPorID(Data.getIdAux()); // RETORNA EL AVION SEGUN EL ID AUX
        // SETEA LA INFORMACION
        btnNombre.setText(avion.getNombre());
        btnNumeracion.setText(Integer.toString(avion.getNumeracion()));
        btnModelo.setText(avion.getModelo());
        btnAerolinea.setText(avion.getAerolinea());
        btnPasajeros.setText(Integer.toString(avion.getCapacidadPasajeros()));
        btnVuelosRealizados.setText(Integer.toString(avion.getVuelosRealizados()));
    }

    @FXML
    void onClickBtnModify(ActionEvent event) {
        try {
            String nombreAvion = btnNombre.getText();
            int numeracion = Integer.parseInt(btnNumeracion.getText());
            String modeloAvion = btnModelo.getText();
            String aerolineaAvion = btnAerolinea.getText();
            int capPasajeros = Integer.parseInt(btnPasajeros.getText());
            int vuelosRealizados = Integer.parseInt(btnVuelosRealizados.getText());

            // VALIDACIONES
            // VALIDA QUE NO HAYA CAMPOS VACIOS
            if (btnNombre.getText().isBlank() || btnNumeracion.getText().isBlank() || btnModelo.getText().isBlank() || btnAerolinea.getText().isBlank()
                || btnPasajeros.getText().isBlank() || btnVuelosRealizados.getText().isBlank()) {
                throw new InputMismatchException("Complete todos los campos");
            }
            if (capPasajeros > 320 || capPasajeros < 30) {
                throw new InputMismatchException("Capacidad de pasajeros invalida.");
            }
            if (vuelosRealizados < 0) {
                throw new InputMismatchException("Los vuelos realizados deben ser mayor o igual a cero");
            }
            if (btnNombre.getText().length() < 4) {
                throw new InputMismatchException("El nombre no posee suficientes caracteres");
            }
            if (btnNumeracion.getText().length() < 3) {
                throw new InputMismatchException("La numeracion no posee los suficientes caracteres");
            }
            if (btnAerolinea.getText().length() < 4) {
                throw new InputMismatchException("La aerolinea no posee suficientes caracteres");
            }
            if (btnModelo.getText().length() < 3) {
                throw new InputMismatchException("El modelo no posee los suficientes caracteres");
            }

            //SE TRAEN TODOS LOS AVIONES DEL JSON Y SE GUARDAN EN LA LISTA DE LA CLASE GESTORA
            GestorAviones gestorAviones = new GestorAviones();
            gestorAviones.cargarAvionDesdeArchivo();
            // TRAIGO AL AVION DESDE EL ARCHIVO JSON
            Avion avionSeleccionado = gestorAviones.buscarAvionPorID(Data.getIdAux());

            //SE VALIDA QUE LA NUMERACION NO ESTE REPETIDA
            if (gestorAviones.verificarNumeracionModifiacion(numeracion, avionSeleccionado)) {
                throw new InputMismatchException("La numeracion del avion ya existe.");
            }

            // SETEO LOS DATOS DEL AVION MODIFICADO
            avionSeleccionado.setNombre(nombreAvion);
            avionSeleccionado.setNumeracion(numeracion);
            avionSeleccionado.setModelo(modeloAvion);
            avionSeleccionado.setAerolinea(aerolineaAvion);
            avionSeleccionado.setCapacidadPasajeros(capPasajeros);
            avionSeleccionado.setVuelosRealizados(vuelosRealizados);


            //Y SE GUARDAN LOS CAMBIOS EN EL JSON
            gestorAviones.guardarAvionToFile();

            //TAMBIEN SE ACTUALIZAN LAS MODIFICACIONES CON LOS AVIONES DEL HANGAR
            GestorHangar gestorHangar = new GestorHangar();
            gestorHangar.cargarHangarDesdeArchivo();
            gestorHangar.agregarModificado(avionSeleccionado);
            gestorHangar.guardarHangarToFile();

            Stage stage = (Stage) btnModify.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/operadorViews/taller-aviones-view.fxml");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modificacion exitosa");
            alert.setHeaderText(null);
            alert.setContentText("Avion modificado exitosamente.");
            alert.showAndWait();

        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }


    @FXML
    void onClickBtnVolver(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0", stage, "/com/utn/hangar/operadorViews/taller-aviones-view.fxml");

        } catch (IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}

