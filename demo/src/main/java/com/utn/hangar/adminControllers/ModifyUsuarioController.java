package com.utn.hangar.adminControllers;

import com.utn.hangar.Ventanas;
import constantes.Archivos;
import control.ControlUsuarios;
import entidades.Usuario;
import enums.Genero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.InputMismatchException;

public class ModifyUsuarioController {

    @FXML
    private ComboBox<Genero> SelecGen;

    @FXML
    private Button btnSavePerfil;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField inputAnioNac;

    @FXML
    private TextField inputDNI;

    @FXML
    private TextField inputNomApe;

    @FXML
    private PasswordField inputPass;

    @FXML
    private TextField inputUser;

    @FXML
    public void initialize() {
        SelecGen.getItems().setAll(Genero.values());
        // TRAE EL USUARIO SELECCIONADO
        ControlUsuarios conUser = new ControlUsuarios();
        conUser.cargarUsuarioDesdeArchivo();
        Usuario user = conUser.getUsuarioById(Archivos.getIdAux()); // RETORNA EL USUARIO SEGUN EL ID AUX
        // SETEA LA INFORMACION
        inputNomApe.setText(user.getNombreApellido());
        inputAnioNac.setText(Integer.toString(user.getAnioNacimiento()));
        inputDNI.setText(user.getDni());
        SelecGen.setValue(user.getGenero());
        inputUser.setText(user.getNombreUsuario());
        inputPass.setText(user.getContrasenia());

    }

    @FXML
    void onClickBtnSavePerfil(ActionEvent event) {
        try {
            // Recupera los datos de los campos de entrada
            String dni = inputDNI.getText();
            String nombreApellido = inputNomApe.getText();
            Genero genero = SelecGen.getValue();
            String anioNacimientoStr = inputAnioNac.getText();
            int anioNacimiento = Integer.parseInt(anioNacimientoStr);
            String username = inputUser.getText();
            String password = inputPass.getText();

            // VALIDA QUE NO HAYA CAMPOS VACIOS
            if (dni.isEmpty() || nombreApellido.isEmpty() || genero == null || anioNacimientoStr.isEmpty() || username.isEmpty() || password.isEmpty()) {
                throw new InputMismatchException("Complete todos los campos");
            }

            // VALIDA QUE LOS DATOS ESTEN BIEN INGRESADOS
            if (username.length() < 3) {
                throw new InputMismatchException("El usuario debe poseer al menos 3 caracteres.");
            }
            if (password.length() < 6) {
                throw new InputMismatchException("La contraseña debe poseer al menos 6 caracteres.");
            }
            if(anioNacimiento < 1899){
                throw new InputMismatchException("El año ingresado no es valido");
            } else if(anioNacimiento > (LocalDateTime.now().getYear() - 18)) {
                throw new InputMismatchException("El usuario debe poseer al menos 18 años.");
            }

            // TRAE EL USUARIO SELECCIONADO
            ControlUsuarios conUser = new ControlUsuarios();
            conUser.cargarUsuarioDesdeArchivo();
            Usuario user = conUser.getUsuarioById(Archivos.getIdAux());

            //SE VALIDA QUE EL NOMBRE DE USUARIO NO ESTE REPETIDO
            if (!conUser.compronbarUsernameModificacion(username, user)) {
                throw new InputMismatchException("El nombre de usuario ya existe");
            }
            //TAMBIEN SE COMPRUEBA QUE NO SE REPITA EL DNI
            if (!conUser.comprobarDniModificacion(dni, user)) {
                throw new InputMismatchException("El DNI ya está registrado.");
            }

            // SETEA LOS DATOS DEL USUARIO
            user.setDni(dni);
            user.setNombreApellido(nombreApellido);
            user.setGenero(genero);
            user.setAnioNacimiento(anioNacimiento);
            user.setNombreUsuario(username);
            user.setContrasenia(password);

            // Guarda los cambios en el archivo JSON
            conUser.guardarUsuarioToFile();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modificacion exitosa");
            alert.setHeaderText(null);
            alert.setContentText("El usuario ha sido modificado con exito.");
            alert.showAndWait();

        } catch (NumberFormatException e) {
            System.out.println("Año de nacimiento debe ser un número.");
        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnVolver(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/adminViews/lista-usuarios-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}
