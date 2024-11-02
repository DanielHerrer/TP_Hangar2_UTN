package control;

import constantes.Archivos;
import entidades.Avion;
import entidades.Piloto;
import entidades.Avion;
import enums.Genero;

public class PruebaMain {

    public static void main (String[] args) {

        // COSAS QUE HICE: REACOMODE LA LOGICA DE LOS METODOS JSON
        // IMPLEMENTE EL TEMITA DE LOS REGISTROS

        Piloto pil1 = new Piloto("2233", "Franco Colapinto", Genero.MASCULINO, 2000, "43");

        ControlPilotos controlPilotos = new ControlPilotos();

        controlPilotos.agregar(pil1);

        controlPilotos.guardarPilotoToFile();

        controlPilotos.cargarPilotoDesdeArchivo();

        Piloto pil2 = new Piloto("78554", "Valtteri Bottas", Genero.MASCULINO, 1992, "77");

        controlPilotos.agregar(pil2);

        controlPilotos.guardarPilotoToFile();

        Avion avi1 = new Avion("Albano", 89, "x-e89", "Latam", 500);

        ControlAviones cA = new ControlAviones();

        cA.listaAviones.add(avi1);

        cA.guardarAvionToFile();

        cA.cargarAvionDesdeArchivo();

        cA.listaAviones.get(0).setPiloto(pil2);

        cA.guardarAvionToFile();

    }
}
