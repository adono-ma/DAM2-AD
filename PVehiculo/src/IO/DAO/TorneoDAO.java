/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IO.DAO;

import Modelo.Torneo;
import Modelo.Vehiculo;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ixchel
 */
public interface TorneoDAO {
    public abstract boolean create (Torneo t) throws SQLException;
    public abstract boolean delete (int pk) throws SQLException;
    public abstract Torneo read (int pk) throws SQLException;
    public abstract ArrayList<Torneo> readAll() throws SQLException;
    public abstract boolean update (Torneo t) throws SQLException;
    public abstract boolean guardarResultadosTorneo (Torneo t)  throws SQLException;
    /*Al finalizar Torneo insertar en la tabla Participa los resultados: (pk torneo, pk Vehiculo y Posicion)
        - En metodo Iniciar -> Lo ejecuta desde Torneo
        - 
    */
    
    //public abstract boolean guardarParticipacion (int codV, int codT, int posicion, int puntos) throws SQLException;
    /*
    public abstract boolean guardarTodos (int codT, ArrayList<Vehiculo> vs) throws SQLException;
    public abstract ArrayList<Vehiculo> participantesT (int codT) throws SQLException;
    public abstract int posicionVT (int codV, int codT) throws SQLException;//para la posición de un coche en un torneo concreto??
    public abstract int puntosVT (int codV, int codT) throws SQLException;//para los puntos de un coche en un torneo concreto??
    public abstract boolean eliminarParticipante (int codV, int codT) throws SQLException;//para los puntos de un coche en un torneo concreto??
    public abstract boolean actualizarInscripcion (int codT, boolean inscripcion) throws SQLException;
    public abstract int numParticipantes (int codT) throws SQLException;
    public abstract boolean torneoLleno (int codT) throws SQLException;
    public abstract ArrayList<Vehiculo> rankingT (int codT) throws SQLException;
    
    */
    
    /*
    boolean create (Vehiculo v) -X
    bolean delete (Vehiculo v)
    Vehiculo read (int pk) -X
    boolean readAll (AL<Vehiculo>) -X
    boolean update (Vehiculo v)
    
    
    tanto en jdbc como en fichero
    */
}
