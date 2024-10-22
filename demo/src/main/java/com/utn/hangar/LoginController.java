package com.utn.hangar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.InputMismatchException;

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
        // TRAIGO ESTA INFO DESDE EL JSON
        String user, adminUser = "admin";
        String pass, adminPass = "admin123";

        try {
            // COMPROBACION SI LOS CAMPOS ESTAN VACIOS
            if (inputUser.getText().isBlank()) {
                throw new InputMismatchException("Ingrese su usuario.");
            }
            if (inputPass.getText().isBlank()) {
                throw new InputMismatchException("Ingrese su clave.");
            }

            // AGARRO EL TEXTO DE LOS CAMPOS TextField
            user = inputUser.getText();
            pass = inputPass.getText();

            // LOGIN EXITOSO COMO ADMIN
            if (adminUser.equalsIgnoreCase(user) && adminPass.equals(pass)) {

                Stage stage = (Stage) btnLogin.getScene().getWindow();
                Ventanas.cambioEscena("Sistema Hangar 2.0 (Administrador)",stage, "/com/utn/hangar/admin-view.fxml");

            } else {
                throw new InputMismatchException("Credenciales incorrectas."); // Lanzar excepción personalizada
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