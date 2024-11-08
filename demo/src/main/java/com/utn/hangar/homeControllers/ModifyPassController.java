package com.utn.hangar.homeControllers;
import com.utn.hangar.Ventanas;
import constantes.Archivos;
import control.ControlUsuarios;
import entidades.Usuario;
import enums.Genero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.InputMismatchException;

public class ModifyPassController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSavePass;

    @FXML
    private PasswordField inputPass;

    @FXML
    private PasswordField inputPassConfirm;

    @FXML
    private TextField inputUser;

    @FXML
    public void initialize() {
        // TRAE EL USUARIO LOGEADO ACTUALMENTE
        ControlUsuarios controlUsuarios = new ControlUsuarios();
        controlUsuarios.cargarUsuarioDesdeArchivo();
        Usuario userLog = controlUsuarios.obtenerUsuarioLogueado(Archivos.userLogueado);
        // SETEA LA INFORMACION
        inputUser.setText(userLog.getNombreUsuario());
        inputPass.setText(userLog.getContrasenia());
        inputPassConfirm.setText(userLog.getContrasenia());
    }

    // BOTON GUARDAR USUARIO Y CONTRASENIA NUEVAS
    @FXML
    void onClickBtnSavePass(ActionEvent event) {
        try {
            String username = inputUser.getText();
            String password = inputPass.getText();
            String passConfirm = inputPassConfirm.getText();

            // VALIDA QUE NO HAYA CAMPOS VACIOS
            if (username.isEmpty() || password.isEmpty() || passConfirm.isEmpty()) {
                throw new InputMismatchException("Complete todos los campos");
            }

            // VALIDA QUE LOS DATOS ESTEN BIEN INGRESADOS
            if (username.length() < 3) {
                throw new InputMismatchException("El usuario debe poseer al menos 3 caracteres.");
            }
            if (password.length() < 6) {
                throw new InputMismatchException("La contraseña debe poseer al menos 6 caracteres.");
            }
            if (!password.equals(passConfirm)) {
                throw new InputMismatchException("Las contraseñas no coinciden");
            }

            // TRAE EL USUARIO SELECCIONADO
            ControlUsuarios conUser = new ControlUsuarios();
            conUser.cargarUsuarioDesdeArchivo();
            Usuario userLog = conUser.obtenerUsuarioLogueado(Archivos.getUserLogueado());

            //SE VALIDA QUE EL NOMBRE DE USUARIO NO ESTE REPETIDO
            if (!conUser.compronbarUsernameModificacion(username, userLog)) {
                throw new InputMismatchException("El nombre de usuario ya existe");
            }

            // SETEA LOS DATOS DEL USUARIO
            userLog.setNombreUsuario(username);
            userLog.setContrasenia(password);

            // Guarda los cambios en el archivo JSON
            conUser.guardarUsuarioToFile();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modificacion exitosa");
            alert.setHeaderText(null);
            alert.setContentText("El usuario ha sido modificado con exito.");
            alert.showAndWait();
        }
        catch (Exception e)
        {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnCancel(ActionEvent event) {
        try {
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/homeViews/modificar-perfil-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}
