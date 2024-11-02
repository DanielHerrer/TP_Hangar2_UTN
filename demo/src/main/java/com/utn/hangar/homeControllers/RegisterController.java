package com.utn.hangar.homeControllers;


import com.utn.hangar.Ventanas;
import constantes.Archivos;
import control.ControlRegistros;
import entidades.Usuario;
import enums.Genero;
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
import control.ControlUsuarios;

public class RegisterController {

    @FXML
    private Button btnRegister;

    @FXML
    private ComboBox<Genero> SelecGen;

    @FXML
    private TextField inputAnioNac;

    @FXML
    private TextField inputDNI;

    @FXML
    private TextField inputNomApe;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField inputPass;

    @FXML
    private TextField inputPassConfirm;

    @FXML
    private TextField inputUser;

    @FXML
    public void initialize() {
        SelecGen.getItems().setAll(Genero.values());
    }

    @FXML
    void onClickBtnRegister(ActionEvent event) {
        try {
            String completeName = inputNomApe.getText();
            String dni = inputDNI.getText();
            int anioNasc = Integer.parseInt(inputAnioNac.getText());
            Genero gen = SelecGen.getSelectionModel().getSelectedItem();
            String user = inputUser.getText();
            String pass = inputPass.getText();
            String passConfirm = inputPassConfirm.getText();

            //SE TRAEN TODOS LOS USUARIOS DEL JSON Y SE GUARDAN EN LA LISTA DE LA CLASE GESTORA
            ControlUsuarios conUsuarios = new ControlUsuarios();
            conUsuarios.cargarUsuarioDesdeArchivo();

            // VALIDACIONES
            if (inputNomApe.getText().isBlank()) {
                throw new InputMismatchException("Ingrese un nombre y apellido.");
            }
            if (inputUser.getText().isBlank()) {
                throw new InputMismatchException("Ingrese un nombre de usuario.");
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
            if (user.isBlank() || user.length() < 3) {
                throw new InputMismatchException("El usuario debe poseer al menos 3 caracteres.");
            }
            if (pass.isBlank() || pass.length() < 6) {
                throw new InputMismatchException("La contraseña debe poseer al menos 6 caracteres.");
            }
            if (!pass.equals(passConfirm)) {
                throw new InputMismatchException("Las contraseñas ingresadas no coinciden.");
            }

            //SE VALIDA QUE EL NOMBRE DE USUARIO NO ESTE REPETIDO
            if (conUsuarios.usuarioYaExiste(user)) {
                throw new InputMismatchException("El nombre de usuario ya existe");
            }
            //TAMBIEN SE COMPRUEBA QUE NO SE REPITA EL DNI
            if (conUsuarios.dniYaExiste(dni)) {
                throw new InputMismatchException("El DNI ya está registrado.");
            }
            //SE VALIDA EL AÑO DE NACIMIENTO DEL USUARIO
            if(anioNasc < 1899){
                throw new InputMismatchException("El año ingresado no es valido");
            } else if(anioNasc > (LocalDateTime.now().getYear() - 18)) {
                throw new InputMismatchException("El usuario debe poseer al menos 18 años.");
            }

            //CON LOS DATOS PEDIDOS ANTERIORMENTE SE INSTANCIA UN USUARIO
            Usuario usuario1 = new Usuario(dni,completeName, gen, anioNasc, user, pass);
            //SE AGREGA EL USUARIO AL ARREGLO DE LA CLASE GESTORA
            conUsuarios.agregar(usuario1);
            //Y SE GUARDA EL CONTENIDO DEL ARREGLO EN EL JSON
            conUsuarios.guardarUsuarioToFile();

            //TAMBIEN SE GUARDA LA INFORMACION DEL REGISTRO EN EL JSON DE REGISTROS
            ControlRegistros controlRegistros = new ControlRegistros();
            controlRegistros.cargarRegistrosDesdeArchivo();
            controlRegistros.guardarRegistro(usuario1);
            controlRegistros.guardarRegistrosEnArchivo();


            Stage stage = (Stage) btnRegister.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/homeViews/login-view.fxml");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro exitoso");
            alert.setHeaderText(null);
            alert.setContentText("Usuario registrado exitosamente.");
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
            Ventanas.cambioEscena("Sistema Hangar 2.0", stage, "/com/utn/hangar/home-view.fxml");

        } catch (IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }


}
