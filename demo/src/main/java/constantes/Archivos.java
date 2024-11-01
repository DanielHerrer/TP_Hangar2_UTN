package constantes;

import control.ControlAviones;
import control.ControlPilotos;
import control.ControlUsuarios;
import entidades.Avion;
import entidades.Piloto;
import entidades.Usuario;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Archivos {

    public static final String archivoPilotos = "src/main/java/Files/pilotos.json";
    public static final String archivoUsuarios = "src/main/java/Files/usuarios.json";
    public static final String archivoAvion = "src/main/java/Files/aviones.json";
    public static final String archivoHangar = "src/main/java/Files/hangar.json";

    public static ControlUsuarios controlUsuarios = new ControlUsuarios();
    public static ControlPilotos controlPilotos = new ControlPilotos();
    public static ControlAviones controlAviones = new ControlAviones();

    public static int obtenerUltimoIdUsuario () {
        controlUsuarios.cargarUsuarioDesdeArchivo();

        Usuario u = controlUsuarios.listaUsuarios.get(controlUsuarios.listaUsuarios.size()-1);

        return  u.getId() + 1;
    }

    public static int obtenerUltimoIdPiloto () {
        controlPilotos.cargarPilotoDesdeArchivo();

        Piloto p = controlPilotos.listaPilotos.get(controlPilotos.listaPilotos.size()-1);

        return  p.getId() + 1;
    }

    public static int obtenerUltimoIdAvion () {
        controlAviones.cargarAvionDesdeArchivo();

        Avion a = controlAviones.listaAviones.get(controlAviones.listaAviones.size()-1);

        return  a.getId() + 1;
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

}
