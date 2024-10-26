package com.utn.hangar;

import control.ControlUsuarios;
import entidades.Usuario;
import enums.Genero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import archivoJSON.LeerJSON;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            LeerJSON leerJSON = new LeerJSON();
            JSONArray usuarios = leerJSON.cargarUsuarios();

            if (usuarios == null) {
                throw new Exception("Error al cargar archivo de usuarios");
            }

            //DECLARO VARIABLES PARA INSTANCIAR UN USUARIO
            String dni = inputUser.getText();
            String completeName = inputUser.getText();
            char genero = inputUser.getCharacters().charAt(0);
            Genero gen;
            //DE ACUERDO AL GENERO QUE INTRODUZCA EL USUARIO SE ELIGE EL ENUM CORRESPONDIENTE
            if (genero == 'M' || genero == 'm') {
                 gen = Genero.MASCULINO;
            }
            else {
                 gen = Genero.FEMENINO;
            }
            String anioNacimientoSTR = inputUser.getText();
            int anioNacimiento = Integer.parseInt(anioNacimientoSTR);
            String user = inputUser.getText();
            String pass = inputPass.getText();
            String passConfirm = inputPassConfirm.getText();

            //CON LOS DATOS PEDIDOS ANTERIORMENTE SE INSTANCIA UN USUARIO
            Usuario usuario1 = new Usuario(dni,completeName, gen, anioNacimiento, user, pass);
            //SE CREA UN OBJETO DE LA CLASE GESTORA
            ControlUsuarios conUsuarios = new ControlUsuarios();
            //SE AGREGA EL USUARIO AL ARREGLO DE LA CLASE GESTORA
            conUsuarios.agregar(usuario1);
            //Y SE GUARDA EL CONTENIDO DEL ARREGLO EN EL JSON
            conUsuarios.guardarUsuarioToFile("Usuarios-JSON");

            //VALIDACIONES
            if (inputUser.getText().isBlank()) {
                throw new InputMismatchException("Ingrese un nombre de usuario.");
            }
            if (!pass.equals(passConfirm)) {
                throw new InputMismatchException("Las contrasenias ingresadas no coinciden.");
            }
            if (user.isBlank() || user.length() < 3) {
                throw new InputMismatchException("El usuario debe poseer al menos 3 caracteres.");
            }
            if (pass.isBlank() || pass.length() < 6) {
                throw new InputMismatchException("La contrasenia debe poseer al menos 6 caracteres.");
            }

            //COMPROBAR QUE NO HAYA UN USER REPETIDO
            for (int i = 0; i < usuarios.length(); i++) {
                JSONObject usuario = usuarios.getJSONObject(i);
                String nameUser = usuario.getString("user");

                if (nameUser.equals(user)) {
                    throw new InputMismatchException("El nombre de usuario ya existe");
                }
            }

            JSONObject nuevoUser = new JSONObject();
            nuevoUser.put("user", user);
            nuevoUser.put("pass", pass);
            nuevoUser.put("rol", 0); //INVITADO

            usuarios.put(nuevoUser);
            //Files.write("/demo/src/main/resources/archivosJSON/usuarios.json"), usuarios.toString(4).getBytes();
            FileWriter file = new FileWriter("demo/src/main/resources/archivosJSON/usuarios.json");
            file.write(usuarios.toString(4));
            //Path agarra la ruta del archivo-Usuarios.toString es para pasar los datos en String al JSON, y el 4 es para 4 espacion el JSON


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro Exitoso");
            alert.setHeaderText(null);
            alert.setContentText("Registro exitoso. Nuevo usuario creado!");
            alert.showAndWait();

        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }

        /*try {

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
        }*/
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
