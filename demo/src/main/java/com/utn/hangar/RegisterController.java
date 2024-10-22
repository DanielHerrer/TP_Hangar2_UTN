package com.utn.hangar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.InputMismatchException;

public class RegisterController {

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField inputPass;

    @FXML
    private TextField inputPassConfirm;

    @FXML
    private TextField inputUser;

    @FXML
    void onClickBtnRegister(ActionEvent event) {

        try {

            if (inputUser.getText().isBlank()) {
                throw new InputMismatchException("Ingrese un nombre de usuario.");
            }
            if (inputPass.getText().isBlank() || inputPassConfirm.getText().isBlank()) {
                throw new InputMismatchException("Ingrese ambas contrasenias.");
            }
            if (inputUser.getText().length() < 3) {
                throw new InputMismatchException("El usuario debe poseer al menos 3 caracteres.");
            }
            if (inputPass.getText().length() < 6) {
                throw new InputMismatchException("La contrasenia debe poseer al menos 6 caracteres.");
            }


            // COMPROBAR QUE NO HAYA UN USER REPETIDO, sino:
            // throw new InputMismatchException("El usuario ingresado ya se encuentra registrado.");


            String user = inputUser.getText();
            String pass = inputPass.getText();
            String passConfirm = inputPassConfirm.getText();

            if (!pass.equals(passConfirm)) {
                throw new InputMismatchException("Las contrasenias ingresadas no coinciden.");
            }

            /// REGISTRO EXITOSO


        //} catch (IOException e) {
            //throw new RuntimeException(e);

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
