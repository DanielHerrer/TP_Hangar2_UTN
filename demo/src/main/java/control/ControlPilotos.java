package control;

import constantes.Archivos;
import entidades.Avion;
import entidades.Piloto;
import entidades.Usuario;
import enums.Genero;
import enums.Rango;
import excepciones.FormatoIncorrectoException;
import excepciones.ObjetoInexistenteException;
import excepciones.ObjetoRepetidoException;
import interfaces.iABML;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ControlPilotos implements iABML<Piloto> {

    public ArrayList<Piloto> listaPilotos;

    public ControlPilotos() {
        listaPilotos = new ArrayList<>();
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

    public int modificarAltaPiloto (int id) {
        Piloto piloto = null;
        boolean encontrado = false;

        for (Piloto pil : listaPilotos) {
            if (id == pil.getId()) {
                if (pil.getAlta() == 1) {
                    pil.setAlta(0);
                    piloto = pil;
                    encontrado = true;
                }
                else {
                    pil.setAlta(1);
                    piloto = pil;
                    encontrado = true;
                }
            }
        }
        if (encontrado) {
            return piloto.getAlta();
        }
        else {
            return -1; //RETORNO -1 SI NO ENCONTRO AL AVION
        }
    }

    public Piloto buscarPilotoPorID (int id) {
        Piloto piloto = null;

        for (Piloto p : listaPilotos) {
            if (p.getId() == id) {
                piloto = p;
            }
        }
        return piloto;
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

        Archivos.grabar(Archivos.archivoPilotos, pilotoArray);

        /*
        try (FileWriter file = new FileWriter(Archivos.archivoPilotos)) {
            file.write(pilotoArray.toString(4));
            System.out.println("Pilotos guardadas en el archivo: " + Archivos.archivoPilotos);

        } catch (IOException e) {
            e.printStackTrace();
        }
         */
    }

    public void cargarPilotoDesdeArchivo () {
        try {

            JSONArray tasksArray = new JSONArray(Archivos.leerArchivo(Archivos.archivoPilotos));

            pilotoJSONArrayToList(tasksArray);
            System.out.println("Pilotos cargados desde el archivo: " + Archivos.archivoPilotos);
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
        int alta = json.getInt("alta");
        return new Piloto(dni, nombreApellido, genero, anioNacimiento, id, numeroLicencia, horasVuelo, rango, alta);
    }
}
