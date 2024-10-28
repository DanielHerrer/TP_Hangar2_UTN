package com.utn.hangar;

import entidades.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import control.ControlUsuarios;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Objects;

public class LoginController {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnVolver;

    @FXML
    private PasswordField inputPass;

    @FXML
    private TextField inputUser;

    @FXML
    void onClickBtnLogin(ActionEvent event) {
        // AGARRO EL TEXTO DE LOS CAMPOS TextField
        String user = inputUser.getText();
        String pass = inputPass.getText();

        try {
            // COMPROBACION SI LOS CAMPOS ESTAN VACIOS
            if (user.isBlank()) {
                throw new InputMismatchException("Ingrese su usuario.");
            }
            if (pass.isBlank()) {
                throw new InputMismatchException("Ingrese su clave.");
            }

            ControlUsuarios conUsuario = new ControlUsuarios();
            conUsuario.cargarUsuarioDesdeArchivo("usuarios.json");

            Usuario usuarioLogeado = conUsuario.verificarUsuarioLogin(user, pass);

            boolean loginExitoso = false;
            if (Objects.equals(usuarioLogeado.getNombreUsuario(), inputUser.getText())) {
                loginExitoso = true;
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                if (usuarioLogeado.getRol() == 2) {
                    Ventanas.cambioEscena("Sistema Hangar 2.0 (Administrador)", stage, "/com/utn/hangar/admin-view.fxml");
                } else if (usuarioLogeado.getRol() == 1) {
                    Ventanas.cambioEscena("Sistema Hangar 2.0 (Operador)", stage, "/com/utn/hangar/operador-view.fxml");
                } else if (usuarioLogeado.getRol() == 0) {
                    Ventanas.cambioEscena("Sistema Hangar 2.0 (Invitado)", stage, "/com/utn/hangar/invitado-view.fxml");
                }
            }

            if (!loginExitoso) {
                throw new InputMismatchException("Credenciales incorrectas.");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnVolver(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage,"/com/utn/hangar/home-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }
}