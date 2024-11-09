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

            // VALIDACIONES
            if (inputNomApe.getText().isBlank()) {
                throw new InputMismatchException("Ingrese un nombre y apellido.");
            }
            if (inputDNI.getText().isBlank()) {
                throw new InputMismatchException("Ingrese un DNI");
            }
            if (inputAnioNac.getText().isBlank()) {
                throw new InputMismatchException("Ingrese un año de nacimiento.");
            }
            if (gen == null){
                throw new InputMismatchException("Ingrese un genero");
            }

            //SE VALIDA QUE EL NUMERO DE LICENCIA NO ESTE REPETIDO
            if (gestorPilotos.verificarNumeroLicencia(licencia)) {
                throw new InputMismatchException("La licencia del piloto ya existe");
            }
            //TAMBIEN SE COMPRUEBA QUE NO SE REPITA EL DNI
            if (gestorPilotos.dniYaExiste(dni)) {
                throw new InputMismatchException("El DNI ya está registrado.");
            }
            //SE VALIDA EL AÑO DE NACIMIENTO DEL USUARIO
            if(anioNasc < 1899){
                throw new InputMismatchException("El año ingresado no es valido");
            } else if(anioNasc > (LocalDateTime.now().getYear() - 18)) {
                throw new InputMismatchException("El piloto debe poseer al menos 18 años.");
            }
            // (AGREGAR VALIDACIONES PRA LAS HORAS DE VUELO)

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
            Ventanas.cambioEscena("Sistema Hangar 2.0", stage, "/com/utn/hangar/operadorControllers/cuartel-pilotos.fxml");

        } catch (IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }
}
