package entidades;


import com.sun.jdi.DoubleValue;
import constantes.Data;
import org.json.JSONObject;
// que lindo avion
import java.util.Objects;

public class Avion implements Comparable<Avion> {

    private static int contadorID = 0;

    private int id;
    private Piloto piloto;
    private String nombre;
    private int numeracion;
    private String modelo;
    private String aerolinea;
    private int capacidadPasajeros;
    private int vuelosRealizados;
    private int combustibleMaximo;
    private int combustibleActual;
    private int alta;

    public Avion(String nombre, int numeracion, String modelo, String aerolinea, int capacidadPasajeros) {
        this.id = Data.obtenerUltimoIdAvion();
        this.piloto = null;
        this.nombre = nombre;
        this.numeracion = numeracion;
        this.modelo = modelo;
        this.aerolinea = aerolinea;
        this.capacidadPasajeros = capacidadPasajeros;
        this.vuelosRealizados = 0;
        this.combustibleMaximo = 100;
        this.combustibleActual = 100;
        this.alta = 1;
    }

    public Avion(int id, Piloto piloto, String nombre, int numeracion, String modelo, String aerolinea, int capacidadPasajeros, int vuelosRealizados, int combustibleMaximo, int combustibleActual, int alta) {
        this.id = id;
        this.piloto = piloto;
        this.nombre = nombre;
        this.numeracion = numeracion;
        this.modelo = modelo;
        this.aerolinea = aerolinea;
        this.capacidadPasajeros = capacidadPasajeros;
        this.vuelosRealizados = vuelosRealizados;
        this.combustibleMaximo = combustibleMaximo;
        this.combustibleActual = combustibleActual;
        this.alta = alta;
    }

    public int getId() {
        return id;
    }
/*
    public void setId(int id) {
        this.id = id;
    }
*/
    public Piloto getPiloto() {
        return piloto;
    }

    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeracion() {
        return numeracion;
    }

    public void setNumeracion(int numeracion) {
        this.numeracion = numeracion;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

    public int getCapacidadPasajeros() {
        return capacidadPasajeros;
    }

    public void setCapacidadPasajeros(int capacidadPasajeros) {
        this.capacidadPasajeros = capacidadPasajeros;
    }

    public int getVuelosRealizados() {
        return vuelosRealizados;
    }

    public void setVuelosRealizados(int vuelosRealizados) {
        this.vuelosRealizados = vuelosRealizados;
    }

    public int getCombustibleMaximo() {
        return combustibleMaximo;
    }

    public void setCombustibleMaximo(int combustibleMaximo) {
        this.combustibleMaximo = combustibleMaximo;
    }

    public int getCombustibleActual() {
        return combustibleActual;
    }

    public void setCombustibleActual(int combustibleActual) {
        this.combustibleActual = combustibleActual;
    }

    public int getAlta() {
        return alta;
    }

    public void setAlta(int alta) {
        this.alta = alta;
    }


    /**
     * @see "Llena por completo el combustible del avion"
     * @return Devuelve un mensaje indicando que el combustible se lleno
     */
    public String cargarCombustible () {
        setCombustibleActual(100);
        return "Combustible lleno.";
    }

    public Double combustibleDouble () {
        Double resultado = (double) this.combustibleActual / 100;
        return resultado;
    }


    @Override
    public String toString() {
        return "Avion{" +
                "id=" + id +
                ", piloto=" + piloto +
                ", nombre='" + nombre + '\'' +
                ", numeracion=" + numeracion +
                ", modelo='" + modelo + '\'' +
                ", aerolinea='" + aerolinea + '\'' +
                ", capacidadPasajeros=" + capacidadPasajeros +
                ", vuelosRealizados=" + vuelosRealizados +
                ", combustibleMaximo=" + combustibleMaximo +
                ", combustibleActual=" + combustibleActual +
                ", alta=" + alta +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avion avion = (Avion) o;
        return id == avion.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public int compareTo(Avion o) {
        return this.nombre.compareTo(o.getNombre());
    }
}
