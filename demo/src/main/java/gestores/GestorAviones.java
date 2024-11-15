package gestores;

import constantes.Data;
import entidades.Avion;
import entidades.Piloto;
import excepciones.FormatoIncorrectoException;
import excepciones.ObjetoRepetidoException;
import interfaces.iMetodosJSON;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class GestorAviones implements iMetodosJSON {

    public ArrayList<Avion> listaAviones;

    public GestorAviones() {
        this.listaAviones = new ArrayList<>();
    }

    public ArrayList<Avion> getListaAviones() {
        return listaAviones;
    }

    // METODO PARA AGREGAR UN AVION A LA LISTA
    public boolean agregar(Avion a) throws FormatoIncorrectoException, ObjetoRepetidoException {
        if (a.getId() != 0  && a.getNombre() != null && a.getNumeracion() != 0) {
            listaAviones.add(a);
            return true;
        }
        else {
            throw new FormatoIncorrectoException("El formato introducido no es correcto");
        }
    }

    // SETEO A NULL EL PILOTO QUE ESTABA ASIGNADO EN EL AVION
    // Y GUARDO LOS CAMBIOS
    public boolean eliminarPilotoDeAvion (Avion avionH) {
        for (Avion avi : listaAviones) {
            if (avi.equals(avionH)) {
                avi.setPiloto(null);
                guardarEnArchivo();
                return true;
            }
        }
        return false;
    }

    public void actualizarPilotoModificado (Piloto p) {
        for (Avion avion : listaAviones) {
            if (avion.getPiloto().equals(p)) {
                avion.setPiloto(p);
            }
        }
    }

    public Avion getAvionPorID(int id) {
        for (Avion a : listaAviones) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    public boolean verificarNumeracionModifiacion (int numeracionNueva, Avion avion) {
        if (avion.getNumeracion() == numeracionNueva) {
            return false; // SI LA NUMERACION SIGUE SIENDO LA MISMA RETORNO FALSE
        }
        for (Avion avi : listaAviones) {
            if (avi.getNumeracion() == numeracionNueva) {
                return true; // SI SE REPITE CON OTRO AVION RETORNO TRUE
            }
        }
        return false;
    }

    public boolean numeracionYaExiste (int numeracion) {
        for (Avion avi : listaAviones) {
            if (avi.getNumeracion() == numeracion) {
                return true;
            }
        }
        return false;
    }


    //================METODOS PARA TRABAJAR CON JSON========================
    public JSONArray crearJSONArray () {
        JSONArray jsonArray = new JSONArray();

        for (Avion avi : listaAviones) {
            jsonArray.put(avionToJSONObject(avi));
        }
        return jsonArray;
    }

    @Override
    public void guardarEnArchivo() {
        JSONArray pilotoArray = crearJSONArray();

        Data.grabar(Data.archivoAvion, pilotoArray);
    }

    @Override
    public void cargarDesdeArchivo() {
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
