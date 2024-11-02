package control;

import constantes.Archivos;
import entidades.Usuario;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ControlRegistros {

    public HashMap<String, LocalDateTime> listaRegistros;


    public ControlRegistros() {
        this.listaRegistros = new HashMap<>();
    }


    public void guardarRegistro (Usuario u) {
        this.listaRegistros.put(u.getNombreUsuario(), u.getRegistro());
    }

    public JSONArray crearJSONArrayRegistros () {
        JSONArray registrosArray = new JSONArray();

        for (Map.Entry<String, LocalDateTime> entrada : listaRegistros.entrySet()) {
            JSONObject jsonRegistro = new JSONObject();
            jsonRegistro.put("usuario", entrada.getKey());
            jsonRegistro.put("registro", entrada.getValue().toString());
            registrosArray.put(jsonRegistro);
        }
        return registrosArray;
    }

    public void guardarRegistrosEnArchivo () {
        JSONArray registroArray = crearJSONArrayRegistros();

        Archivos.grabar(Archivos.archivoRegistros, registroArray);
    }

    public boolean cargarRegistrosDesdeArchivo () {
        try {
            if (Archivos.leerArchivo(Archivos.archivoRegistros) == null) {
                return false; // SI NO LEYO EL ARCHIVO DEVUELVE FALSE
            }
            JSONArray registrosArray = new JSONArray(Archivos.leerArchivo(Archivos.archivoRegistros));

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
           String usuario = registroJSON.getString("usuario");
           LocalDateTime registro = LocalDateTime.parse(registroJSON.getString("registro"), formatter);

           listaRegistros.put(usuario, registro);
        }
    }


}
