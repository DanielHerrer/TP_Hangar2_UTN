package entidades;

import enums.Genero;

import java.time.LocalDateTime;
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
