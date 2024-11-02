package com.utn.hangar.adminControllers;

import com.utn.hangar.Ventanas;
import constantes.Archivos;
import control.ControlUsuarios;
import entidades.Usuario;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class ListaUsuariosController {

    @FXML
    private Button btnVolver;

    @FXML
    private TableView<Usuario> tablaUsuarios;

    @FXML
    private TableColumn<Usuario, Integer> idUsuario;

    @FXML
    private TableColumn<Usuario, String> userUsuario;

    @FXML
    private TableColumn<Usuario, String> apellidoUsuario;

    @FXML
    private TableColumn<Usuario, String> rolUsuario;

    @FXML
    private TableColumn<Usuario, Void> altaUsuario;

    @FXML
    private TableColumn<Usuario, Void> modifyUsuario;

    @FXML
    public void initialize() {
        //LLAMA A CLASE GESTORA Y TRAE A LOS USUARIOS DEL JSON
        ControlUsuarios conUsuario = new ControlUsuarios();
        conUsuario.cargarUsuariosDesdeArchivo();

        // Convierte el ArrayList a ObservableList
        ObservableList<Usuario> listaObservableUsuarios = FXCollections.observableArrayList(conUsuario.getListaUsuarios());

        // Enlaza cada columna usando expresiones lambda
        idUsuario.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        apellidoUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreApellido()));
        userUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreUsuario()));

        //rolUsuario.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRol()));
        // Configura la columna rolUsuario para mostrar el texto correspondiente
        rolUsuario.setCellValueFactory(cellData -> {
            int rol = cellData.getValue().getRol();
            String rolTexto;
            switch (rol) {
                case 0: rolTexto = "INVITADO"; break;
                case 1: rolTexto = "OPERADOR"; break;
                case 2: rolTexto = "ADMIN"; break;
                default: rolTexto = "DESCONOCIDO"; break; // Para valores no esperados
            }
            return new SimpleStringProperty(rolTexto);
        });

        //altaUsuario.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAlta()));
        // Agrega el botón de "Estado" en cada fila de la columna estadoUsuario
        altaUsuario.setCellFactory(new Callback<TableColumn<Usuario, Void>, TableCell<Usuario, Void>>() {
            @Override
            public TableCell<Usuario, Void> call(final TableColumn<Usuario, Void> param) {
                return new TableCell<Usuario, Void>() {
                    private final Button btnEstado = new Button();

                    {
                        btnEstado.setMaxWidth(Double.MAX_VALUE);
                        btnEstado.setAlignment(Pos.CENTER);
                        // Define la acción del botón para alternar entre Alta y Baja
                        btnEstado.setOnAction((ActionEvent event) -> {
                            Usuario usuario = getTableView().getItems().get(getIndex());

                            // Alterna el estado entre 0 y 1
                            int nuevoEstado = usuario.getAlta() == 1 ? 0 : 1;
                            usuario.setAlta(nuevoEstado);

                            // Actualiza el botón en la interfaz
                            actualizarBotonEstado(btnEstado, nuevoEstado);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Usuario usuario = getTableView().getItems().get(getIndex());
                            int estadoActual = usuario.getAlta();

                            // Actualiza el botón según el estado actual del usuario
                            actualizarBotonEstado(btnEstado, estadoActual);
                            setGraphic(btnEstado);
                        }
                    }

                    // Método para configurar el botón de estado según el valor
                    private void actualizarBotonEstado(Button btn, int estado) {
                        if (estado == 1) {
                            btn.setText("Alta");
                            btn.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                        } else {
                            btn.setText("Baja");
                            btn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                        }
                    }
                };
            }
        });

        // Agrega el botón "Modificar" en cada fila de la columna modifyUsuario
        modifyUsuario.setCellFactory(new Callback<TableColumn<Usuario, Void>, TableCell<Usuario, Void>>() {
            @Override
            public TableCell<Usuario, Void> call(final TableColumn<Usuario, Void> param) {
                return new TableCell<Usuario, Void>() {
                    private final Button btnModificar = new Button("Modificar");
                    {
                        btnModificar.setMaxWidth(Double.MAX_VALUE);
                        btnModificar.setAlignment(Pos.CENTER);
                        btnModificar.setOnAction((ActionEvent event) -> {
                            try {
                                // OBTENER EL ID DEL USUARIO DE LA FILA
                                Usuario usuario = getTableView().getItems().get(getIndex());
                                Archivos.setIdAux(usuario.getId()); // SETEA EL ID AUX
                                // ABRIR VENTANA DE MODIFICACION
                                Stage stage = (Stage) btnVolver.getScene().getWindow();
                                Ventanas.cambioEscena("Sistema Hangar 2.0",stage,"/com/utn/hangar/adminViews/modificar-usuario-view.fxml");
                            } catch(IOException e) {
                                Ventanas.exceptionError(e);
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnModificar);
                        }
                    }
                };
            }
        });


        // Establece la lista observable como el elemento de la tabla
        tablaUsuarios.setItems(listaObservableUsuarios);
    }

    @FXML
    void onClickBtnVolver(ActionEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Ventanas.cambioEscena("Sistema Hangar 2.0",stage,"/com/utn/hangar/adminViews/admin-view.fxml");

        } catch(IOException e) {
            Ventanas.exceptionError(e);
            e.printStackTrace();
        }
    }

}
