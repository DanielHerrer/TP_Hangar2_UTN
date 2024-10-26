package control;

import entidades.Avion;
import entidades.Piloto;
import enums.Genero;

public class PruebaMain {

    public static void main (String[] args) {

        ControlPilotos controlPilotos = new ControlPilotos();

        Piloto pil1 = new Piloto("4342", "Franco Colapinto", Genero.MASCULINO, 1980, "43");

        boolean estado = controlPilotos.verificarUsuario("archi pilotos", pil1);

        if (estado) {
            System.out.println("El piloto ya esta en el estado");
        }
        else  {
            System.out.println("El piloto no lo esta");
        }

        ControlAviones ca = new ControlAviones();

        Avion avi = new Avion("Concorde", 912, "747", "PANAM", 320);
        Avion avi2 = new Avion("Hercules", 902, "369", "TURKISH", 270);

        ca.listaAviones.add(avi);
        ca.listaAviones.add(avi2);

        ca.guardarAvionToFile("archi aviones");

        avi = ca.listaAviones.get(0);

        avi.setPiloto(pil1);

        ca.listaAviones.add(avi);

        ca.guardarAvionToFile("archi aviones");

        
    }
}
