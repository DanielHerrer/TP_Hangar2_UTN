package control;

import entidades.Piloto;
import enums.Genero;

public class PruebaMain {

    public static void main (String[] args) {

        ControlPilotos controlPilotos = new ControlPilotos();

        Piloto pil1 = new Piloto("434", "Franco Colapinto", Genero.MASCULINO, 1980, "43");

        boolean estado = controlPilotos.verificarUsuario("archi pilotos", pil1);

        if (estado) {
            System.out.println("El piloto ya esta en el estado");
        }
        else  {
            System.out.println("El piloto no lo esta");
        }
    }
}
