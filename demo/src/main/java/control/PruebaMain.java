package control;

import constantes.Archivos;
import entidades.Avion;
import entidades.Piloto;
import entidades.Avion;
import enums.Genero;

public class PruebaMain {

    public static void main (String[] args) {

        Piloto pil1 = new Piloto("2233", "Franco Colapinto", Genero.MASCULINO, 2000, "43");

        ControlPilotos controlPilotos = new ControlPilotos();

        controlPilotos.agregar(pil1);

        controlPilotos.guardarPilotoToFile(Archivos.archivoPilotos);


    }
}
