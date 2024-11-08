package interfaces;

import excepciones.FormatoIncorrectoException;
import excepciones.ObjetoInexistenteException;
import excepciones.ObjetoRepetidoException;

import javax.swing.text.StyledEditorKit;

// iABML // Alta Baja Modificacion Lectura
// iCRUD // Create Read Update Delete
public interface iABML<T> {

    public boolean agregar (T t) throws FormatoIncorrectoException, ObjetoRepetidoException;

    public boolean eliminar (T t) throws FormatoIncorrectoException, ObjetoInexistenteException;

    public boolean modificar (T t);

    public boolean listar (T t);

}
