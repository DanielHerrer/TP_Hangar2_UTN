package entidades;

import enums.Genero;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Dupla {

    // SE GUARDAN EL ID DEL AVION Y DEL PILOTO
    private int idAvion;
    private int idPiloto;

    //SE USA EL CONSTRUCTOR PARA QUE YA NOS CREE UNA DUPLA
    public Dupla(int idAvion, int idPiloto) {
        this.idAvion = idAvion;
        this.idPiloto = idPiloto;
    }

    public int getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(int idAvion) {
        this.idAvion = idAvion;
    }

    public int getIdPiloto() {
        return idPiloto;
    }

    public void setIdPiloto(int idPiloto) {
        this.idPiloto = idPiloto;
    }


    public static JSONObject duplaToJSON(Dupla d) {
        JSONObject json = new JSONObject();
        json.put("id_avion", d.getIdAvion());
        json.put("id_piloto", d.getIdPiloto());
        return json;
    }


    public static Dupla JSONObjectToDupla(JSONObject json) {
        int id_avion = json.getInt("id_piloto");
        int id_piloto = json.getInt("id_avion");
        return new Dupla(id_avion, id_piloto);
    }
}
