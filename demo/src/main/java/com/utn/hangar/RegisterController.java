package com.utn.hangar;

import controlJSON.LeerJSON;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
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
            JSONArray usuarios = leerJSON.cargarUsuarios(); // Cargar el JSONArray de usuarios

            if (usuarios == null) {
                throw new Exception("Error al cargar archivo de usuarios");
            }

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

            // COMPROBAR QUE NO HAYA UN USER REPETIDO
            for (int i = 0; i < usuarios.length(); i++) {
                JSONObject usuario = usuarios.getJSONObject(i);
                String nameUser = usuario.getString("user");

                if (nameUser.equals(user)) {
                    throw new InputMismatchException("El nombre de usuario ya existe");
                }
            }

            // Crear el nuevo usuario y añadirlo al JSONArray
            JSONObject nuevoUser = new JSONObject();
            nuevoUser.put("user", user);
            nuevoUser.put("pass", pass);
            nuevoUser.put("rol", 0); // Rol de invitado
            usuarios.put(nuevoUser); // Agregar el nuevo usuario

            // Crear un nuevo JSONObject que contendrá el JSONArray de usuarios
            JSONObject data = new JSONObject();
            data.put("usuarios", usuarios);

            // Guardar el JSON completo en el archivo
            URL resourceUrl = getClass().getResource("/archiJSON/usuarios.json");
            if (resourceUrl != null) {
                try (FileWriter file = new FileWriter(new File(resourceUrl.toURI()))) {
                    file.write(data.toString(4)); // Guardar el JSON con el nuevo usuario
                }
            } else {
                System.out.println("El archivo usuarios.json no se encontró.");
            }

            // Mostrar mensaje de confirmación al usuario
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro Exitoso");
            alert.setHeaderText(null);
            alert.setContentText("Registro exitoso. Nuevo usuario creado!");
            alert.showAndWait();

        } catch (Exception e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
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
