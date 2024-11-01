package entidades;

import enums.Genero;
import org.json.JSONObject;

import java.util.Objects;

public abstract class Persona {

    private String dni;
    private String nombreApellido;
    private Genero genero;
    private int anioNacimiento;

    public Persona () {
        this.dni = null;
        this.nombreApellido = null;
        this.genero = null;
        this.anioNacimiento = 0;
    }

    /**
     * @see "Constructor con todos los parametros para crear el JSON"
     */
    public Persona(String dni, String nombreApellido, Genero genero, int anioNacimiento) {
        this.dni = dni;
        this.nombreApellido = nombreApellido;
        this.genero = genero;
        this.anioNacimiento = anioNacimiento;
    }

    /**
     *
     * @see "Constructor con todos los parametros para crear el JSON"
     */
    public Persona(int id, String dni, String nombreApellido, Genero genero, int anioNacimiento, int alta) {
        this.id = id;
        this.dni = dni;
        this.nombreApellido = nombreApellido;
        this.genero = genero;
        this.anioNacimiento = anioNacimiento;
        this.alta = alta;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public int getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(int anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }


    @Override
    public String toString() {
        return "Persona{" +
                "dni='" + dni + '\'' +
                ", nombreApellido='" + nombreApellido + '\'' +
                ", genero=" + genero +
                ", anioNacimiento=" + anioNacimiento +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return Objects.equals(dni, persona.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }
}
