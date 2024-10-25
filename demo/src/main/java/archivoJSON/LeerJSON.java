package claseControllerJSON_Lgn;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class LeerJSON {
    public JSONArray cargarUsuarios() {
        String content = "";
        try {
            // Cargar el archivo desde resources/archivosJSON/usuarios.json
            InputStream inputStream = getClass().getResourceAsStream("/archivosJSON/usuarios.json");
            if (inputStream == null) {
                throw new NullPointerException("No se pudo encontrar el archivo usuarios.json en la ruta especificada.");
            }

            // Convertir el InputStream a String
            content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convertir el contenido JSON a un objeto JSONObject
        JSONObject jsonObject = new JSONObject(content);

        // Devolver el JSONArray de usuarios
        return jsonObject.getJSONArray("usuarios");
    }
}
