package gestores;

import constantes.Data;
import entidades.Piloto;
import enums.Genero;
import enums.Rango;
import excepciones.FormatoIncorrectoException;
import excepciones.ObjetoInexistenteException;
import excepciones.ObjetoRepetidoException;
import interfaces.iABML;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GestorPilotos implements iABML<Piloto> {

    public ArrayList<Piloto> listaPilotos;

    public GestorPilotos() {
        listaPilotos = new ArrayList<>();
    }

    public ArrayList<Piloto> getListaPilotos() {
        return listaPilotos;
    }

    public void setListaPilotos(ArrayList<Piloto> listaPilotos) {
        this.listaPilotos = listaPilotos;
    }

    // ======================= METODOS INTERFAZ =====================================

    @Override
    public boolean agregar(Piloto p) throws FormatoIncorrectoException, ObjetoRepetidoException {
        if (listaPilotos.contains(p)) {
            throw new ObjetoRepetidoException("El piloto ya esta cargado en el sistema");
        }
        if (p.getId() != 0  && p.getNumeroLicencia() != null && p.getRango() != null) {
            listaPilotos.add(p);
            return true;
        }
        else {
            throw new FormatoIncorrectoException("El formato introducido no es correcto");
        }
    }

    @Override
    public boolean eliminar(Piloto p) throws FormatoIncorrectoException, ObjetoInexistenteException {
        if (p.getId() != 0  && p.getNumeroLicencia() != null && p.getRango() != null) {
            if (listaPilotos.contains(p)) {
                p.setAlta(0);
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
    public boolean modificar(Piloto p) {
        return false;
    }

    @Override
    public boolean listar(Piloto p) {
        return false;
    }

    // ================================================================================

    public Piloto getPilotoPorID(int id) {
        Piloto piloto = null;

        for (Piloto p : listaPilotos) {
            if (p.getId() == id) {
                piloto = p;
            }
        }
        return piloto;
    }

    // SETEO EL ESTADO DEL PILOTO A TRUE Y GUARDO LOS CAMBIOS
    public boolean actualizarEstadoPiloto (Piloto pilotoHangar) {
        for (Piloto p : listaPilotos) {
            if (p.equals(pilotoHangar)) {
                p.setDisponible(true);
                guardarPilotoToFile();
                return true;
            }
        }
        return false;
    }

    // DEVUELVE TRUE SI EL DNI YA EXISTE
    public boolean dniYaExiste(String dni) {
        for (Piloto piloto : listaPilotos) {
            if (piloto.getDni().equals(dni)) {
                return true;
            }
        }
        return false;
    }

    public boolean verificarLicencia (String licencia) {
        for (Piloto pil : listaPilotos) {
            if (pil.getNumeroLicencia().equals(licencia)) {
                return true; // SI ESTA REPETIDO RETORNO TRUE
            }
        }
        return false;
    }

    public boolean verificarLicenciaModificacion (String licenciaNueva, Piloto piloto) {
        if (piloto.getNumeroLicencia().equals(licenciaNueva)) {
            return false; // SI LA LICENCIA SIGUE SIENDO LA MISMA RETORNO FALSE
        }
        for (Piloto pil : listaPilotos) {
            if (pil.getNumeroLicencia().equals(licenciaNueva)) {
                return true; // SI SE REPITE CON OTRO PILOTO RETORNO TRUE
            }
        }
        return false;
    }

    public boolean comprobarDniModificacion (String dni, Piloto p) {
        if (p.getDni().equals(dni)) {
            return false; // SI EL dni SIGUE SIENDO EL MISMO RETORNO FALSE
        }
        else {
            for (Piloto pil : listaPilotos) {
                if (pil.getDni().equals(dni)) {
                    return false; // SI EL DNI NUEVO COINCIDE CON ALGUN OTRO RETORNO TRUE
                }
            }
        }
        return false;
    }

    /**
     * @see "Verifica si un piloto esta o no el sistema, se basa en su numero de licencia"
     * @param p es el Piloto que se quiere crear
     * @return devuelve un boolean de acuerdo a si esta o no
     */
    public boolean verificarPiloto (Piloto p) {
        cargarPilotoDesdeArchivo();

        return listaPilotos.contains(p);

    }


    //======================METODOS PARA TRABAJAR CON JSON============================
    public JSONArray crearJSONArray () {
        JSONArray jsonArray = new JSONArray();

        for (Piloto pil : listaPilotos) {
            jsonArray.put(pilotoToJSONObject(pil));
        }
        return jsonArray;
    }

    public void guardarPilotoToFile() {
        JSONArray pilotoArray = crearJSONArray();

        Data.grabar(Data.archivoPilotos, pilotoArray);
    }

    public void cargarPilotoDesdeArchivo () {
        try {

            JSONArray tasksArray = new JSONArray(Data.leerArchivo(Data.archivoPilotos));

            pilotoJSONArrayToList(tasksArray);
            System.out.println("Pilotos cargados desde el archivo: " + Data.archivoPilotos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pilotoJSONArrayToList(JSONArray jsonArray) {
        listaPilotos.clear();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject pilotoJSON = jsonArray.getJSONObject(i);
            Piloto task = JSONObjectToPiloto(pilotoJSON);
            listaPilotos.add(task);
        }
    }

    public static JSONObject pilotoToJSONObject(Piloto p) {
        JSONObject json = new JSONObject();
        json.put("dni", p.getDni());
        json.put("nombreApellido", p.getNombreApellido());
        json.put("genero", p.getGenero());
        json.put("anioNacimiento", p.getAnioNacimiento());
        json.put("id", p.getId());
        json.put("numeroLicencia", p.getNumeroLicencia());
        json.put("horasVuelo", p.getHorasVuelo());
        json.put("rango", p.getRango());
        json.put("disponible", p.isDisponible());
        json.put("alta", p.getAlta());
        return json;
    }

    public static Piloto JSONObjectToPiloto(JSONObject json) {
        String dni = json.getString("dni");
        String nombreApellido = json.getString("nombreApellido");
        String generoStr = json.getString("genero");
        Genero genero = Genero.valueOf(generoStr.toUpperCase());
        int anioNacimiento = json.getInt("anioNacimiento");
        int id = json.getInt("id");
        String numeroLicencia = json.getString("numeroLicencia");
        int horasVuelo = json.getInt("horasVuelo");
        String rangoStr = json.getString("rango");
        Rango rango = Rango.valueOf(rangoStr.toUpperCase());
        boolean disponible = json.getBoolean("disponible");
        int alta = json.getInt("alta");
        return new Piloto(dni, nombreApellido, genero, anioNacimiento, id, numeroLicencia, horasVuelo, rango, disponible, alta);
    }
}
