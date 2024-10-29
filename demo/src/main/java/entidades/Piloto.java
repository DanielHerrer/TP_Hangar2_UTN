package entidades;

import enums.Genero;
import enums.Rango;
import org.json.JSONObject;

import java.util.Objects;

public class Piloto extends Persona {

    // HACER UN ID ACUMULATIVO
    private String numeroLicencia;
    private int horasVuelo;
    private Rango rango;
    private int alta;

    public Piloto () {
        this.numeroLicencia = null;
        this.horasVuelo = 0;
        this.rango = null;
        this.alta = 0;
    }

    public Piloto(String dni, String nombreApellido, Genero genero, int anioNacimiento, String numeroLicencia) {
        super(dni, nombreApellido, genero, anioNacimiento);
        this.numeroLicencia = numeroLicencia;
        this.horasVuelo = 0;
        this.rango = Rango.ALUMNO_PILOTO;
        this.alta = 1;
    }

    public Piloto(String dni, String nombreApellido, Genero genero, int anioNacimiento, int horasVuelo, String numeroLicencia) {
        super(dni, nombreApellido, genero, anioNacimiento);
        this.numeroLicencia = numeroLicencia;
        this.horasVuelo = horasVuelo;
        actualizarRango();
        this.alta = 1;
    }

    public Piloto(int id, String dni, String nombreApellido, Genero genero, int anioNacimiento, String numeroLicencia, int horasVuelo, Rango rango) {
        super(id, dni, nombreApellido, genero, anioNacimiento);
        this.numeroLicencia = numeroLicencia;
        this.horasVuelo = horasVuelo;
        this.rango = rango;
    }

    /**
     *
     * @see "Constructor con todos los parametros para crear el JSON"
     */
    public Piloto(int id, String dni, String nombreApellido, Genero genero, int anioNacimiento, String numeroLicencia, int horasVuelo, Rango rango, int alta) {
        super(id, dni, nombreApellido, genero, anioNacimiento);
        this.numeroLicencia = numeroLicencia;
        this.horasVuelo = horasVuelo;
        this.rango = rango;
        this.alta = alta;
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
        else if (this.horasVuelo < 30)
        {
            setRango(Rango.ALUMNO_PILOTO);
        }
    }

    public JSONObject pilotoToJSONObject() {
        JSONObject json = new JSONObject();
        json.put("id", super.getId());
        json.put("dni", super.getDni());
        json.put("nombreApellido", super.getNombreApellido());
        json.put("genero", super.getGenero());
        json.put("anioNacimiento", super.getAnioNacimiento());
        json.put("alta", getAlta());
        json.put("numeroLicencia", this.numeroLicencia);
        json.put("horasVuelo", this.horasVuelo);
        json.put("rango", this.rango);
        return json;
    }

    public static Piloto JSONObjectToPiloto(JSONObject json) {
        int id = json.getInt("id");
        String dni = json.getString("dni");
        String nombreApellido = json.getString("nombreApellido");
        String generoStr = json.getString("genero");
        Genero genero = Genero.valueOf(generoStr.toUpperCase());
        int anioNacimiento = json.getInt("anioNacimiento");
        int alta = json.getInt("alta");
        String numeroLicencia = json.getString("numeroLicencia");
        int horasVuelo = json.getInt("horasVuelo");
        String rangoStr = json.getString("rango");
        Rango rango = Rango.valueOf(rangoStr.toUpperCase());
        return new Piloto(id, dni, nombreApellido, genero, anioNacimiento, numeroLicencia, horasVuelo, rango, alta);
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
