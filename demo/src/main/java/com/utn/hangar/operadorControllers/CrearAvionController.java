package com.utn.hangar.operadorControllers;

import com.utn.hangar.Ventanas;
import entidades.Avion;
import entidades.Usuario;
import enums.Genero;
import gestores.GestorAviones;
import gestores.GestorRegistros;
import gestores.GestorUsuarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.InputMismatchException;

public class CrearAvionController {
    @FXML
    private Button btnRegister;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField nombre;

    @FXML
    private TextField numeracion;

    @FXML
    private TextField modelo;

    @FXML
    private TextField aerolinea;

    @FXML
    private TextField capacidadPasajeros;


    @FXML
    public void initialize() {

    }

    @FXML
    void onClickBtnRegister(ActionEvent event) {
        try {
            String nombreAvion = nombre.getText();
            int numeracionInt = Integer.parseInt(numeracion.getText());
            String modeloAvion = modelo.getText();
            String aerolineaAvion = aerolinea.getText();
            int capPasajeros = Integer.parseInt(capacidadPasajeros.getText());

            //SE TRAEN TODOS LOS AVIONES DEL JSON Y SE GUARDAN EN LA LISTA DE LA CLASE GESTORA
            GestorAviones gestorAviones = new GestorAviones();
            gestorAviones.cargarAvionDesdeArchivo();

            // VALIDACIONES
            if (nombre.getText().isBlank()) {
                throw new InputMismatchException("Ingrese un nombre.");
            }
            if (numeracion.getText().isBlank()) {
                throw new InputMismatchException("Ingrese una numeracion.");
            }
            if (modelo.getText().isBlank()) {
                throw new InputMismatchException("Ingrese un modelo de avion.");
            }
            if (aerolinea.getText().isBlank()) {
                throw new InputMismatchException("Ingrese una aerolinea.");
            }
            if (capPasajeros > 300 || capPasajeros < 30) {
                throw new InputMismatchException("Capacidad de pasajeros invalida.");
            }
            // DESPUES AGREGAR VERIFICACIONES PARA LA CANTIDAD DE LETRAS

            //SE VALIDA QUE LA NUMERACION NO ESTE REPETIDA
            if (gestorAviones.verificarNumeracion(numeracionInt)) {
                throw new InputMismatchException("La numeracion del avion ya existe.");
            }

            //CON LOS DATOS PEDIDOS ANTERIORMENTE SE INSTANCIA UN AVION
            Avion avion = new Avion(nombreAvion, numeracionInt, modeloAvion, aerolineaAvion, capPasajeros);
            //SE AGREGA EL AVION AL ARREGLO DE LA CLASE GESTORA
            gestorAviones.agregar(avion);
            //Y SE GUARDA EL CONTENIDO DEL ARREGLO EN EL JSON
            gestorAviones.guardarAvionToFile();
/*
            Stage stage = (Stage) btnRegister.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/homeViews/login-view.fxml");
*/
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Creacion exitosa");
            alert.setHeaderText(null);
            alert.setContentText("Avion creado exitosamente.");
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
            Ventanas.cambioEscena("Sistema Hangar 2.0", stage, "/com/utn/hangar/operadorControllers/taller-aviones-view.fxml");

        } catch (IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }
}