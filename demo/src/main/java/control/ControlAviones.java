package control;

import constantes.Archivos;
import entidades.Avion;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class ControlAviones {

    public ArrayList<Avion> listaAviones;

    public ControlAviones() {
        this.listaAviones = new ArrayList<>();

    }

    /**
     * @return Muestra a todos los aviones que estan dados de alta
     */
    public String mostrarAvionesDadosAlta () {

        StringBuilder sb = new StringBuilder("");

        for (Avion avi : listaAviones) {
            if (avi.getAlta() == 1) {
                sb.append(avi.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     *
     * @return Muestra a los aviones que estan dados de baja
     */
    public String mostrarAvionesDadosBaja () {

        StringBuilder sb = new StringBuilder("");

        for (Avion avi : listaAviones) {
            if (avi.getAlta() == 0) {
                sb.append(avi.toString()).append("\n");
            }
        }
        return sb.toString();
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
     * @see "Usa un arreglo auxiliar para no modificar le original"
     * @return retorna un arreglo auxiliar ordenado alfabeticamente
     */
    public String mostrarAvionesAlfabetico () {
        ArrayList<Avion> auxiliar = new ArrayList<>(listaAviones);
        Collections.sort(auxiliar);
        StringBuilder sb = new StringBuilder("");
        for (Avion avi : auxiliar) {
            sb.append(avi.toString()).append("\n");
        }
        return sb.toString();
    }


    /**
     * @see "Modifica la baja/alta de el avion elegido"
     * @param id recibe el id del avion que desea modificar (el paramatro deberia venir desde la vista)
     */
    public int modificarAltaAvion (int id) {
        Avion avion = null;
        boolean encontrado = false;

        for (Avion avi : listaAviones) {
            if (id == avi.getId()) {
                if (avi.getAlta() == 1) {
                    avi.setAlta(0);
                    avion = avi;
                    encontrado = true;
                }
                else {
                    avi.setAlta(1);
                    avion = avi;
                    encontrado = true;
                }
            }
        }
        if (encontrado) {
            return avion.getAlta();
        }
        else {
            return -1; //RETORNO -1 SI NO ENCONTRO AL AVION
        }
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
            jsonArray.put(avi.avionToJSONObject());
        }
        return jsonArray;
    }


    public void guardarAvionToFile() {
        JSONArray pilotoArray = crearJSONArray();

        try (FileWriter file = new FileWriter(Archivos.archivoAvion)) {
            file.write(pilotoArray.toString(4));
            System.out.println("Aviones guardados en el archivo: " + Archivos.archivoAvion);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarAvionDesdeArchivo () {
        try {
            JSONArray avionesArray = new JSONArray(Archivos.leerArchivo(Archivos.archivoAvion));

            avionJSONArrayToList(avionesArray);
            System.out.println("Aviones cargados desde el archivo: " + Archivos.archivoAvion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void avionJSONArrayToList(JSONArray jsonArray) {
        listaAviones.clear();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject avionJSON = jsonArray.getJSONObject(i);
            Avion avion = Avion.JSONObjectToAvion(avionJSON);
            listaAviones.add(avion);
        }
    }

}
