package control;

import entidades.Piloto;
import entidades.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ControlPilotos {

    public ArrayList<Piloto> listaPilotos;

    public ControlPilotos() {
        listaPilotos = new ArrayList<>();
    }

    public void agregar (Piloto p) {
        listaPilotos.add(p);
    }

    /**
     * @see "Verifica si un piloto esta o no el sistema, se basa en su numero de licencia"
     * @param nombreArchivo referencia al JSON de Pilotos
     * @param p es el Piloto que se quiere crear
     * @return devuelve un boolean de acuerdo a si esta o no
     */
    public boolean verificarUsuario (String nombreArchivo, Piloto p) {
        cargarPilotoDesdeArchivo(nombreArchivo);

        return listaPilotos.contains(p);

    }

    public JSONArray crearJSONArray () {
        JSONArray jsonArray = new JSONArray();

        for (Piloto pil : listaPilotos) {
            jsonArray.put(pil.pilotoToJSONObject());
        }
        return jsonArray;
    }


    public void guardarPilotoToFile(String archivo) {
        JSONArray pilotoArray = crearJSONArray();

        try (FileWriter file = new FileWriter(archivo)) {
            file.write(pilotoArray.toString(4));
            System.out.println("Pilotos guardadas en el archivo: " + archivo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarPilotoDesdeArchivo (String archivo) {
        try {

            JSONArray tasksArray = new JSONArray(leerArchivo(archivo));

            pilotoJSONArrayToList(tasksArray);
            System.out.println("Pilotos cargados desde el archivo: " + archivo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONTokener leerArchivo(String nombreArchivo){
        JSONTokener tokener = null;
        try{
            tokener= new JSONTokener(new FileReader(nombreArchivo));
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return tokener;
    }

    public void pilotoJSONArrayToList(JSONArray jsonArray) {
        listaPilotos.clear();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject pilotoJSON = jsonArray.getJSONObject(i);
            Piloto task = Piloto.JSONObjectToPiloto(pilotoJSON);
            listaPilotos.add(task);
        }
    }
}
