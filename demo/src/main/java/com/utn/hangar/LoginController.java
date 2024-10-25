package com.utn.hangar;

import archivoJSON.LeerJSON;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

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
        //Trae la clase LeerJSON que tiene el metodo
        LeerJSON leerJSON = new LeerJSON();
        JSONArray usuarios = leerJSON.cargarUsuarios();

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

            //PARA CONTROLAR SI ENCONTRO O NO
            boolean loginExitoso = false;

            //RECORRE TODO EL ARRAY
            for (int i = 0; i < usuarios.length(); i++) {
                JSONObject usuario = usuarios.getJSONObject(i);
                String jsonUser = usuario.getString("user");
                String jsonPass = usuario.getString("pass");
                String rol = usuario.getString("rol");

                //VERIFICACION
                if (jsonUser.equalsIgnoreCase(user) && jsonPass.equals(pass)) {
                    loginExitoso = true;

                    //CAMBIAR PARA LA PANTALLA CORRECTA A DEPENDER DEL ROL(FALTA AGREGAR UN ROL DE USER INVITADO
                    // EN LA BASE DE DATOS Y PONER EL PROXIMO ELSE IF)
                    Stage stage = (Stage) btnLogin.getScene().getWindow();
                    if (rol.equalsIgnoreCase("2")) {
                        Ventanas.cambioEscena("Sistema Hangar 2.0 (Administrador)", stage, "/com/utn/hangar/admin-view.fxml");
                    } else if (rol.equalsIgnoreCase("1")) {
                        Ventanas.cambioEscena("Sistema Hangar 2.0 (Operador)", stage, "/com/utn/hangar/operador-view.fxml");
                    } else if (rol.equalsIgnoreCase("0")) {
                        Ventanas.cambioEscena("Sistema Hangar 2.0 (Invitado)", stage, "/com/utn/hangar/invitado-view.fxml");
                    }
                    break; //PARA SALIR DEL BUCLE
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