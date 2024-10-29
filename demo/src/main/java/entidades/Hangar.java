package entidades;

import control.ControlAviones;
import control.ControlPilotos;
import control.ControlUsuarios;

import java.util.ArrayList;

public class Hangar {

    ControlPilotos controlPilotos;
    ControlAviones controlAviones;
    private int numeroHangar;

    public Hangar(int numeroHangar) {
        this.controlPilotos = new ControlPilotos();
        this.controlAviones = new ControlAviones();
        this.numeroHangar = numeroHangar;
    }


    public String vincularPilotoConAvion (int idPiloto, int idAvion) {
        Piloto piloto = controlPilotos.buscarPilotoPorID(idPiloto);

        if (piloto == null) {
            return "No existe piloto con el id: " + idPiloto + "\n";
        }

        Avion avion = controlAviones.buscarAvionPorID(idAvion);

        if (avion == null) {
            return "No existe avion con el id: " + idPiloto + "\n";
        }

        avion.setPiloto(piloto);

        return "Se ha asignado al piloto " + piloto.getNombreApellido() + " al avion " + avion.getNombre() + "\n";
    }



}
