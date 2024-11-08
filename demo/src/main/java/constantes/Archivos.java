package constantes;

import control.ControlAviones;
import control.ControlPilotos;
import control.ControlUsuarios;
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

public class Archivos {

    public static Usuario userLogueado = null;
    public static Integer idAux = null;

    public static final String archivoPilotos = "src/main/java/Files/pilotos.json";
    public static final String archivoUsuarios = "src/main/java/Files/usuarios.json";
    public static final String archivoAvion = "src/main/java/Files/aviones.json";
    public static final String archivoHangar = "src/main/java/Files/hangar.json";
    public static final String archivoRegistros = "src/main/java/Files/registros.json";

    public static ControlUsuarios controlUsuarios = new ControlUsuarios();
    public static ControlPilotos controlPilotos = new ControlPilotos();
    public static ControlAviones controlAviones = new ControlAviones();

    public static Usuario getUserLogueado() {
        return userLogueado;
    }

    public static void setUserLogueado(Usuario userLogueado) {
        Archivos.userLogueado = userLogueado;
    }

    public static Integer getIdAux() {
        return idAux;
    }

    public static void setIdAux(Integer idAux) {
        Archivos.idAux = idAux;
    }

    // ============= METODOS PARA OBTENER EL ULTIMO ID DE UN REGISTRO ======================

    public static int obtenerUltimoIdUsuario () {
        controlUsuarios.cargarUsuarioDesdeArchivo();

        if (controlUsuarios.listaUsuarios.isEmpty()) {
            return 1;
        }

        Usuario u = controlUsuarios.listaUsuarios.get(controlUsuarios.listaUsuarios.size()-1);

        return  u.getId() + 1;
    }

    public static int obtenerUltimoIdPiloto () {
        controlPilotos.cargarPilotoDesdeArchivo();

        if (controlPilotos.listaPilotos.isEmpty()) {
            return 1;
        }

        Piloto p = controlPilotos.listaPilotos.get(controlPilotos.listaPilotos.size()-1);

        return  p.getId() + 1;
    }

    public static int obtenerUltimoIdAvion () {
        controlAviones.cargarAvionDesdeArchivo();

        if (controlAviones.listaAviones.isEmpty()) {
            return 1;
        }

        Avion a = controlAviones.listaAviones.get(controlAviones.listaAviones.size()-1);

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
