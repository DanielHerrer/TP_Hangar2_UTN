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

public class ControlUsuarios {

    public ArrayList<Usuario> listaUsuarios;

    public ControlUsuarios () {
        this.listaUsuarios = new ArrayList<>();
    }

    public void agregar (Usuario u) {
        listaUsuarios.add(u);
    }

    public boolean verificarUsuario (String nombreArchivo, Usuario u) {
        cargarUsuariooDesdeArchivo(nombreArchivo);

        return listaUsuarios.contains(u);

    }

    public JSONArray crearJSONArray () {
        JSONArray jsonArray = new JSONArray();

        for (Usuario usu : listaUsuarios) {
            jsonArray.put(usu.usuarioToJSONObject());
        }
        return jsonArray;
    }


    public void guardarUsuarioToFile(String archivo) {
        JSONArray usuarioArray = crearJSONArray();

        try (FileWriter file = new FileWriter(archivo)) {
            file.write(usuarioArray.toString(2));
            System.out.println("Usuarios guardados en el archivo: " + archivo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarUsuariooDesdeArchivo (String archivo) {
        try {

            JSONArray usuarioArray = new JSONArray(leerArchivo(archivo));

            usuarioJSONArrayToList(usuarioArray);
            System.out.println("Usuarios cargados desde el archivo: " + archivo);
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

    public void usuarioJSONArrayToList(JSONArray jsonArray) {
        listaUsuarios.clear();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject usuarioJSON = jsonArray.getJSONObject(i);
            Usuario usuario = Usuario.JSONObjectToUsuario(usuarioJSON);
            listaUsuarios.add(usuario);
        }
    }

}
