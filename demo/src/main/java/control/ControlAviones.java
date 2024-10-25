package control;

import entidades.Avion;

import java.util.ArrayList;
import java.util.Collections;

public class ControlAviones {

    ArrayList<Avion> listaAviones;


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


}
