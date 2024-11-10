package entidades;

import gestores.GestorAviones;
import gestores.GestorPilotos;

public class Hangar {

    GestorPilotos gestorPilotos;
    GestorAviones gestorAviones;
    private int numeroHangar;

    public Hangar(int numeroHangar) {
        this.gestorPilotos = new GestorPilotos();
        this.gestorAviones = new GestorAviones();
        this.numeroHangar = numeroHangar;
    }


    public String vincularPilotoConAvion (int idPiloto, int idAvion) {
        Piloto piloto = gestorPilotos.getPilotoPorID(idPiloto);

        if (piloto == null) {
            return "No existe piloto con el id: " + idPiloto + "\n";
        }

        Avion avion = gestorAviones.getAvionPorID(idAvion);

        if (avion == null) {
            return "No existe avion con el id: " + idPiloto + "\n";
        }

        avion.setPiloto(piloto);

        return "Se ha asignado al piloto " + piloto.getNombreApellido() + " al avion " + avion.getNombre() + "\n";
    }



}
