package entidades;

import enums.Genero;
import org.json.JSONObject;

import java.util.Objects;

public abstract class Persona {

    public static int contadorID = 0;

    private int id;
    private String dni;
    private String nombreApellido;
    private Genero genero;
    private int anioNacimiento;
    private int alta;

    public Persona () {
        this.id = 0;
        this.dni = null;
        this.nombreApellido = null;
        this.genero = null;
        this.anioNacimiento = 0;
        this.alta = 0;
    }

    public Persona(String dni, String nombreApellido, Genero genero, int anioNacimiento) {
        this.id = ++contadorID;
        this.dni = dni;
        this.nombreApellido = nombreApellido;
        this.genero = genero;
        this.anioNacimiento = anioNacimiento;
        this.alta = 1;
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

    public int getId() {
        return id;
    }
/*
    public void setId(int id) {
        this.id = id;
    }
*/
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

    public int getAlta() {
        return alta;
    }

    public void setAlta(int alta) {
        this.alta = alta;
    }


    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombreApellido='" + nombreApellido + '\'' +
                ", genero=" + genero +
                ", anioNacimiento=" + anioNacimiento +
                ", alta=" + alta +
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
