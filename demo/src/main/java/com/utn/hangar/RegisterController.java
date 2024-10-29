package com.utn.hangar;


import constantes.Archivos;
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
import java.util.InputMismatchException;
import control.ControlUsuarios;

public class RegisterController {

    @FXML
    private Button btnRegister;

    @FXML
    private ComboBox<Genero> SelecGen;

    @FXML
    private TextField inputAñoNas;

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
            String archivoUsuarios = "usuarios.json";
            String completeName = inputNomApe.getText();
            String dni = inputDNI.getText();
            int anioNasc = Integer.parseInt(inputAñoNas.getText());
            Genero gen = SelecGen.getSelectionModel().getSelectedItem();
            String user = inputUser.getText();
            String pass = inputPass.getText();
            String passConfirm = inputPassConfirm.getText();

            // VALIDACIONES

            if (inputUser.getText().isBlank()) {
                throw new InputMismatchException("Ingrese un nombre de usuario.");
            }
            if (!pass.equals(passConfirm)) {
                throw new InputMismatchException("Las contraseñas ingresadas no coinciden.");
            }
            if (user.isBlank() || user.length() < 3) {
                throw new InputMismatchException("El usuario debe poseer al menos 3 caracteres.");
            }
            if (pass.isBlank() || pass.length() < 6) {
                throw new InputMismatchException("La contraseña debe poseer al menos 6 caracteres.");
            }
            if (inputNomApe.getText().isBlank()) {
                throw new InputMismatchException("Ingrese un nombre de apellido.");
            }
            if (inputDNI.getText().isBlank()) {
                throw new InputMismatchException("Ingrese un DNI");
            }
            if (inputAñoNas.getText().isBlank()) {
                throw new InputMismatchException("Ingrese un año de nascimento.");
            }
            //SE TRAEN TODOS LOS USUARIOS DEL JSON Y SE GUARDAN EN LA LISTA DE LA CLASE GESTORA
            ControlUsuarios conUsuarios = new ControlUsuarios();
            conUsuarios.cargarUsuariosDesdeArchivo(Archivos.archivoUsuarios);

            // COMPROBAR QUE NO HAYA UN USER REPETIDO
            if (conUsuarios.usuarioYaExiste(user)) {
                throw new InputMismatchException("El nombre de usuario ya existe");
            }

            //PARA QUE FUNCIONE LA VALIDACION, FALTA ARREGLAR EL METODO dniYaExiste EN LA CLASE ControlUsuario!
            if (conUsuarios.dniYaExiste(dni)) {
                throw new InputMismatchException("El DNI ya está registrado.");
            }

            //CON LOS DATOS PEDIDOS ANTERIORMENTE SE INSTANCIA UN USUARIO
            Usuario usuario1 = new Usuario(dni,completeName, gen, anioNasc, user, pass);
            //SE AGREGA EL USUARIO AL ARREGLO DE LA CLASE GESTORA
            conUsuarios.agregar(usuario1);
            //Y SE GUARDA EL CONTENIDO DEL ARREGLO EN EL JSON
            conUsuarios.guardarUsuarioToFile(Archivos.archivoUsuarios);

            Stage stage = (Stage) btnRegister.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage,"/com/utn/hangar/login-view.fxml");

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
