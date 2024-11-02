package entidades;

import constantes.Archivos;
import enums.Genero;
import enums.Rango;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Usuario extends Persona {

    private int id;
    private String nombreUsuario;
    private String contrasenia;
    private LocalDateTime registro;
    private int rol;
    private int alta;

    public Usuario(String dni, String nombreApellido, Genero genero, int anioNacimiento, String nombreUsuario, String contrasenia) {
        super(dni, nombreApellido, genero, anioNacimiento);
        this.id = Archivos.obtenerUltimoIdUsuario();
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.registro = LocalDateTime.now();
        this.rol = 0;
        this.alta = 1;
    }

    /**
     * @see "Constructor con todos los param para el JSON"
     */
    public Usuario(String dni, String nombreApellido, Genero genero, int anioNacimiento, int id, String nombreUsuario, String contrasenia, LocalDateTime registro, int rol, int alta) {
        super(dni, nombreApellido, genero, anioNacimiento);
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.registro = registro;
        this.rol = rol;
        this.alta = alta;
    }

    public int getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public LocalDateTime getRegistro() {
        return registro;
    }

    public void setRegistro(LocalDateTime registro) {
        this.registro = registro;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public int getAlta() {
        return alta;
    }

    public void setAlta(int alta) {
        this.alta = alta;
    }

    /*
    public JSONObject usuarioToJSONObject() {
        JSONObject json = new JSONObject();
        json.put("dni", super.getDni());
        json.put("nombreApellido", super.getNombreApellido());
        json.put("genero", super.getGenero());
        json.put("anioNacimiento", super.getAnioNacimiento());
        json.put("id", this.id);
        json.put("nombreUsuario", this.nombreUsuario);
        json.put("contrasenia", this.contrasenia);
        json.put("registro", this.registro);
        json.put("rol", this.rol);
        json.put("alta", this.alta);
        return json;
    }

    public static Usuario JSONObjectToUsuario(JSONObject json) {
        String dni = json.getString("dni");
        String nombreApellido = json.getString("nombreApellido");
        String generoStr = json.getString("genero");
        Genero genero = Genero.valueOf(generoStr.toUpperCase());
        int anioNacimiento = json.getInt("anioNacimiento");
        int id = json.getInt("id");
        String nombreUsuario = json.getString("nombreUsuario");
        String contrasenia = json.getString("contrasenia");
        String registroStr = json.getString("registro");
        LocalDateTime registro = LocalDateTime.parse(registroStr, DateTimeFormatter.ISO_DATE_TIME);
        int rol = json.getInt("rol");
        int alta = json.getInt("alta");
        return new Usuario(dni, nombreApellido, genero, anioNacimiento, id, nombreUsuario, contrasenia, registro, rol, alta);
    }
*/

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", registro=" + registro +
                ", rol=" + rol +
                ", alta=" + alta +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(nombreUsuario, usuario.nombreUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nombreUsuario);
    }
}
