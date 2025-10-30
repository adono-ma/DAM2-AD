/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import IO.DAO.BBDD;
import IO.DAO.CSV.DAOCSVVehiculo;
import IO.DAO.CSV.FConfig;
import IO.DAO.JDBC.DAOJDBCVehiculo;
import Modelo.Vehiculo;
import Modelo.Torneo;
import java.util.ArrayList;

/**
 *
 * @author ixchel
 */
public class PVehiculo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //System.out.println("Primer Torneo de la vida");
        /*
        Torneo t1 = new Torneo(null, "Torneo Probatorio", 3);
        
        Vehiculo v1 = Vehiculo.generarVehiculos();
        Vehiculo v2 = Vehiculo.generarVehiculos();
        Vehiculo v3 = Vehiculo.generarVehiculos();
//        Vehiculo v4 = Vehiculo.generarVehiculos();
//        Vehiculo v5 = Vehiculo.generarVehiculos();
//        Vehiculo v6 = Vehiculo.generarVehiculos();
//        Vehiculo v7 = Vehiculo.generarVehiculos();
//        Vehiculo v8 = Vehiculo.generarVehiculos();
//        Vehiculo v9 = Vehiculo.generarVehiculos();
        
        //inscripciones
        t1.inscripcion(v1);
        t1.inscripcion(v2);
        t1.inscripcion(v3);
//        t1.inscripcion(v4);
//        t1.inscripcion(v5);
//        t1.inscripcion(v6);
//        t1.inscripcion(v7);
//        t1.inscripcion(v8);
//        t1.inscripcion(v9);
        
        //inicio
        System.out.println("===========================================");
        t1.iniciarTorneo();
        
        //ranking final
        System.out.println("===========================================");
        t1.ranking();
        
         */
        //Comprobación BBDD:
        BBDD bbdd = new BBDD();
        if (bbdd.crearBBDD()) {
            System.out.println("Se ha creado la base de datos correctamente.");
        } else {
            System.out.println("Error al crear la base de datos.");
        }
        //DAOJDBCVehiculo v = new DAOJDBCVehiculo();
        /*
        DAOCSVVehiculo vcsv = new DAOCSVVehiculo();
        
        //Se carga el código desde el archivo de configuración.
        Vehiculo.setContador(FConfig.cargarCodigo());

        //Crear un vehículo para guardarlo en el csv de vehículos:
        //vcsv.create(Vehiculo.generarVehiculos());
        ArrayList<Vehiculo> vehiculos = vcsv.readAll();
        //System.out.println("Numero total de vehiculos: " + vehiculos.size());

        for (int i = 0; i < vehiculos.size(); i++) {
            System.out.println((i+1) + ".- " + vehiculos.get(i));
        }
        
        FConfig.guardarCodigo(Vehiculo.getContador());
         */
        System.out.println("Numero de vehiculos actuales: " + Vehiculo.getContador());
        //Vehiculo.setContador(FConfig.cargarCodigo());
        Torneo t1 = new Torneo(null, "Torneo Probatorio", 3);

        Vehiculo v1 = Vehiculo.generarVehiculos();
        Vehiculo v2 = Vehiculo.generarVehiculos();
        Vehiculo v3 = Vehiculo.generarVehiculos();
//        Vehiculo v4 = Vehiculo.generarVehiculos();
//        Vehiculo v5 = Vehiculo.generarVehiculos();
//        Vehiculo v6 = Vehiculo.generarVehiculos();
//        Vehiculo v7 = Vehiculo.generarVehiculos();
//        Vehiculo v8 = Vehiculo.generarVehiculos();
//        Vehiculo v9 = Vehiculo.generarVehiculos();

        //inscripciones
        t1.inscripcion(v1);
        t1.inscripcion(v2);
        t1.inscripcion(v3);
//        t1.inscripcion(v4);
//        t1.inscripcion(v5);
//        t1.inscripcion(v6);
//        t1.inscripcion(v7);
//        t1.inscripcion(v8);
//        t1.inscripcion(v9);

        //inicio
        System.out.println("===========================================");
        t1.iniciarTorneo();

        //ranking final
        System.out.println("===========================================");
        t1.ranking();

        
        
        
        
        //FConfig.guardarCodigo(Vehiculo.getContador());
        
        bbdd.closeConnection();
        System.out.println(FConfig.cargarCodigo());
    }

}
