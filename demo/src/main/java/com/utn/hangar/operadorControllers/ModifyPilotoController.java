package com.utn.hangar.operadorControllers;

import com.utn.hangar.Ventanas;
import constantes.Data;
import entidades.Piloto;
import enums.Genero;
import gestores.GestorAviones;
import gestores.GestorHangar;
import gestores.GestorPilotos;
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

public class ModifyPilotoController {

    @FXML
    private ComboBox<Genero> SelecGen;

    @FXML
    private Button btnSavePerfil;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField inputAnioNac;

    @FXML
    private TextField inputDNI;

    @FXML
    private TextField inputHorasVuelo;

    @FXML
    private TextField inputLicencia;

    @FXML
    private TextField inputNomApe;

    @FXML
    public void initialize() {
        SelecGen.getItems().setAll(Genero.values());
        // TRAE EL PILOTO SELECCIONADO
        GestorPilotos gestorPilotos = new GestorPilotos();
        gestorPilotos.cargarDesdeArchivo();
        Piloto piloto = gestorPilotos.getPilotoPorID(Data.getIdAux()); // RETORNA EL PILOTO SEGUN EL ID AUX
        // SETEA LA INFORMACION
        inputNomApe.setText(piloto.getNombreApellido());
        inputAnioNac.setText(Integer.toString(piloto.getAnioNacimiento()));
        inputDNI.setText(piloto.getDni());
        SelecGen.setValue(piloto.getGenero());
        inputLicencia.setText(piloto.getNumeroLicencia());
        inputHorasVuelo.setText(String.valueOf(piloto.getHorasVuelo()));
    }

    @FXML
    void onClickBtnSavePerfil(ActionEvent event) {
        try {
            // Recupera los datos de los campos de entrada
            String dni = inputDNI.getText();
            String nombreApellido = inputNomApe.getText();
            Genero genero = SelecGen.getValue();
            String anioNacimientoStr = inputAnioNac.getText();
            int anioNacimiento = Integer.parseInt(anioNacimientoStr);
            String numeroLicencia = inputLicencia.getText();
            int horasVuelo = Integer.parseInt(inputHorasVuelo.getText());

            // VALIDA QUE NO HAYA CAMPOS VACIOS
            if (dni.isEmpty() || nombreApellido.isEmpty() || genero == null || anioNacimientoStr.isEmpty() || numeroLicencia.isEmpty() || horasVuelo < 0) {
                throw new InputMismatchException("Complete todos los campos");
            }
            // VALIDA QUE LOS DATOS ESTEN BIEN INGRESADOS
            if (nombreApellido.length() < 3) {
                throw new InputMismatchException("El nombre y apellido deben poseer al menos 3 caracteres.");
            }
            if (dni.length() < 6) {
                throw new InputMismatchException("El DNI no puede poseer menos de 6 caracteres.");
            }
            if(anioNacimiento < 1899){
                throw new InputMismatchException("El año ingresado no es valido");
            } else if(anioNacimiento > (LocalDateTime.now().getYear() - 18)) {
                throw new InputMismatchException("El usuario debe poseer al menos 18 años.");
            }
            if (numeroLicencia.length() < 5 || numeroLicencia.length() > 10) {
                throw new InputMismatchException("El numero de licencia debe tener entre 5 y 10 caracteres");
            }

            // TRAE EL PILOTO SELECCIONADO
            GestorPilotos gestorPilotos = new GestorPilotos();
            gestorPilotos.cargarDesdeArchivo();
            Piloto piloto = gestorPilotos.getPilotoPorID(Data.getIdAux());

            //SE VALIDA QUE EL DNI NO ESTE REPETIDO
            if (gestorPilotos.comprobarDniModificacion(dni, piloto)) {
                throw new InputMismatchException("El DNI del piloto ya existe");
            }
            //TAMBIEN SE COMPRUEBA QUE EL NUMERO DE LICENCIA NO SE REPITA
            if (gestorPilotos.verificarLicenciaModificacion(numeroLicencia, piloto)) {
                throw new InputMismatchException("El numero de licencia ya está registrado.");
            }

            // SETEA LOS DATOS DEL USUARIO
            piloto.setDni(dni);
            piloto.setNombreApellido(nombreApellido);
            piloto.setGenero(genero);
            piloto.setAnioNacimiento(anioNacimiento);
            piloto.setNumeroLicencia(numeroLicencia);
            piloto.setHorasVuelo(horasVuelo); //EL RANGO SE ACTUALIZA AUTOMATICAMENTE

            // Guarda los cambios en el archivo JSON
            gestorPilotos.guardarEnArchivo();

            // TAMBIEN DEBO GUARDARLO EN EL ARCHIVO DE AVIONES Y EN EL HANGAR
            GestorHangar gestorHangar = new GestorHangar();
            gestorHangar.cargarDesdeArchivo();
            gestorHangar.actualizarPilotoModificado(piloto);
            gestorHangar.guardarEnArchivo();

            GestorAviones gestorAviones = new GestorAviones();
            gestorAviones.cargarDesdeArchivo();
            gestorHangar.actualizarPilotoModificado(piloto);
            gestorHangar.guardarEnArchivo();

            Stage stage = (Stage) btnSavePerfil.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/operadorViews/cuartel-pilotos-view.fxml");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modificacion exitosa");
            alert.setHeaderText(null);
            alert.setContentText("El piloto ha sido modificado con exito.");
            alert.showAndWait();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de entrada");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, ingrese solo números en los campos de año de nacimiento y horas de vuelo.");
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
            Ventanas.cambioEscena("Sistema Hangar 2.0", stage, "/com/utn/hangar/operadorViews/cuartel-pilotos-view.fxml");

        } catch (IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}

