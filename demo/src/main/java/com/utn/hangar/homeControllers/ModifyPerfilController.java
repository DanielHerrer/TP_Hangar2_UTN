package com.utn.hangar.homeControllers;

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

public class ModifyPerfilController {

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
    private Hyperlink linkCambiarUserPass;

    @FXML
    public void initialize() {
        SelecGen.getItems().setAll(Genero.values());
        // TRAE EL USUARIO LOGEADO ACTUALMENTE
        Usuario userLog = Archivos.getUserLogueado();
        // SETEA LA INFORMACION
        inputNomApe.setText(userLog.getNombreApellido());
        inputAnioNac.setText(Integer.toString(userLog.getAnioNacimiento()));
        inputDNI.setText(userLog.getDni());
        SelecGen.setValue(userLog.getGenero());


    }

    // BOTON GUARDAR PERFIL
    @FXML
    void onClickBtnSavePerfil(ActionEvent event) {
        try {
            // Recupera los datos de los campos de entrada
            String dni = inputDNI.getText();
            String nombreApellido = inputNomApe.getText();
            Genero genero = SelecGen.getValue();
            String anioNacimientoStr = inputAnioNac.getText();
            int anioNacimiento = Integer.parseInt(anioNacimientoStr);

            // VALIDA QUE NO HAYA CAMPOS VACIOS
            if (dni.isEmpty() || nombreApellido.isEmpty() || genero == null || anioNacimientoStr.isEmpty()) {
                throw new InputMismatchException("Complete todos los campos");
            }
            if(anioNacimiento < 1899){
                throw new InputMismatchException("El año ingresado no es valido");
            } else if(anioNacimiento > (LocalDateTime.now().getYear() - 18)) {
                throw new InputMismatchException("El usuario debe poseer al menos 18 años.");
            }

            // TRAE EL USUARIO SELECCIONADO
            ControlUsuarios conUser = new ControlUsuarios();
            conUser.cargarUsuarioDesdeArchivo();
            Usuario userLog = conUser.obtenerUsuarioLogueado(Archivos.getUserLogueado());

            System.out.println(userLog.toString());

            //SE COMPRUEBA QUE NO SE REPITA EL DNI
            if (!conUser.comprobarDniModificacion(dni, userLog)) {
                throw new InputMismatchException("El DNI ya está registrado.");
            }

            // Modifico los datos del usuario
            userLog.setDni(dni);
            userLog.setNombreApellido(nombreApellido);
            userLog.setGenero(genero);
            userLog.setAnioNacimiento(anioNacimiento);

            System.out.println(conUser.listaUsuarios);

            // Guarda los cambios en el archivo JSON
            // (NO SE GUARDAN LOS CAMBIOS)
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
    void onClickLinkCambiarUserPass(ActionEvent event) {
        try {
            Stage stage = (Stage) linkCambiarUserPass.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage, "/com/utn/hangar/homeViews/modificar-pass-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnVolver(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            // TRAE EL USUARIO LOGEADO ACTUALMENTE
            Usuario userLog = Archivos.getUserLogueado();
            // COMPARA EL USUARIO LOGUEADO ACTUALMENTE PARA VOLVER A SU MENU CORRESPONDIENTE
            if (userLog.getRol() < 1) {
                Ventanas.cambioEscena("Sistema Hangar 2.0 (Invitado)",stage, "/com/utn/hangar/invitadoViews/invitado-view.fxml");
            } else if (userLog.getRol() == 1) {
                Ventanas.cambioEscena("Sistema Hangar 2.0 (Operador)",stage, "/com/utn/hangar/operadorViews/operador-view.fxml");
            } else if (userLog.getRol() > 1) {
                Ventanas.cambioEscena("Sistema Hangar 2.0 (Admin)",stage, "/com/utn/hangar/adminViews/admin-view.fxml");
            }

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}