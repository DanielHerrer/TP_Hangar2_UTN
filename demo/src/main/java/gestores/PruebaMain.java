package gestores;

import entidades.Avion;
import entidades.Piloto;
import enums.Genero;
import excepciones.FormatoIncorrectoException;
import excepciones.ObjetoRepetidoException;

public class PruebaMain {

    public static void main (String[] args) {

        // COSAS QUE HICE: AGREGE INTERFAZ iABML
        //AGREGE 4 EXCEPCIONES
        //IMPLEMENTE INTERFAZ EN CONTROLUSUARIOS
        //LOGICA PARA COMPROBACION DE ALTA/BAJA
        //PERSISTENCIA DE LA ALTA Y LA BAJA EN EL MENU DE LISTAR USUARIOS
        //PERSISTENCIA Y VALIDACIONES EN LA MODIFICACION DE UN USARIOS EN EL MENU DE LISTAR USUARIOS
        //

        Piloto pil1 = new Piloto("2233", "Franco Colapinto", Genero.MASCULINO, 2000, "43");

        GestorPilotos gestorPilotos = new GestorPilotos();

        try {
            gestorPilotos.agregar(pil1);
        }
        catch (ObjetoRepetidoException e) {
            e.printStackTrace();
        } catch (FormatoIncorrectoException e) {
            throw new RuntimeException(e);
        }

        gestorPilotos.guardarPilotoToFile();

        gestorPilotos.cargarPilotoDesdeArchivo();

        Piloto pil2 = new Piloto("78554", "Valtteri Bottas", Genero.MASCULINO, 1992, "77");

        try {
            gestorPilotos.agregar(pil1);
        } catch (ObjetoRepetidoException | FormatoIncorrectoException e) {
            System.out.println(e.getMessage());
        }

        gestorPilotos.guardarPilotoToFile();

        Avion avi1 = new Avion("Albano", 89, "x-e89", "Latam", 500);

        GestorAviones cA = new GestorAviones();

        cA.listaAviones.add(avi1);

        cA.guardarAvionToFile();

        cA.cargarAvionDesdeArchivo();

        cA.listaAviones.get(0).setPiloto(pil2);

        cA.guardarAvionToFile();

        GestorUsuarios gestorUsuarios = new GestorUsuarios();

        gestorUsuarios.cargarUsuarioDesdeArchivo();

        try {
            gestorUsuarios.eliminar(gestorUsuarios.listaUsuarios.get(3));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        gestorUsuarios.guardarUsuarioToFile();

    }
}
