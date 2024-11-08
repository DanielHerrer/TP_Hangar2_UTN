package gestores;

import constantes.Data;
import entidades.Avion;
import entidades.Piloto;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class GestorAviones {

    public ArrayList<Avion> listaAviones;

    public GestorAviones() {
        this.listaAviones = new ArrayList<>();
    }


    // SETEO A NULL EL PILOTO QUE ESTABA ASIGNADO EN EL AVION
    // Y GUARDO LOS CAMBIOS
    public boolean eliminarPilotoDeAvion (Avion avionH) {
        for (Avion avi : listaAviones) {
            if (avi.equals(avionH)) {
                avi.setPiloto(null);
                guardarAvionToFile();
                return true;
            }
        }
        return false;
    }

    public Avion buscarAvionPorID (int id) {
        Avion avion = null;

        for (Avion a : listaAviones) {
            if (a.getId() == id) {
                avion = a;
            }
        }
        return avion;
    }

    /**
     *
     * @return Muestra a todos los aviones del sistema
     */
    public String mostrarAviones () {
        StringBuilder sb = new StringBuilder("");

        for (Avion avi : listaAviones) {
           sb.append(avi.toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     *
     * @return Muestra a los aviones pero destacando el nivel de combustible que tienen
     */
    public String mostrarNivelCombustible () {
        StringBuilder sb = new StringBuilder("");

        for (Avion avi : listaAviones) {
            sb.append(avi.getId()).append(" ").append(avi.getCombustibleActual()).append(" ").append(avi.getNombre()).
            append(" ").append(avi.getModelo()).append(" ").append(avi.getAerolinea()).append("\n");
        }
        return sb.toString();
    }


    /**
     *
     * @param nombre recibe el nombre o una subcadena del avion que se desea encontrar
     * @return devuelve un String con los aviones cuyo nombre coincide
     */
    public String buscarAvionPorNombre (String nombre) {
        StringBuilder sb = new StringBuilder("");
        boolean encontrado = false;

        for (Avion avi : listaAviones) {
            if (avi.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                sb.append(avi.toString()).append("\n");
                encontrado = true;
            }
        }

        if (!encontrado) {
            return "No hay coincidencias con el nombre ingresado \n";
        }
        return sb.toString();
    }


    /**
     * @see "Si el combustible de un avion es menor a 80 lo carga hasta el maximo"
     * @return devuelve un mensaje de acuerdo a si se cargaron o no los aviones
     */
    public String cargarCombustible () {
        boolean cargas = false;

        for (Avion avi : listaAviones) {
            if (avi.getCombustibleActual() < 80) {
                avi.setCombustibleActual(100);
                cargas = true;
            }
        }
        if (cargas) {
            return "Se lleno el tanque de todos los aviones \n";
        }
        else {
            return "Los aviones ya tienen el tanque lleno \n";
        }
    }

    //QUEDAN POR HACER LAS FUNCIONES DE MODIFICACION

    //================METODOS PARA TRABAJAR CON JSON========================
    public JSONArray crearJSONArray () {
        JSONArray jsonArray = new JSONArray();

        for (Avion avi : listaAviones) {
            jsonArray.put(avionToJSONObject(avi));
        }
        return jsonArray;
    }


    public void guardarAvionToFile() {
        JSONArray pilotoArray = crearJSONArray();

        Data.grabar(Data.archivoAvion, pilotoArray);
    }

    public void cargarAvionDesdeArchivo () {
        try {
            JSONArray avionesArray = new JSONArray(Data.leerArchivo(Data.archivoAvion));

            avionJSONArrayToList(avionesArray);
            System.out.println("Aviones cargados desde el archivo: " + Data.archivoAvion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void avionJSONArrayToList(JSONArray jsonArray) {
        listaAviones.clear();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject avionJSON = jsonArray.getJSONObject(i);
            Avion avion = JSONObjectToAvion(avionJSON);
            listaAviones.add(avion);
        }
    }

    // ========== METODOS JSON PARA EL HANGAR ==========================

    public static JSONObject avionToJSONObject(Avion a) {
        JSONObject json = new JSONObject();
        json.put("id", a.getId());
        if (a.getPiloto() != null) {
            json.put("piloto", GestorPilotos.pilotoToJSONObject(a.getPiloto()));
        } else {
            json.put("piloto", JSONObject.NULL); //SI NO TIENE UN PILOTO CARGADO USO JSONObject.NULL
        }
        json.put("nombre", a.getNombre());
        json.put("numeracion", a.getNumeracion());
        json.put("modelo", a.getModelo());
        json.put("aerolinea", a.getAerolinea());
        json.put("capacidadPasajeros", a.getCapacidadPasajeros());
        json.put("vuelosRealizados", a.getVuelosRealizados());
        json.put("combustibleMaximo", a.getCombustibleMaximo());
        json.put("combustibleActual", a.getCombustibleActual());
        json.put("alta", a.getAlta());
        return json;
    }

    public static Avion JSONObjectToAvion(JSONObject json) {
        int id = json.getInt("id");
        Piloto piloto = null;
        if (!json.isNull("piloto")) {
            piloto = GestorPilotos.JSONObjectToPiloto(json.getJSONObject("piloto"));
        }
        String nombre = json.getString("nombre");
        int numeracion = json.getInt("numeracion");
        String modelo = json.getString("modelo");
        String aerolinea = json.getString("aerolinea");
        int capacidadPasajeros = json.getInt("capacidadPasajeros");
        int vuelosRealizados = json.getInt("vuelosRealizados");
        int combustibleMaximo = json.getInt("combustibleMaximo");
        int combustibleActual = json.getInt("combustibleActual");
        int alta = json.getInt("alta");
        return new Avion(id, piloto, nombre, numeracion, modelo, aerolinea, capacidadPasajeros, vuelosRealizados, combustibleMaximo, combustibleActual, alta);
    }

}
