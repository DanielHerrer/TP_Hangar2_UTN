package entidades;

import java.util.ArrayList;

public class Hangar {

    private ArrayList<Avion> avionesHangar;
    private int numeroHangar;

    public Hangar(int numeroHangar) {
        this.avionesHangar = new ArrayList<>(20);
        this.numeroHangar = numeroHangar;
    }


}
