package entidades;

import enums.Genero;

import java.util.Objects;

public class Piloto extends Persona {

    private String numeroLicencia;
    private int horasVuelo;
    private String rango;

    public Piloto(String dni, String nombreApellido, Genero genero, int anioNacimiento, String numeroLicencia, String rango) {
        super(dni, nombreApellido, genero, anioNacimiento);
        this.numeroLicencia = numeroLicencia;
        this.horasVuelo = 0;
        this.rango = rango;
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

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
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
