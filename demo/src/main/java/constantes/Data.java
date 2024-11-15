package constantes;

import gestores.GestorAviones;
import gestores.GestorPilotos;
import gestores.GestorUsuarios;
import entidades.Avion;
import entidades.Piloto;
import entidades.Usuario;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Data {

    public static Usuario userLogueado = null;
    public static Integer idAux = null;
    public static boolean radarTerminado = false;

    public static final String archivoPilotos = "src/main/java/Files/pilotos.json";
    public static final String archivoUsuarios = "src/main/java/Files/usuarios.json";
    public static final String archivoAvion = "src/main/java/Files/aviones.json";
    public static final String archivoHangar = "src/main/java/Files/hangar.json";
    public static final String archivoRegistros = "src/main/java/Files/registros.json";
    public static final String archivoVuelos = "src/main/java/Files/vuelos.json";

    public static GestorUsuarios gestorUsuarios = new GestorUsuarios();
    public static GestorPilotos gestorPilotos = new GestorPilotos();
    public static GestorAviones gestorAviones = new GestorAviones();

    public static Usuario getUserLogueado() {
        return userLogueado;
    }

    public static void setUserLogueado(Usuario userLogueado) {
        Data.userLogueado = userLogueado;
    }

    public static Integer getIdAux() {
        return idAux;
    }

    public static void setIdAux(Integer idAux) {
        Data.idAux = idAux;
    }

    public static boolean getRadarTerminado() {
        return radarTerminado;
    }

    public static void setRadarTerminado(boolean radarTerminado) {
        Data.radarTerminado = radarTerminado;
    }

    // ============= METODOS PARA OBTENER EL ULTIMO ID DE UN REGISTRO ======================

    public static int obtenerUltimoIdUsuario () {
        gestorUsuarios.cargarDesdeArchivo();

        if (gestorUsuarios.listaUsuarios.isEmpty()) {
            return 1;
        }

        Usuario u = gestorUsuarios.listaUsuarios.get(gestorUsuarios.listaUsuarios.size()-1);

        return  u.getId() + 1;
    }

    public static int obtenerUltimoIdPiloto () {
        gestorPilotos.cargarDesdeArchivo();

        if (gestorPilotos.listaPilotos.isEmpty()) {
            return 1;
        }

        Piloto p = gestorPilotos.listaPilotos.get(gestorPilotos.listaPilotos.size()-1);

        return  p.getId() + 1;
    }

    public static int obtenerUltimoIdAvion () {
        gestorAviones.cargarDesdeArchivo();

        if (gestorAviones.listaAviones.isEmpty()) {
            return 1;
        }

        Avion a = gestorAviones.listaAviones.get(gestorAviones.listaAviones.size()-1);

        return  a.getId() + 1;
    }


    // PERMITE GUARDAR UN JSONOBJECT EN UN ARCHIVO
    public static void grabar(String nombreArchivo, JSONObject jsonObject) {
        try {
            FileWriter fileWriter = new FileWriter(nombreArchivo);
            fileWriter.write(jsonObject.toString(4));
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    // PERMITE GUARDAR UN JSONARRAY EN UN ARCHIVO
    public static void grabar(String nombreArchivo, JSONArray jsonArray) {
        try {
            FileWriter fileWriter = new FileWriter(nombreArchivo);
            fileWriter.write(jsonArray.toString(4));
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONTokener leerArchivo(String nombreArchivo){
        JSONTokener tokener = null;
        try{
            tokener= new JSONTokener(new FileReader(nombreArchivo));
        }catch(FileNotFoundException | JSONException e){
            e.printStackTrace();
        }
        return tokener;
    }

}
