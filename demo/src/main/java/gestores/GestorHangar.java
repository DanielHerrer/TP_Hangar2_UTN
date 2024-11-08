package gestores;

import constantes.Data;
import entidades.Avion;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;

import static gestores.GestorAviones.JSONObjectToAvion;
import static gestores.GestorAviones.avionToJSONObject;

public class GestorHangar {

    public HashSet<Avion> listaHangar;

    public GestorHangar() {
        this.listaHangar = new HashSet<>();
    }

    public HashSet<Avion> getListaHangar() {
        return listaHangar;
    }

    public void setListaHangar(HashSet<Avion> listaHangar) {
        this.listaHangar = listaHangar;
    }

    public void agregar (Avion a) {
        listaHangar.add(a);
        System.out.println("se agrego el avion " + a.getNombre() + " con el piloto " + a.getPiloto().getNombreApellido());
    }

    public Avion getAvionByID (int numeroID) {
        Avion avi = null;

        for (Avion a : listaHangar) {
            if (a.getId() == numeroID) {
                return a;
            }
        }
        return avi;
    }

    // BORRO EL AVION SELECCIONADO DEL ARCHIVO HANGAR Y LO ACTUALIZO
    public boolean eliminarAvionFromHangar (Avion avionCancelado) {
        if (listaHangar.remove(avionCancelado)) {
            guardarHangarToFile();
            return true;
        }
        return false;
    }


    // ================== METODOS PARA GUARDAR EL HANGAR EN UN JSON ============================

    public JSONArray crearJSONArray () {
        JSONArray jsonArray = new JSONArray();

        for (Avion avi : listaHangar) {
            jsonArray.put(avionToJSONObject(avi));
        }
        return jsonArray;
    }


    public void guardarHangarToFile() {
        JSONArray pilotoArray = crearJSONArray();

        Data.grabar(Data.archivoHangar, pilotoArray);
    }

    public void cargarHangarDesdeArchivo () {
        try {
            JSONArray avionesArray = new JSONArray(Data.leerArchivo(Data.archivoHangar));

            hangarJSONArrayToList(avionesArray);
            System.out.println("Aviones cargados desde el archivo: " + Data.archivoHangar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hangarJSONArrayToList(JSONArray jsonArray) {
        listaHangar.clear();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject avionJSON = jsonArray.getJSONObject(i);
            Avion avion = JSONObjectToAvion(avionJSON);
            listaHangar.add(avion);
        }
    }

}
