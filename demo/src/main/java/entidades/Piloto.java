package entidades;

import enums.Genero;
import enums.Rango;

import java.util.Objects;

public class Piloto extends Persona {

    private String numeroLicencia;
    private int horasVuelo;
    private Rango rango;

    public Piloto(String dni, String nombreApellido, Genero genero, int anioNacimiento, String numeroLicencia) {
        super(dni, nombreApellido, genero, anioNacimiento);
        this.numeroLicencia = numeroLicencia;
        this.horasVuelo = 0;
        this.rango = Rango.ALUMNO_PILOTO;
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
    }

    public Rango getRango() {
        return rango;
    }

    public void setRango(Rango rango) {
        this.rango = rango;
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
        else if (this.horasVuelo < 30)
        {
            setRango(Rango.ALUMNO_PILOTO);
        }
    }

    @Override
    public String toString() {
        return "Piloto{" +
                "numeroLicencia='" + numeroLicencia + '\'' +
                ", horasVuelo=" + horasVuelo +
                ", rango='" + rango + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Piloto piloto = (Piloto) o;
        return Objects.equals(numeroLicencia, piloto.numeroLicencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numeroLicencia);
    }
}
