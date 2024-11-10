package com.utn.hangar.operadorControllers;

import com.utn.hangar.Ventanas;
import entidades.Piloto;
import entidades.Usuario;
import enums.Genero;
import gestores.GestorPilotos;
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

public class CrearPilotoController {

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnVolver;


    @FXML
    private TextField inputNomApe;

    @FXML
    private TextField inputAnioNac;

    @FXML
    private TextField inputDNI;

    @FXML
    private ComboBox<Genero> SelecGen;

    @FXML
    private TextField numeroLicencia;

    @FXML
    private TextField horasVuelo;


    @FXML
    public void initialize() {
        SelecGen.getItems().setAll(Genero.values());
    }

    @FXML
    void onClickBtnRegister(ActionEvent event) {
        try {
            String nombre = inputNomApe.getText();
            String dni = inputDNI.getText();
            int anioNasc = Integer.parseInt(inputAnioNac.getText());
            Genero gen = SelecGen.getSelectionModel().getSelectedItem();
            String licencia = numeroLicencia.getText();
            int horasDeVuelo = Integer.parseInt(horasVuelo.getText());

            //SE TRAEN TODOS LOS PILOTOS DEL JSON Y SE GUARDAN EN LA LISTA DE LA CLASE GESTORA
            GestorPilotos gestorPilotos = new GestorPilotos();
            gestorPilotos.cargarPilotoDesdeArchivo();

            // VALIDA QUE NO HAYA CAMPOS VACIOS
            if (dni.isEmpty() || nombre.isEmpty() || gen == null || inputAnioNac.getText().isBlank() || licencia.isEmpty() || horasDeVuelo < 0) {
                throw new InputMismatchException("Complete todos los campos");
            }
            // VALIDA QUE LOS DATOS ESTEN BIEN INGRESADOS
            if (nombre.length() < 3) {
                throw new InputMismatchException("El nombre y apellido deben poseer al menos 3 caracteres.");
            }
            if (dni.length() < 6) {
                throw new InputMismatchException("El DNI no puede poseer menos de 6 caracteres.");
            }
            if(anioNasc < 1899){
                throw new InputMismatchException("El año ingresado no es valido");
            } else if(anioNasc > (LocalDateTime.now().getYear() - 18)) {
                throw new InputMismatchException("El usuario debe poseer al menos 18 años.");
            }
            if (licencia.length() < 5 || licencia.length() > 10) {
                throw new InputMismatchException("El numero de licencia debe tener entre 5 y 10 caracteres");
            }

            //SE VALIDA QUE EL NUMERO DE LICENCIA NO ESTE REPETIDO
            if (gestorPilotos.verificarLicencia(licencia)) {
                throw new InputMismatchException("La licencia del piloto ya existe");
            }
            //TAMBIEN SE COMPRUEBA QUE NO SE REPITA EL DNI
            if (gestorPilotos.dniYaExiste(dni)) {
                throw new InputMismatchException("El DNI ya está registrado.");
            }

            //CON LOS DATOS PEDIDOS ANTERIORMENTE SE INSTANCIA UN PILOTO
            Piloto piloto = new Piloto(dni, nombre, gen, anioNasc, licencia, horasDeVuelo);
            //SE AGREGA EL PILOTO AL ARREGLO DE LA CLASE GESTORA
            gestorPilotos.agregar(piloto);
            //Y SE GUARDA EL CONTENIDO DEL ARREGLO EN EL JSON
            gestorPilotos.guardarPilotoToFile();

            Stage stage = (Stage) btnRegister.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/operadorViews/cuartel-pilotos-view.fxml");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Creacion exitosa");
            alert.setHeaderText(null);
            alert.setContentText("Piloto registrado exitosamente.");
            alert.showAndWait();

        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    @FXML
    public String returnGenero(){
        return SelecGen.getSelectionModel().getSelectedItem().toString();
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
