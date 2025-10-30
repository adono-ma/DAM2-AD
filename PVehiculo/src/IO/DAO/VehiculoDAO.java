/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IO.DAO;

import Modelo.Vehiculo;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Connection;

/**
 *
 * @author ixchel
 */
public interface VehiculoDAO {

    public abstract boolean create (Vehiculo v) throws SQLException;
    public abstract boolean delete (Vehiculo v) throws SQLException;
    public abstract Vehiculo read (int pk) throws SQLException;
    public abstract ArrayList<Vehiculo> readAll() throws SQLException;
    public abstract boolean update (Vehiculo v) throws SQLException;
    
    
    
    
    /*
    boolean create (Vehiculo v) -X
    bolean delete (Vehiculo v)
    Vehiculo read (int pk) -X
    boolean readAll (AL<Vehiculo>) -X
    boolean update (Vehiculo v)
    
    
    tanto en jdbc como en fichero
    */
}
