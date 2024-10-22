package archivoJSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LeerJSON {
    public JSONArray cargarUsuarios() {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get("usuarios.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject(content);
        return jsonObject.getJSONArray("usuarios");
    }

}
