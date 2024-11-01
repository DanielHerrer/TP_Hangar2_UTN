package entidades;

import constantes.Archivos;
import enums.Genero;
import enums.Rango;
import org.json.JSONObject;

import java.util.Objects;

public class Piloto extends Persona {

    private int id;
    private String numeroLicencia;
    private int horasVuelo;
    private Rango rango;
    private int alta;

    public Piloto () {
        this.id = 0; //llamo a la funcion
        this.numeroLicencia = null;
        this.horasVuelo = 0;
        this.rango = null;
        this.alta = 0;
    }

    public Piloto(String dni, String nombreApellido, Genero genero, int anioNacimiento, String numeroLicencia) {
        super(dni, nombreApellido, genero, anioNacimiento);
        this.id = Archivos.obtenerUltimoIdPiloto();
        this.numeroLicencia = numeroLicencia;
        this.horasVuelo = 0;
        this.rango = Rango.ALUMNO_PILOTO;
        this.alta = 1;
    }

    /**
     * @see "Constructor por si se especifican las horas de vuelo"
     */
    public Piloto(String dni, String nombreApellido, Genero genero, int anioNacimiento, int horasVuelo, String numeroLicencia) {
        super(dni, nombreApellido, genero, anioNacimiento);
        this.id = Archivos.obtenerUltimoIdPiloto(); //llamo a la funcion
        this.numeroLicencia = numeroLicencia;
        this.horasVuelo = horasVuelo;
        actualizarRango();
        this.alta = 1;
    }

    /**
     * @see "Constructor con todos los parametros para el JSON"
     */
    public Piloto(String dni, String nombreApellido, Genero genero, int anioNacimiento, int id, String numeroLicencia, int horasVuelo, Rango rango, int alta) {
        super(dni, nombreApellido, genero, anioNacimiento);
        this.id = id;
        this.numeroLicencia = numeroLicencia;
        this.horasVuelo = horasVuelo;
        this.rango = rango;
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
        json.put("dni", super.getDni());
        json.put("nombreApellido", super.getNombreApellido());
        json.put("genero", super.getGenero());
        json.put("anioNacimiento", super.getAnioNacimiento());
        json.put("id", this.id);
        json.put("numeroLicencia", this.numeroLicencia);
        json.put("horasVuelo", this.horasVuelo);
        json.put("rango", this.rango);
        json.put("alta", this.alta);
        return json;
    }

    public static Piloto JSONObjectToPiloto(JSONObject json) {
        String dni = json.getString("dni");
        String nombreApellido = json.getString("nombreApellido");
        String generoStr = json.getString("genero");
        Genero genero = Genero.valueOf(generoStr.toUpperCase());
        int anioNacimiento = json.getInt("anioNacimiento");
        int id = json.getInt("id");
        String numeroLicencia = json.getString("numeroLicencia");
        int horasVuelo = json.getInt("horasVuelo");
        String rangoStr = json.getString("rango");
        Rango rango = Rango.valueOf(rangoStr.toUpperCase());
        int alta = json.getInt("alta");
        return new Piloto(dni, nombreApellido, genero, anioNacimiento, id, numeroLicencia, horasVuelo, rango, alta);
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
        return Objects.equals(numeroLicencia, piloto.numeroLicencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numeroLicencia);
    }
}
