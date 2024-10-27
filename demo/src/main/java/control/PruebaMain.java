package control;

import entidades.Piloto;
import entidades.Avion;
import enums.Genero;

public class PruebaMain {

    public static void main (String[] args) {

        String archivoPilotos = "pilotos.json";
        String archivoAviones = "aviones.json";

        ControlPilotos controlPilotos = new ControlPilotos();

        Piloto pil1 = new Piloto("4342", "Franco Colapinto", Genero.MASCULINO, 2003, "43");

        controlPilotos.listaPilotos.add(pil1);

        controlPilotos.guardarPilotoToFile(archivoPilotos);

        boolean estado = controlPilotos.verificarUsuario(archivoPilotos, pil1);

        if (estado) {
            System.out.println("El piloto ya esta en el archivo");
        }
        else  {
            System.out.println("El piloto no lo esta");
        }

        ControlAviones ca = new ControlAviones();

        Avion avi = new Avion("Concorde", 912, "747", "PANAM", 320);
        Avion avi2 = new Avion("Hercules", 902, "369", "TURKISH", 270);

        ca.listaAviones.add(avi);
        ca.listaAviones.add(avi2);

        ca.guardarAvionToFile(archivoAviones);

        avi = ca.listaAviones.get(0);

        avi.setPiloto(pil1);

        ca.listaAviones.add(avi);

        ca.guardarAvionToFile(archivoAviones);

        Piloto pil2 = new Piloto("90210", "Lamine Yamal", Genero.MASCULINO, 2007, "19");

        avi2.setPiloto(pil2);

        avi.setPiloto(null);

        ca.guardarAvionToFile(archivoAviones);


    }
}
