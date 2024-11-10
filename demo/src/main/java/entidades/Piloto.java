package entidades;

import constantes.Data;
import enums.Genero;
import enums.Rango;

import java.util.Objects;

public class Piloto extends Persona {

    private int id;
    private String numeroLicencia;
    private int horasVuelo;
    private Rango rango;
    private boolean disponible;
    private int alta;

    public Piloto () {
        this.id = 0; //llamo a la funcion
        this.numeroLicencia = null;
        this.horasVuelo = 0;
        this.rango = null;
        this.alta = 0;
    }

    public Piloto(String dni, String nombreApellido, Genero genero, int anioNacimiento, String numeroLicencia, int horasVuelo) {
        super(dni, nombreApellido, genero, anioNacimiento);
        this.id = Data.obtenerUltimoIdPiloto();
        this.numeroLicencia = numeroLicencia;
        this.horasVuelo = horasVuelo;
        actualizarRango();
        this.disponible = true;
        this.alta = 1;
    }

    /**
     * @see "Constructor con todos los parametros para el JSON"
     */
    public Piloto(String dni, String nombreApellido, Genero genero, int anioNacimiento, int id, String numeroLicencia, int horasVuelo, Rango rango, boolean disponible, int alta) {
        super(dni, nombreApellido, genero, anioNacimiento);
        this.id = id;
        this.numeroLicencia = numeroLicencia;
        this.horasVuelo = horasVuelo;
        this.rango = rango;
        this.disponible = disponible;
        this.alta = alta;
    }

    public int getId() {
        return id;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public int getHorasVuelo() {
        return horasVuelo;
    }

    public void setHorasVuelo(int horasVuelo) {
        this.horasVuelo = horasVuelo;
        actualizarRango();
    }

    public Rango getRango() {
        return rango;
    }

    public void setRango(Rango rango) {
        this.rango = rango;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getAlta() {
        return alta;
    }

    public void setAlta(int alta) {
        this.alta = alta;
    }

    /**
     * @see "Setea el rango del piloto de acuerdo a sus horas de vuelo"
     */
    public void actualizarRango () {
        if (this.horasVuelo >= 500)
        {
            setRango(Rango.COMANDANTE);
        }
        else if (this.horasVuelo >= 400)
        {
            setRango(Rango.SEGUNDO_COMANDANTE);
        }
        else if (this.horasVuelo >= 300)
        {
            setRango(Rango.TERCER_COMANDANTE);
        }
        else if (this.horasVuelo >= 200)
        {
            setRango(Rango.CAPITAN);
        }
        else if (this.horasVuelo >= 150)
        {
            setRango(Rango.SEGUNDO_CAPITAN);
        }
        else if (this.horasVuelo >= 100)
        {
            setRango(Rango.TERCER_CAPITAN);
        }
        else if (this.horasVuelo >= 80)
        {
            setRango(Rango.PRIMER_OFICIAL);
        }
        else if (this.horasVuelo >= 50)
        {
            setRango(Rango.SEGUNDO_OFICIAL);
        }
        else if (this.horasVuelo >= 30)
        {
            setRango(Rango.PILOTO_INSTRUMENTAL);
        }
        else
        {
            setRango(Rango.ALUMNO_PILOTO);
        }
    }

    @Override
    public String toString() {
        return "Piloto{" +
                "id=" + id +
                ", numeroLicencia='" + numeroLicencia + '\'' +
                ", horasVuelo=" + horasVuelo +
                ", rango=" + rango +
                ", alta=" + alta +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Piloto piloto = (Piloto) o;
        return id == piloto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
