package control;

import constantes.Archivos;
import entidades.Usuario;
import enums.Genero;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ControlUsuarios {

    public ArrayList<Usuario> listaUsuarios;

    public ControlUsuarios () {
        this.listaUsuarios = new ArrayList<>();
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void agregar (Usuario u) {
        listaUsuarios.add(u);
    }

    public boolean verificarUsuario (Usuario u) {
        cargarUsuarioDesdeArchivo();

        return listaUsuarios.contains(u);

    }

    // ========================================== METODOS JSON ===================================================

    /**
     * @see "Crea un JSONArray en base a la lista de usuarios, para que luego sea subido al archivo"
     * @return
     */
    public JSONArray crearJSONArray () {
        JSONArray jsonArray = new JSONArray();

        for (Usuario usu : listaUsuarios) {
            jsonArray.put(usuarioToJSONObject(usu));
        }
        return jsonArray;
    }

    /**
     * @see "El JSONArray devuelto por la funcion anterior, es subido a un archivo.
     *      LLama a la funcion estatica de la clase Archivos para guardarlo"
     */
    public void guardarUsuarioToFile() {
        JSONArray usuarioArray = crearJSONArray();

        Archivos.grabar(Archivos.archivoUsuarios, usuarioArray);
        /*
        try (FileWriter file = new FileWriter(Archivos.archivoUsuarios)) {
            file.write(usuarioArray.toString(4));
            System.out.println("Usuarios guardados en el archivo: " + Archivos.archivoUsuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    /**
     * @see "Lee el archivo para guardar la informacion en un JSONArray. Luego llama a otra funcion
     *      que guarda el JSONArray en la lista de usuarios"
     */
    public void cargarUsuarioDesdeArchivo() {
        try {
            JSONArray usuarioArray = new JSONArray(Archivos.leerArchivo(Archivos.archivoUsuarios));

            usuarioJSONArrayToList(usuarioArray);
            System.out.println("Usuarios cargados desde el archivo: " + Archivos.archivoUsuarios);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @see "Se guarda la informacion del JSONArray de la funcion anterior, en la lista de usuarios"
     * @param jsonArray
     */
    public void usuarioJSONArrayToList(JSONArray jsonArray) {
        listaUsuarios.clear();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject usuarioJSON = jsonArray.getJSONObject(i);
            Usuario usuario = JSONObjectToUsuario(usuarioJSON);
            listaUsuarios.add(usuario);
        }
    }

    /**
     * @see "Convierte un Usuario en un JSONObject"
     * @param u
     * @return
     */
    public static JSONObject usuarioToJSONObject(Usuario u) {
        JSONObject json = new JSONObject();
        json.put("dni", u.getDni());
        json.put("nombreApellido", u.getNombreApellido());
        json.put("genero", u.getGenero());
        json.put("anioNacimiento", u.getAnioNacimiento());
        json.put("id", u.getId());
        json.put("nombreUsuario", u.getNombreUsuario());
        json.put("contrasenia", u.getContrasenia());
        json.put("registro", u.getRegistro());
        json.put("rol", u.getRol());
        json.put("alta", u.getAlta());
        return json;
    }

    /**
     * @see "Convierte un JSONObject en un Usuario"
     * @param json
     * @return
     */
    public static Usuario JSONObjectToUsuario(JSONObject json) {
        String dni = json.getString("dni");
        String nombreApellido = json.getString("nombreApellido");
        String generoStr = json.getString("genero");
        Genero genero = Genero.valueOf(generoStr.toUpperCase());
        int anioNacimiento = json.getInt("anioNacimiento");
        int id = json.getInt("id");
        String nombreUsuario = json.getString("nombreUsuario");
        String contrasenia = json.getString("contrasenia");
        String registroStr = json.getString("registro");
        LocalDateTime registro = LocalDateTime.parse(registroStr, DateTimeFormatter.ISO_DATE_TIME);
        int rol = json.getInt("rol");
        int alta = json.getInt("alta");
        return new Usuario(dni, nombreApellido, genero, anioNacimiento, id, nombreUsuario, contrasenia, registro, rol, alta);
    }

    // ==================================================================================================

    /**
     * @see "Verifica mediante un boolean si el usuario ya existe"
     * @param username
     * @return
     */
    public boolean usuarioYaExiste(String username) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getNombreUsuario().equals(username)) {
                return true;
            }
        }
        return false;
    }

    //getDNI viene de persona, lo tengo que traer pero tengo pereza ahora, si se arregla, el metodo para
    //no dejar cargar el mismo DNI ya ira funcionar!
    public boolean dniYaExiste(String dni) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getDni().equals(dni)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @see "Busca si el usuario esta en la lista, en base al username y la contrasenia"
     * @param nombreUsuario
     * @param password
     * @return Si encontro al usuario en la lista lo devuelve
     */
    public Usuario verificarUsuarioLogin (String nombreUsuario, String password) {
        Usuario u = null;

        for (Usuario usu : listaUsuarios) {
            if (usu.getNombreUsuario().equals(nombreUsuario) && usu.getContrasenia().equals(password)) {
                u = usu;
                return u;
            }
        }
        return u;
    }



}
