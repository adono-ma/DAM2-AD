/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import IO.DAO.BBDD;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ixchel
 */
public class MainBBDD_Transacciones {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { //
        // TODO code application logic here
        //BBDD bbdd = new BBDD();
        boolean crearBBDD = BBDD.crearBBDD(); 
//        
//        try {
//            Connection con = BBDD.getConnection();
//            Statement stat = con.createStatement();         
//            stat.executeUpdate("INSERT INTO torneo (codigo, nombre, fecha, plazas) VALUES (1, 'Torneo 1', '2025-10-16', 5)");
//            System.out.println("Torneo 1 insertado correctamente.");
//
//        } catch (SQLException e) {
//            System.out.println("Error de insercion sin transaccion");
//        }
//        
//        System.out.println("Torneo sin transaccion:");
//        insertarSinTransaccion();
//        
        System.out.println("Torneo con transaccion:");
        insertarConTransaccion();
    
    }    
    
    private static void insertarSinTransaccion() {
        try {
            Connection con = BBDD.getConnection();
            Statement stat = con.createStatement();
            
            stat.executeUpdate("INSERT INTO torneo (codigo, nombre, fecha, plazas) VALUES (2, 'Torneo 2', '2025-09-16', 6)");
            System.out.println("Torneo 2 insertado correctamente.");
            
            stat.executeUpdate("INSERT INTO torneo (codigo, nombre, fecha, plazas) VALUES (3, 'Torneo 3', '2024-10-18', 3)");
            System.out.println("Torneo 3 insertado correctamente.");
            
            stat.executeUpdate("INSERT INTO torneo (codigo, nombre, fecha, plazas) VALUES (1, 'Torneo 1', '2025-10-16', 5)");
            System.out.println("Torneo 1 insertado correctamente.");

            
        } catch (SQLException e) {
            System.out.println("Error de insercion sin transaccion");
        }
    }
    
    private static void insertarConTransaccion() {//Este espera a que se hagan todos los insert antes de ejecutar para asegurarse de que no haya duplicados
        Connection con = null;
        try {
            con = BBDD.getConnection();
            Statement stat = con.createStatement();
            
            con.setAutoCommit(false);
            //todos los INSERT se ejecutan automáticamente (y luego peta en la BBDD porque no puede haber dos pk iguales)
            
            stat.executeUpdate("INSERT INTO torneo (codigo, nombre, fecha, plazas) VALUES (2, 'Torneo 2', '2025-09-16', 6)");
            System.out.println("Torneo 2 insertado correctamente.");
            
            stat.executeUpdate("INSERT INTO torneo (codigo, nombre, fecha, plazas) VALUES (3, 'Torneo 3', '2024-10-18', 3)");
            System.out.println("Torneo 3 insertado correctamente.");
            
            stat.executeUpdate("INSERT INTO torneo (codigo, nombre, fecha, plazas) VALUES (1, 'Torneo 1', '2025-10-16', 5)");
            System.out.println("Torneo 1 insertado correctamente.");
            //esto debería petar en el trigger
            con.commit();
            System.out.println("Transaccion completada correctamente.");
            
            stat.close();
            con.close();
            
        } catch (SQLException e) {
            System.out.println("Error de insercion sin transaccion.");
        }
        try {
            if (con != null) {
                con.rollback();
                System.out.println("Ningun Torneo fue insertado en la tabla Torneo.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al hacer rollback.");
        }
        
    }
    
    private static void insertarTorneoCompleto() {
        Connection con = null;
        try {
            con = BBDD.getConnection();
            con.setAutoCommit(false); // Iniciar transacción

            // 1. Insertar el torneo
            String queryTorneo = "INSERT INTO torneo (nombre, fecha, plazas, inscripcion) VALUES (?, ?, ?, ?)";
            PreparedStatement psTorneo = con.prepareStatement(queryTorneo, Statement.RETURN_GENERATED_KEYS);
            psTorneo.setString(1, "Torneo Completo 2025");
            psTorneo.setString(2, "2025-12-20");
            psTorneo.setInt(3, 8);
            psTorneo.setString(4, "abierto");
            psTorneo.executeUpdate();

            // Obtener el ID del torneo insertado
            ResultSet rsKeys = psTorneo.getGeneratedKeys();
            if (!rsKeys.next()) {
                throw new SQLException("No se pudo obtener el ID del torneo");
            }
            int idTorneo = rsKeys.getInt(1);
            System.out.println("  Torneo creado con ID: " + idTorneo);

            // 2. Insertar circuitos para el torneo
            String queryCircuito = "INSERT INTO circuito (cod_torneo, curva, recta) VALUES (?, ?, ?)";
            PreparedStatement psCircuito = con.prepareStatement(queryCircuito);
            
            // Circuito 1: Muchas rectas
            psCircuito.setInt(1, idTorneo);
            psCircuito.setInt(2, 3);
            psCircuito.setInt(3, 10);
            psCircuito.executeUpdate();
            System.out.println("  Circuito 1 creado (rectas: 10, curvas: 3)");

            // Circuito 2: Muchas curvas
            psCircuito.setInt(1, idTorneo);
            psCircuito.setInt(2, 10);
            psCircuito.setInt(3, 3);
            psCircuito.executeUpdate();
            System.out.println("  Circuito 2 creado (rectas: 3, curvas: 10)");

            // 3. Insertar trofeos
            String queryTrofeo = "INSERT INTO trofeo (idTorneo, tipo, modelo) VALUES (?, ?, ?)";
            
            // Trofeo ORO
            PreparedStatement psTrofeo = con.prepareStatement(queryTrofeo, Statement.RETURN_GENERATED_KEYS);
            psTrofeo.setInt(1, idTorneo);
            psTrofeo.setString(2, "ORO");
            psTrofeo.setString(3, "COPA");
            psTrofeo.executeUpdate();
            
            ResultSet rsTrofeoOro = psTrofeo.getGeneratedKeys();
            if (rsTrofeoOro.next()) {
                int idTrofeoOro = rsTrofeoOro.getInt(1);
                
                // Insertar detalle de Oro
                String queryOro = "INSERT INTO oro (idTrofeo, manejo, velocidad) VALUES (?, ?, ?)";
                PreparedStatement psOro = con.prepareStatement(queryOro);
                psOro.setInt(1, idTrofeoOro);
                psOro.setInt(2, 3);
                psOro.setInt(3, 3);
                psOro.executeUpdate();
                psOro.close();
                System.out.println("  Trofeo ORO creado (bonus: +3 manejo, +3 velocidad)");
            }

            // Trofeo PLATA
            psTrofeo.setInt(1, idTorneo);
            psTrofeo.setString(2, "PLATA");
            psTrofeo.setString(3, "MEDALLA");
            psTrofeo.executeUpdate();
            
            ResultSet rsTrofeoPlata = psTrofeo.getGeneratedKeys();
            if (rsTrofeoPlata.next()) {
                int idTrofeoPlata = rsTrofeoPlata.getInt(1);
                
                String queryPlata = "INSERT INTO plata (idTrofeo, manejo, velocidad) VALUES (?, ?, ?)";
                PreparedStatement psPlata = con.prepareStatement(queryPlata);
                psPlata.setInt(1, idTrofeoPlata);
                psPlata.setInt(2, 2);
                psPlata.setInt(3, 2);
                psPlata.executeUpdate();
                psPlata.close();
                System.out.println("  Trofeo PLATA creado (bonus: +2 manejo, +2 velocidad)");
            }

            // Trofeo BRONCE
            psTrofeo.setInt(1, idTorneo);
            psTrofeo.setString(2, "BRONCE");
            psTrofeo.setString(3, "PLATO");
            psTrofeo.executeUpdate();
            
            ResultSet rsTrofeoBronce = psTrofeo.getGeneratedKeys();
            if (rsTrofeoBronce.next()) {
                int idTrofeoBronce = rsTrofeoBronce.getInt(1);
                
                String queryBronce = "INSERT INTO bronce (idTrofeo, manejo, velocidad) VALUES (?, ?, ?)";
                PreparedStatement psBronce = con.prepareStatement(queryBronce);
                psBronce.setInt(1, idTrofeoBronce);
                psBronce.setInt(2, 1);
                psBronce.setInt(3, 1);
                psBronce.executeUpdate();
                psBronce.close();
                System.out.println("  Trofeo BRONCE creado (bonus: +1 manejo, +1 velocidad)");
            }

            // Commit de toda la transacción
            con.commit();
            System.out.println("✓ Torneo completo insertado exitosamente con circuitos y trofeos");

            // Cerrar recursos
            psTorneo.close();
            psCircuito.close();
            psTrofeo.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("✗ Error al insertar torneo completo: " + e.getMessage());
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback();
                    System.out.println("✓ ROLLBACK completado.");
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("✗ Error al hacer rollback: " + ex.getMessage());
            }
        }
    }

    /**
     * Muestra todos los datos insertados
     */
    private static void mostrarDatosFinales() {
        Connection con = BBDD.getConnection();
        if (con == null) {
            System.out.println("Error: No se pudo conectar a la base de datos");
            return;
        }

        try {
            // Mostrar torneos
            System.out.println("\n--- TORNEOS ---");
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM torneo ORDER BY codigo");
            while (rs.next()) {
                System.out.println(String.format("  ID: %d | Nombre: %s | Fecha: %s | Plazas: %d | Estado: %s",
                        rs.getInt("codigo"),
                        rs.getString("nombre"),
                        rs.getDate("fecha"),
                        rs.getInt("plazas"),
                        rs.getString("inscripcion")));
            }
            rs.close();

            // Mostrar circuitos
            System.out.println("\n--- CIRCUITOS ---");
            rs = stat.executeQuery("SELECT * FROM circuito ORDER BY codigo");
            while (rs.next()) {
                System.out.println(String.format("  ID: %d | Torneo: %d | Curvas: %d | Rectas: %d",
                        rs.getInt("codigo"),
                        rs.getInt("cod_torneo"),
                        rs.getInt("curva"),
                        rs.getInt("recta")));
            }
            rs.close();

            // Mostrar trofeos
            System.out.println("\n--- TROFEOS ---");
            rs = stat.executeQuery("SELECT * FROM trofeo ORDER BY codigo");
            while (rs.next()) {
                System.out.println(String.format("  ID: %d | Torneo: %d | Tipo: %s | Modelo: %s",
                        rs.getInt("codigo"),
                        rs.getInt("idTorneo"),
                        rs.getString("tipo"),
                        rs.getString("modelo")));
            }
            rs.close();

            // Mostrar detalles de trofeos ORO
            System.out.println("\n--- DETALLES TROFEOS ORO ---");
            rs = stat.executeQuery(
                    "SELECT o.codigo, o.idTrofeo, o.manejo, o.velocidad, t.tipo " +
                    "FROM oro o JOIN trofeo t ON o.idTrofeo = t.codigo ORDER BY o.codigo");
            while (rs.next()) {
                System.out.println(String.format("  ID: %d | Trofeo: %d | Tipo: %s | Bonus Manejo: %d | Bonus Velocidad: %d",
                        rs.getInt("codigo"),
                        rs.getInt("idTrofeo"),
                        rs.getString("tipo"),
                        rs.getInt("manejo"),
                        rs.getInt("velocidad")));
            }
            rs.close();

            // Mostrar detalles de trofeos PLATA
            System.out.println("\n--- DETALLES TROFEOS PLATA ---");
            rs = stat.executeQuery(
                    "SELECT p.codigo, p.idTrofeo, p.manejo, p.velocidad, t.tipo " +
                    "FROM plata p JOIN trofeo t ON p.idTrofeo = t.codigo ORDER BY p.codigo");
            while (rs.next()) {
                System.out.println(String.format("  ID: %d | Trofeo: %d | Tipo: %s | Bonus Manejo: %d | Bonus Velocidad: %d",
                        rs.getInt("codigo"),
                        rs.getInt("idTrofeo"),
                        rs.getString("tipo"),
                        rs.getInt("manejo"),
                        rs.getInt("velocidad")));
            }
            rs.close();

            // Mostrar detalles de trofeos BRONCE
            System.out.println("\n--- DETALLES TROFEOS BRONCE ---");
            rs = stat.executeQuery(
                    "SELECT b.codigo, b.idTrofeo, b.manejo, b.velocidad, t.tipo " +
                    "FROM bronce b JOIN trofeo t ON b.idTrofeo = t.codigo ORDER BY b.codigo");
            while (rs.next()) {
                System.out.println(String.format("  ID: %d | Trofeo: %d | Tipo: %s | Bonus Manejo: %d | Bonus Velocidad: %d",
                        rs.getInt("codigo"),
                        rs.getInt("idTrofeo"),
                        rs.getString("tipo"),
                        rs.getInt("manejo"),
                        rs.getInt("velocidad")));
            }
            rs.close();

            // Mostrar vehículos (si hay)
            System.out.println("\n--- VEHÍCULOS ---");
            rs = stat.executeQuery("SELECT * FROM vehiculo ORDER BY codigo");
            int countVehiculos = 0;
            while (rs.next()) {
                countVehiculos++;
                System.out.println(String.format("  Código: %d | Tipo: %s | Velocidad: %d | Manejo: %d | Puntos: %d",
                        rs.getInt("codigo"),
                        rs.getString("tipo"),
                        rs.getInt("velocidad"),
                        rs.getInt("manejo"),
                        rs.getInt("puntos")));
            }
            if (countVehiculos == 0) {
                System.out.println("  (No hay vehículos registrados todavía)");
            }
            rs.close();

            // Mostrar participaciones (si hay)
            System.out.println("\n--- PARTICIPACIONES ---");
            rs = stat.executeQuery("SELECT * FROM participa ORDER BY codigo");
            int countParticipaciones = 0;
            while (rs.next()) {
                countParticipaciones++;
                System.out.println(String.format("  ID: %d | Vehículo: %d | Torneo: %d | Posición: %d | Puntos: %d",
                        rs.getInt("codigo"),
                        rs.getInt("cod_vehiculo"),
                        rs.getInt("cod_torneo"),
                        rs.getInt("posicion"),
                        rs.getInt("puntos")));
            }
            if (countParticipaciones == 0) {
                System.out.println("  (No hay participaciones registradas todavía)");
            }
            rs.close();

            stat.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error al mostrar datos finales: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
