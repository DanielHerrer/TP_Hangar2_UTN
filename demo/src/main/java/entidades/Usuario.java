package entidades;

import enums.Genero;
import enums.Rango;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Usuario extends Persona{

    private String nombreUsuario;
    private String contrasenia;
    private LocalDateTime registro;
    private int rol;

    public Usuario(String dni, String nombreApellido, Genero genero, int anioNacimiento, String nombreUsuario, String contrasenia, LocalDateTime registro) {
        super(dni, nombreApellido, genero, anioNacimiento);
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.registro = LocalDateTime.now();
        this.rol = 0;
    }

    public Usuario(int id, String dni, String nombreApellido, Genero genero, int anioNacimiento, int alta, String nombreUsuario, String contrasenia, LocalDateTime registro, int rol) {
        super(id, dni, nombreApellido, genero, anioNacimiento, alta);
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.registro = registro;
        this.rol = rol;
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

    public JSONObject usuarioToJSONObject() {
        JSONObject json = new JSONObject();
        json.put("id", super.getId());
        json.put("dni", super.getDni());
        json.put("nombreApellido", super.getNombreApellido());
        json.put("genero", super.getGenero());
        json.put("anioNacimiento", super.getAnioNacimiento());
        json.put("alta", super.getAlta());
        json.put("nombreUsuario", this.nombreUsuario);
        json.put("contrasenia", this.contrasenia);
        json.put("registro", this.registro);
        json.put("rol", this.rol);
        return json;
    }

    public static Usuario JSONObjectToUsuario(JSONObject json) {
        int id = json.getInt("id");
        String dni = json.getString("dni");
        String nombreApellido = json.getString("nombreApellido");
        String generoStr = json.getString("genero");
        Genero genero = Genero.valueOf(generoStr.toUpperCase());
        int anioNacimiento = json.getInt("anioNacimiento");
        int alta = json.getInt("alta");
        String nombreUsuario = json.getString("nombreUsuario");
        String contrasenia = json.getString("contrasenia");
        String registroStr = json.getString("registro"); // Asegúrate de que el JSON contiene este campo en formato ISO-8601
        LocalDateTime registro = LocalDateTime.parse(registroStr, DateTimeFormatter.ISO_DATE_TIME);
        int rol = json.getInt("rol");
        return new Usuario(id, dni, nombreApellido, genero, anioNacimiento, alta, nombreUsuario, contrasenia, registro, rol);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombreUsuario='" + nombreUsuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", registro=" + registro +
                ", rol=" + rol +
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
