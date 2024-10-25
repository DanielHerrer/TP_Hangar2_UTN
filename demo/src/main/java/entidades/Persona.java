package entidades;

import enums.Genero;
import org.json.JSONObject;

import java.util.Objects;

public abstract class Persona {
    private static final String RUTA_ARCHIVO_CONTADOR = "contadorID.txt";
    public static int contadorID = cargarContador();


    private int id;
    private String dni;
    private String nombreApellido;
    private Genero genero;
    private int anioNacimiento;
    private int alta;

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

    // Método para cargar el contador desde el archivo
    private static int cargarContador() {
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO_CONTADOR))) {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            return 0; // Si el archivo no existe o hay un error, el contador comienza en 0
        }
    }

    // Método para guardar el contador en el archivo
    private static void guardarContador(int contador) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO_CONTADOR))) {
            writer.write(String.valueOf(contador));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        return id == persona.id && Objects.equals(dni, persona.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dni);
    }


}
