package gestores;

import constantes.Data;
import entidades.Usuario;
import enums.Genero;
import excepciones.FormatoIncorrectoException;
import excepciones.ObjetoInexistenteException;
import excepciones.ObjetoRepetidoException;
import interfaces.iABML;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GestorUsuarios implements iABML<Usuario> {

    public static Usuario usuarioLogueado = null;

    public ArrayList<Usuario> listaUsuarios;

    public GestorUsuarios() {
        this.listaUsuarios = new ArrayList<>();
    }

    public static Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public static void setUsuarioLogueado(Usuario usuarioLogueado) {
        GestorUsuarios.usuarioLogueado = usuarioLogueado;
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    // ===================== METODOS DE LA INTERFAZ =====================================

    @Override
    public boolean agregar(Usuario u) throws FormatoIncorrectoException, ObjetoRepetidoException {
        if (listaUsuarios.contains(u)) {
            throw new ObjetoRepetidoException("El usuario ya esta cargado en el sistema");
        }
        if (u.getId() != 0  && u.getNombreUsuario() != null && u.getContrasenia() != null ) {
            listaUsuarios.add(u);
            return true;
        }
        else {
            throw new FormatoIncorrectoException("El formato introducido no es correcto");
        }
    }

    /**
     * @see "Da a un usuario de baja"
     * @param u
     * @return
     * @throws FormatoIncorrectoException
     * @throws ObjetoInexistenteException
     */
    @Override
    public boolean eliminar(Usuario u) throws FormatoIncorrectoException, ObjetoInexistenteException {
        if (u.getId() != 0  && u.getNombreUsuario() != null && u.getContrasenia() != null) {
            if (listaUsuarios.contains(u)) {
                u.setAlta(0);
                return true;
            }
            else {
                throw new ObjetoInexistenteException("El usuario no se encuentra en el sistema");
            }
        }
        else {
            throw new FormatoIncorrectoException("El usuario introducido no posee el formato correcto");
        }
    }

    @Override
    public boolean modificar(Usuario u) {
        return false;
    }

    @Override
    public boolean listar(Usuario u) {
        return false;
    }

    // ==============================================================================================

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

        Data.grabar(Data.archivoUsuarios, usuarioArray);
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
            JSONArray usuarioArray = new JSONArray(Data.leerArchivo(Data.archivoUsuarios));

            usuarioJSONArrayToList(usuarioArray);
            System.out.println("Usuarios cargados desde el archivo: " + Data.archivoUsuarios);
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

    public boolean comprobarUsernameModificacion(String username, Usuario u) {
        if (u.getNombreUsuario().equals(username)) {
            return true; // SI EL NOMBRE SIGUE SIENDO EL MISMO RETORNO TRUE
        }
        else {
            for (Usuario user : listaUsuarios) {
                if (user.getNombreUsuario().equals(username)) {
                    return false; // SI EL USERNAME NUEVO COINCIDE CON ALGUN OTRO RETORNO FALSE
                }
            }
            return true; // SI NO LO ENCONTRO ENTONCES ESTA DISPONIBLE Y RETORNO TRUE
        }
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

    public boolean comprobarDniModificacion (String dni, Usuario u) {
        if (u.getDni().equals(dni)) {
            return true; // SI EL dni SIGUE SIENDO EL MISMO RETORNO TRUE
        }
        else {
            for (Usuario user : listaUsuarios) {
                if (user.getDni().equals(dni)) {
                    return false; // SI EL DNI NUEVO COINCIDE CON ALGUN OTRO RETORNO FALSE
                }
            }
            return true; // SI NO LO ENCONTRO ENTONCES ESTA DISPONIBLE Y RETORNO TRUE
        }
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
            }
        }
        return u;
    }


    public Usuario getUsuarioById(int id) {
        Usuario user = null;
        for (Usuario u : listaUsuarios) {
            if (u.getId() == id) {
                user = u;
                return user;
            }
        }
        return user;
    }

    public Usuario obtenerUsuarioLogueado (Usuario u) {
        for (Usuario user : listaUsuarios) {
            if (u.equals(user)) {
                return user;
            }
        }
        return null;
    }

    public void modificarAltaUsuario (Usuario u) {

        if (u.getAlta() == 0) {
            u.setAlta(1);
        }
        else {
            u.setAlta(0);
        }
    }



}
