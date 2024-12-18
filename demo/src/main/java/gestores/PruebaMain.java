package gestores;

import entidades.Avion;
import entidades.Piloto;
import enums.Genero;
import excepciones.FormatoIncorrectoException;
import excepciones.ObjetoRepetidoException;

public class PruebaMain {

    public static void main (String[] args) {

        // COSAS QUE HICE:
        // VENATANA PARA LISTAR USUARIOS MENU INVITADO
        // CLASE PARA GUARDAR LOS VUELOS ASIGANDOS CON UN ID


        Piloto pil1 = new Piloto("2233", "Franco Colapinto", Genero.MASCULINO, 2000, "43", 200);
        Piloto pil2 = new Piloto("78554", "Valtteri Bottas", Genero.MASCULINO, 1992, "77", 900);

        GestorPilotos gestorPilotos = new GestorPilotos();

        try {
            gestorPilotos.agregar(pil1);
        }
        catch (ObjetoRepetidoException e) {
            e.printStackTrace();
        } catch (FormatoIncorrectoException e) {
            throw new RuntimeException(e);
        }

        try {
            gestorPilotos.agregar(pil2);
        } catch (ObjetoRepetidoException | FormatoIncorrectoException e) {
            System.out.println(e.getMessage());
        }

        gestorPilotos.guardarEnArchivo();

        Avion avi1 = new Avion("Albano", 89, "x-e89", "Latam", 500);

        GestorAviones gestorAviones = new GestorAviones();

        gestorAviones.listaAviones.add(avi1);

        gestorAviones.guardarEnArchivo();

        // SIMULACION DEL MENU DE HANGAR ASIGNAR VUELO
        GestorHangar gestorHangar = new GestorHangar();

        // CARGO TODAS LAS LISTAS
        gestorHangar.cargarDesdeArchivo();
        gestorPilotos.cargarDesdeArchivo();
        gestorAviones.cargarDesdeArchivo();

        //ELIJO UN AVION, ELIJO UN PILOTO
        Avion avionSeleccionado = gestorAviones.getAvionPorID(avi1.getId());
        Piloto pilotoSeleccionado = gestorPilotos.getPilotoPorID(pil2.getId());

        // ACA TENDRIAN QUE IR VERIFIACIONES (DISPONIBLE == TRUE, PILOTO == NULL, COMBUSTIBLE > 80)

        // SI PASAN LAS VALIDACIONES, AGREGO EL PILOTO AL AVION
        avionSeleccionado.setPiloto(pilotoSeleccionado);
        pilotoSeleccionado.setDisponible(false); //SETEO SU ESTADO A FALSE

        // Y LO GUARDO EN EL HANGAR
        gestorHangar.agregar(avionSeleccionado);
        gestorHangar.guardarEnArchivo();
        gestorAviones.guardarEnArchivo();

        // TAMBIEN ACTUALIZO EL ARCHIVO DE PILOTOS
        gestorPilotos.guardarEnArchivo();

        // SIMULACION MENU HANGAR CANCELAR VUELO
        // se repiten los mismo pasos de cargar las listas

        // ELIJO EL VUELO QUE QUIERO CANCELAR
        Avion avionCancelado = gestorHangar.getAvionByID(avi1.getId());

        // BORRO EL AVION DEL HANGAR
        gestorHangar.eliminarAvionFromHangar(avionCancelado);

        // EL ATRIBUTO PILOTO DEL AVION PASA A NULL
        gestorAviones.eliminarPilotoDeAvion(avionCancelado);

        // SETEO EL ESTADO DEL PILOTO
        gestorPilotos.actualizarEstadoPiloto(avionCancelado.getPiloto());

        // LOS CAMBIOS SE GUARDAN DENTRO DEL METODO


    }



}
