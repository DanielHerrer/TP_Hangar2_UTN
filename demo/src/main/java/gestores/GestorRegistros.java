package gestores;

import constantes.Data;
import entidades.Usuario;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class GestorRegistros {

    public HashMap<Integer, LocalDateTime> listaRegistros;


    public GestorRegistros() {
        this.listaRegistros = new HashMap<>();
    }


    public void guardarRegistro (Usuario u) {
        this.listaRegistros.put(u.getId(), u.getRegistro());
    }

    public JSONArray crearJSONArrayRegistros () {
        JSONArray registrosArray = new JSONArray();

        for (Map.Entry<Integer, LocalDateTime> entrada : listaRegistros.entrySet()) {
            JSONObject jsonRegistro = new JSONObject();
            jsonRegistro.put("id", entrada.getKey());
            jsonRegistro.put("registro", entrada.getValue().toString());
            registrosArray.put(jsonRegistro);
        }
        return registrosArray;
    }

    public void guardarRegistrosEnArchivo () {
        JSONArray registroArray = crearJSONArrayRegistros();

        Data.grabar(Data.archivoRegistros, registroArray);
    }

    public boolean cargarRegistrosDesdeArchivo () {
        try {
            if (Data.leerArchivo(Data.archivoRegistros) == null) {
                return false; // SI NO LEYO EL ARCHIVO DEVUELVE FALSE
            }
            JSONArray registrosArray = new JSONArray(Data.leerArchivo(Data.archivoRegistros));

            cargarListaDesdeJSONArray(registrosArray);

            return true; // RETORNA TRUE SI LO PUDO LEER
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void cargarListaDesdeJSONArray (JSONArray registrosArray) {
        listaRegistros.clear();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        for (int i = 0; i < registrosArray.length(); i++) {
           JSONObject registroJSON = registrosArray.getJSONObject(i);
           Integer id = registroJSON.getInt("id");
           LocalDateTime registro = LocalDateTime.parse(registroJSON.getString("registro"), formatter);

           listaRegistros.put(id, registro);
        }
    }


}
