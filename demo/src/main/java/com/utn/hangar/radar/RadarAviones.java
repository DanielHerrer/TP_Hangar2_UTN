package com.utn.hangar.radar;

import entidades.Avion;
import gestores.GestorHangar;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class RadarAviones {
    private static final int TAMANIO_RADAR = 550;
    private static final int TAMANIO_CELDA = 10;
    private static int NUM_AVIONES;
    private static final double PROBABILIDAD_CAMBIO_DIRECCION = 0.2;

    private AvionRadar[] aviones;
    private Random random = new Random();
    private Canvas canvas = new Canvas(TAMANIO_RADAR, TAMANIO_RADAR);
    private Canvas infoCanvas = new Canvas(220, 550);

    public RadarAviones() {
        // Cargar la cantidad de aviones al inicio
        GestorHangar gestorHangar = new GestorHangar();
        gestorHangar.cargarHangarDesdeArchivo();
        NUM_AVIONES = gestorHangar.getListaHangar().size();

        // Inicializar el arreglo de aviones con el número de aviones obtenido
        aviones = new AvionRadar[NUM_AVIONES];
    }

    public void mostrarRadar() {
        Stage radarStage = new Stage();

        iniciarAviones();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext infoGc = infoCanvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            moverAviones();
            dibujarRadar(gc);
            actualizarInfoAviones(infoGc);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        StackPane radarPane = new StackPane(canvas);
        radarPane.setPadding(new Insets(0, 20, 0, 0));

        BorderPane root = new BorderPane();
        root.setCenter(radarPane);
        root.setRight(infoCanvas);
        root.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(root, 1000, 600);
        radarStage.setTitle("Radar de Aviones");
        radarStage.setScene(scene);
        radarStage.show();
    }

    private void iniciarAviones() {
        int radioMayor = TAMANIO_RADAR / 2 / TAMANIO_CELDA;
        GestorHangar gestorHangar = new GestorHangar();
        gestorHangar.cargarHangarDesdeArchivo();

        ArrayList<String> nombreAvion = new ArrayList<>();
        for (Avion av : gestorHangar.getListaHangar()) {
            nombreAvion.add(av.getNombre());
        }

        for (int i = 0; i < NUM_AVIONES; i++) {
            int x, y;
            do {
                x = random.nextInt(2 * radioMayor) - radioMayor;
                y = random.nextInt(2 * radioMayor) - radioMayor;
            } while (x * x + y * y >= radioMayor * radioMayor);

            x += TAMANIO_RADAR / 2 / TAMANIO_CELDA;
            y += TAMANIO_RADAR / 2 / TAMANIO_CELDA;

            int dx = random.nextInt(3) - 1;
            int dy = random.nextInt(3) - 1;
            if (dx == 0 && dy == 0) dy = 1;
            Color color = Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());

            aviones[i] = new AvionRadar(nombreAvion.get(i) + (i + 1), x, y, dx, dy, color);
        }
    }

    private void moverAviones() {
        int radioMayor = TAMANIO_RADAR / 2 / TAMANIO_CELDA;
        int centro = TAMANIO_RADAR / 2 / TAMANIO_CELDA;

        for (AvionRadar avion : aviones) {
            if (avion.enVuelo) {
                if (random.nextDouble() < PROBABILIDAD_CAMBIO_DIRECCION) {
                    int cambioDireccion = random.nextInt(3) - 1;
                    if (random.nextBoolean()) {
                        avion.dx = cambioDireccion == 0 ? avion.dx : cambioDireccion;
                    } else {
                        avion.dy = cambioDireccion == 0 ? avion.dy : cambioDireccion;
                    }
                }

                avion.x += avion.dx;
                avion.y += avion.dy;

                int distanciaDesdeCentro = (int) Math.sqrt(Math.pow(avion.x - centro, 2) + Math.pow(avion.y - centro, 2));
                if (distanciaDesdeCentro >= radioMayor) {
                    avion.enVuelo = false;
                    avion.setEstado("Finalizado");
                } else {
                    avion.setEstado("En vuelo");
                }
            }
        }
    }

    private void dibujarRadar(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        int numCirculos = 6;
        for (int i = 1; i <= numCirculos; i++) {
            double radio = (TAMANIO_RADAR / 2.0) * (i / (double) numCirculos);
            gc.strokeOval((TAMANIO_RADAR - 2 * radio) / 2, (TAMANIO_RADAR - 2 * radio) / 2, 2 * radio, 2 * radio);
        }

        double centro = TAMANIO_RADAR / 2.0;
        gc.setLineWidth(3);
        double extension = 20;
        gc.strokeLine(centro, -extension, centro, TAMANIO_RADAR + extension);
        gc.strokeLine(-extension, centro, TAMANIO_RADAR + extension, centro);

        for (AvionRadar avion : aviones) {
            if (avion.enVuelo) {
                gc.setFill(avion.color);
                gc.fillOval(avion.y * TAMANIO_CELDA, avion.x * TAMANIO_CELDA, TAMANIO_CELDA, TAMANIO_CELDA);
            }
        }
    }

    private void actualizarInfoAviones(GraphicsContext gc) {
        gc.clearRect(0, 0, infoCanvas.getWidth(), infoCanvas.getHeight());

        double y = 20;

        for (AvionRadar avion : aviones) {
            gc.setFill(avion.color);
            gc.fillText("■", 5, y); // Movido a x = 5 para más espacio a la izquierda

            gc.setFill(Color.WHITE);
            gc.fillText(avion.getNombre() + " " + avion.getPosicion(), 25, y); // Movido a x = 25 para mejor alineación

            if (avion.enVuelo) {
                gc.setFill(Color.GREEN);
            } else {
                gc.setFill(Color.RED);
            }
            gc.fillText(avion.getEstado(), 150, y); // Movido a x = 170 para más espacio para nombres largos

            y += 25; // Aumentada la separación vertical a 25 para mayor claridad
        }
    }


    public static class AvionRadar {
        private String nombre;
        int x, y, dx, dy;
        Color color;
        private String estado;
        boolean enVuelo = true;

        public AvionRadar(String nombre, int x, int y, int dx, int dy, Color color) {
            this.nombre = nombre;
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
            this.color = color;
            this.estado = "En vuelo";
        }

        public String getNombre() {
            return nombre;
        }

        public String getPosicion() {
            return "(" + x + ", " + y + ")";
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public Color getColor() {
            return color;
        }
    }
}