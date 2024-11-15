package com.utn.hangar.homeControllers;

import com.utn.hangar.Ventanas;
import constantes.Data;
import entidades.Usuario;
import excepciones.FormatoIncorrectoException;
import excepciones.UsuarioDeBajaException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import gestores.GestorUsuarios;

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
    private Hyperlink linkRegister;

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

            //LLAMA A CLASE GESTORA Y TRAE A LOS USUARIOS DEL JSON
            GestorUsuarios conUsuario = new GestorUsuarios();
            conUsuario.cargarDesdeArchivo();
            //VERIFICA SI EL USUARIO INGRESADO ESTA EN EL JSON
            Usuario usuarioLogeado = null;
            usuarioLogeado = conUsuario.verificarUsuarioLogin(user, pass);

            if (usuarioLogeado == null) {
                throw new FormatoIncorrectoException("Credenciales incorrectas");
            }


            //SI SE ENCONTRO AL USUARIO, SE PASA AL MENU CORRESPONDIENTE
            if (usuarioLogeado.getAlta() == 1) {
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                // SETEAR USUARIO LOGUEADO
                Data.setUserLogueado(usuarioLogeado);
                // ENVIAR A LA VENTANA CORRESPONDIENTE SEGUN EL USUARIO
                if (usuarioLogeado.getRol() == 2) {
                    Ventanas.cambioEscena("Sistema Hangar 2.0 (Administrador)", stage, "/com/utn/hangar/adminViews/admin-view.fxml");
                } else if (usuarioLogeado.getRol() == 1) {
                    Ventanas.cambioEscena("Sistema Hangar 2.0 (Operador)", stage, "/com/utn/hangar/operadorViews/operador-view.fxml");
                } else if (usuarioLogeado.getRol() == 0) {
                    Ventanas.cambioEscena("Sistema Hangar 2.0 (Invitado)", stage, "/com/utn/hangar/invitadoViews/invitado-view.fxml");
                }
            } // SI NO SE ENCONTRO, SE ARROJA LA SIGUIENTE EXCEPCION
            else {
                throw new UsuarioDeBajaException("Su usuario ha sido dado de baja");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    @FXML
    void onClickLinkRegister(ActionEvent event) {
        try {
            Stage stage = (Stage) linkRegister.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/homeViews/register-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnVolver(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/home-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }
}