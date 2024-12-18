package gestores;

import constantes.Data;
import entidades.Dupla;
import interfaces.iMetodosJSON;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GestorVuelos implements iMetodosJSON {

    public HashMap<Integer, Dupla> listaVuelos;
    // INTEGER = CODIGO DEL VUELO, DUPLA = ID DEL PILOTO Y EL AVION


    public GestorVuelos() {
        this.listaVuelos = new HashMap<>();
    }

    public HashMap<Integer, Dupla> getListaVuelos() {
        return listaVuelos;
    }

    public void setListaVuelos(HashMap<Integer, Dupla> listaVuelos) {
        this.listaVuelos = listaVuelos;
    }

    //METOOD PARA GENERAR UN NUMERO RANDOM EN LA CLAVE
    public int generarCodigo () {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    public JSONArray crearJSONArrayVuelos () {
        JSONArray registrosArray = new JSONArray();

        for (Map.Entry<Integer, Dupla> entrada : listaVuelos.entrySet()) {
            JSONObject jsonRegistro = new JSONObject();
            jsonRegistro.put("codigo", entrada.getKey());
            jsonRegistro.put("vuelo", Dupla.duplaToJSON(entrada.getValue()));
            registrosArray.put(jsonRegistro);
        }
        return registrosArray;
    }

    @Override
    public void guardarEnArchivo() {
        JSONArray vueloArray = crearJSONArrayVuelos();

        Data.grabar(Data.archivoVuelos, vueloArray);
    }

    @Override
    public void cargarDesdeArchivo() {
        try {
            JSONArray vueloArray = new JSONArray(Data.leerArchivo(Data.archivoVuelos));

            cargarListaDesdeJSONArray(vueloArray);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void cargarListaDesdeJSONArray (JSONArray vuelosJSONArray) {
        listaVuelos.clear();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        for (int i = 0; i < vuelosJSONArray.length(); i++) {
            JSONObject registroJSON = vuelosJSONArray.getJSONObject(i);
            Integer codigo = registroJSON.getInt("codigo");
            JSONObject vueloJSON = registroJSON.getJSONObject("vuelo");
            Dupla vuelo = Dupla.JSONObjectToDupla(vueloJSON);

            listaVuelos.put(codigo, vuelo);
        }
    }
}
