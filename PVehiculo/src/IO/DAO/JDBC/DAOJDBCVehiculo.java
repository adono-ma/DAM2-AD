/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IO.DAO.JDBC;

import IO.DAO.BBDD;
import Modelo.Vehiculo;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.SQLException;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import IO.DAO.VehiculoDAO;

/**
 *http://localhost/phpmyadmin/
 * @author ixchel
 */
public class DAOJDBCVehiculo implements VehiculoDAO {
    private Connection con = null;
    
public DAOJDBCVehiculo() {
        this.con = BBDD.getConnectionSingleton();
    }
    
//    public DAOJDBCVehiculo() {
//        String cadena_conexion = "jdbc:mysql://localhost:3306/";
//        String nombre_BBDD = "TorneoJDBC";
//        String usuario = "BBDDAdmin";
//        String contrasenya = "!!2allaoT4";
//
//        try {
//            con = DriverManager.getConnection(cadena_conexion + nombre_BBDD, usuario, contrasenya);
//        } catch (SQLException ex) {
//            System.out.println("Error al conectar con la base de datos");
//            ex.printStackTrace();
//        }
//    }
//    
//    public void cerrarConexion() {
//        //se hace aquí el exception para que cierre la conexión al final de todas las operaciones
//        try {
//            con.close();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            System.out.println("Error al cerrar la conexion.");
//        }
//    }
 

    @Override
    public boolean create(Vehiculo v) {
        String query = "INSERT INTO vehiculo (codigo, tipo, velocidad, manejo, puntos) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, v.getCodigo());
            ps.setString(2, String.valueOf(v.getTipo()));
            ps.setInt(3, v.getVelocidad());
            ps.setInt(4, v.getManejo());
            ps.setInt(5, v.getPuntos());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al introducir los datos.");
            return false;
        }
    }
        
    @Override
  public boolean delete (Vehiculo v) {
      String query = "DELETE FROM vehiculo WHERE codigo = ?";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, v.getCodigo());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
  }
    public Vehiculo read (int pk) {
      String query = "SELECT * FROM vehiculo WHERE codigo = ?";
        Vehiculo v = null;
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, pk);
            ResultSet rs = ps.executeQuery();
            rs.next();
            v = new Vehiculo (rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));

            return v;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error de busqueda.");
        }
        return v;
        /*
        select asterisco from usuarios
        el result-set se itera con el while .next funciona como los cursores,
        es un fetch, como solo hay 1 resultado,
        se one resultset.next directamente y a tirar.
        con el usr.get pues ya te saca el id que sea.
        */
  }
    
    @Override
    public ArrayList<Vehiculo> readAll(){
      ArrayList<Vehiculo> v = new ArrayList<>();
        String query = "SELECT * FROM vehiculo";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vehiculo aux = new Vehiculo (rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
                v.add(aux);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error de lista.");
        }
        return v;
        /*
        el listado lo mete en un arraylist
         */
  }
    
    
    @Override
    public boolean update (Vehiculo v){
       String query = "UPDATE vehiculo SET tipo = ?, velocidad = ?, manejo = ?, puntos = ? WHERE codigo = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, String.valueOf(v.getTipo()));
            ps.setInt(2, v.getVelocidad());
            ps.setInt(3, v.getManejo());
            ps.setInt(4, v.getPuntos());
            ps.setInt(5, v.getCodigo());
            ps.executeUpdate();
            //ps.close();
            
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al actualizar los datos.");
            return false;
        }
  }

   
    

    /*
    boolean create (Vehiculo v) -X
    bolean delete (Vehiculo v)
    Vehiculo read (int pk) -X
    boolean readAll (AL<Vehiculo>) -X
    boolean update (Vehiculo v)
    
    
    tanto en jdbc como en fichero
    */
}
