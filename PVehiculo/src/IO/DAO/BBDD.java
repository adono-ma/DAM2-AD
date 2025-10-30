/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IO.DAO;

import IO.DAO.CSV.FConfig;
import Modelo.Vehiculo;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 *
 * @author ixchel
 */
public class BBDD {

    private static Connection con = null;
//    private static final String cadena_conexion = "jdbc:mysql://localhost:3306/";
//    private static final String nombre_BBDD = "BBDDJDBC";
//    private static final String usuario = "root";
//    private static final String contrasenya = "";

    private static final String cadena_conexion = "jdbc:mysql://localhost:3306/";
    private static final String nombre_BBDD = "TorneoJDBC";
    private static final String usuario = "BBDDAdmin";
    private static final String contrasenya = "!!2allaoT4";

//    public BBDD() {//para guardar la conexion ????
//
//    }
    public static Connection getConnectionSingleton() {//Para devolver 1 única conexión (Singleton)
        try {
            if (con == null || con.isClosed()) {
                con = DriverManager.getConnection(cadena_conexion + nombre_BBDD, usuario, contrasenya);
                System.out.println("Se ha establecido la conexion con la BBDD " + nombre_BBDD + " (Singleton).");
            }
            Statement stat = con.createStatement();
            stat.execute("USE " + nombre_BBDD);
            stat.close();

            return con;

        } catch (SQLException ex) {
            System.out.println("Error al conectar con la base de datos");
            ex.printStackTrace();
            return null;
        }
    }

    public static Connection getConnection() {//Crear la conexion, una nueva cada vez que se llama.

        try {
            return DriverManager.getConnection(cadena_conexion + nombre_BBDD, usuario, contrasenya);

        } catch (SQLException ex) {
            System.out.println("Error al conectar con la base de datos");
            ex.printStackTrace();
            return null;
        }
    }

    public static Connection getConnectionNoDB() {//Cuando sabemos que no está la base de datos y queremos crearla
        try {
            return DriverManager.getConnection(cadena_conexion, usuario, contrasenya);

        } catch (SQLException ex) {
            System.out.println("Error al conectar con el servidor MySQL");
            ex.printStackTrace();
            return null;
        }
    }

    public void closeConnection() {
        //se hace aquí el exception para que cierre la conexión al final de todas las operaciones

        //Guardamos el código de vehículo:
        FConfig.guardarCodigo(Vehiculo.getContador());
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                con = null;//para resetear con
                System.out.println("Conexion cerrada correctamente.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al cerrar la conexion.");
        }
    }

    public static boolean crearBBDD() {
        //Connection cTemp = null;
        try {
            Connection cTemp = getConnectionNoDB();
            Statement stat = cTemp.createStatement();
            /*
            Se utiliza PreparedStatement para introducir datos (mayor protección contra SQL Injections,
            pero para crear tablas o BBDD se suele poner Statement.
             */

            //Nos conectamos al servidor y le decimos que cree la bbdd si no existe:
            stat.executeUpdate("CREATE DATABASE IF NOT EXISTS " + nombre_BBDD);
            System.out.println("Base de datos " + nombre_BBDD + " creada.");

            stat.close();
            cTemp.close();

            getConnectionSingleton();
            stat = con.createStatement();
            //stat.executeUpdate("USE " + nombre_BBDD);
            crearTablas(stat);

            triggerMinParticipantes(stat);
            
            //queries();

            stat.close();
            //con.close();
            return true;

        } catch (SQLException ex) {
            //ex.printStackTrace();
            System.out.println("Error al crear la base de datos. No se crearon las tablas en crearBBDD");
            return false;
        }
    }

    private static void crearTablas(Statement stat) throws SQLException { //la excepcion la va a tirar crear BBDD

        stat.executeUpdate(//vehiculo
                "CREATE TABLE IF NOT EXISTS vehiculo ("
                + "codigo INT NOT NULL PRIMARY KEY, "
                + "tipo VARCHAR(20) NOT NULL, "
                + "velocidad INT NOT NULL, "
                + "manejo INT NOT NULL, "
                + "puntos INT DEFAULT 0, "
                + "puntosHistoricos INT DEFAULT 0"
                + ")"
        );
        System.out.println("Tabla Vehiculo disponible.");

        stat.executeUpdate(//Torneo
                "CREATE TABLE IF NOT EXISTS torneo ("
                + "codigo INT AUTO_INCREMENT PRIMARY KEY, "
                + "nombre VARCHAR(50) NOT NULL, "
                + "fecha DATE DEFAULT (CURRENT_DATE), "
                + "plazas INT NOT NULL, "
                + "inscripcion VARCHAR(20) DEFAULT 'abierto' CHECK (inscripcion IN ('abierto', 'cerrado'))"
                + ")"
        );
        //hacer import del sql en el java de la fecha!!!!!!!!!!!
        System.out.println("Tabla Torneo disponible.");

        stat.executeUpdate(//Circuito
                "CREATE TABLE IF NOT EXISTS circuito ("
                + "codigo INT AUTO_INCREMENT PRIMARY KEY, "
                + "cod_torneo INT NOT NULL, "
                + "curva INT NOT NULL, "
                + "recta INT NOT NULL, "
                + "FOREIGN KEY (cod_torneo) REFERENCES torneo(codigo) "
                + "ON DELETE CASCADE "
                + "ON UPDATE CASCADE"
                + ")"
        );
        System.out.println("Tabla Circuito disponible.");

        stat.executeUpdate(//Participa
                "CREATE TABLE IF NOT EXISTS participa ("
                + "codigo INT AUTO_INCREMENT PRIMARY KEY, "
                + "cod_vehiculo INT NOT NULL, "
                + "cod_torneo INT NOT NULL, "
                + "posicion INT NOT NULL, "
                + "puntos INT NOT NULL, "
                + "FOREIGN KEY (cod_vehiculo) REFERENCES vehiculo(codigo) "
                + "ON DELETE CASCADE "
                + "ON UPDATE CASCADE, "
                + "FOREIGN KEY (cod_torneo) REFERENCES torneo(codigo) "
                + "ON DELETE CASCADE "
                + "ON UPDATE CASCADE"
                + ")"
        );
        /*+ "UNIQUE KEY participacion (cod_vehiculo, cod_torneo)"*/
        System.out.println("Tabla Participa disponible.");

        //Trofeo
        stat.executeUpdate(
                "CREATE TABLE IF NOT EXISTS trofeo ("
                + "codigo INT AUTO_INCREMENT PRIMARY KEY, "
                + "idTorneo INT NOT NULL, "
                + "tipo VARCHAR(20) DEFAULT 'ORO', "
                + "modelo VARCHAR(20) DEFAULT 'FIGURA', "
                + "FOREIGN KEY (idTorneo) REFERENCES torneo(codigo) "
                + "ON DELETE CASCADE "
                + "ON UPDATE CASCADE"
                + ")"
        );
        /*+ "UNIQUE KEY participacion (cod_vehiculo, cod_torneo)"*/
        System.out.println("Tabla Trofeo disponible.");

        //Oro
        stat.executeUpdate(
                "CREATE TABLE IF NOT EXISTS oro ("
                + "codigo INT AUTO_INCREMENT PRIMARY KEY, "
                + "idTrofeo INT NOT NULL, "
                + "manejo INT DEFAULT 2, "
                + "velocidad INT DEFAULT 2, "
                + "FOREIGN KEY (idTrofeo) REFERENCES trofeo(codigo) "
                + "ON DELETE CASCADE "
                + "ON UPDATE CASCADE"
                + ")"
        );
        System.out.println("Tabla Oro disponible.");

        //Plata
        stat.executeUpdate(
                "CREATE TABLE IF NOT EXISTS plata ("
                + "codigo INT AUTO_INCREMENT PRIMARY KEY, "
                + "idTrofeo INT NOT NULL, "
                + "manejo INT DEFAULT 2, "
                + "velocidad INT DEFAULT 2, "
                + "FOREIGN KEY (idTrofeo) REFERENCES trofeo(codigo) "
                + "ON DELETE CASCADE "
                + "ON UPDATE CASCADE"
                + ")"
        );
        System.out.println("Tabla Plata disponible.");

        //Bronce
        stat.executeUpdate(
                "CREATE TABLE IF NOT EXISTS bronce ("
                + "codigo INT AUTO_INCREMENT PRIMARY KEY, "
                + "idTrofeo INT NOT NULL, "
                + "manejo INT DEFAULT 1, "
                + "velocidad INT DEFAULT 1, "
                + "FOREIGN KEY (idTrofeo) REFERENCES trofeo(codigo) "
                + "ON DELETE CASCADE "
                + "ON UPDATE CASCADE"
                + ")"
        );
        System.out.println("Tabla Bronce disponible.");
        /*+ "UNIQUE KEY participacion (cod_vehiculo, cod_torneo)"*/

    }
    //con.close();

    private static void triggerMinParticipantes(Statement stat) {
        try {
            stat.executeUpdate("DROP TRIGGER IF EXISTS nPlazasMin");

            stat.executeUpdate(
                    "CREATE TRIGGER nPlazasMin "
                    + "BEFORE INSERT ON torneo "
                    + "FOR EACH ROW "
                    + "BEGIN "
                    + "  IF NEW.plazas < 3 THEN "
                    + "    SIGNAL SQLSTATE '45000' "
                    + "    SET MESSAGE_TEXT = 'Debe haber al menos 3 plazas en el torneo.'; "
                    + "  END IF; "
                    + "END"
            );
            System.out.println("Se ha creado el TRIGGER nPlazasMin.");
        } catch (SQLException e) {
            System.out.println("Error al crear el TRIGGER nPlazasMin.");
        }
    }
    
    private static void queries() {
        Connection cn = getConnectionSingleton();
        
        try {
            // Verificar si ya hay datos
            Statement checkStat = con.createStatement();
            var rs = checkStat.executeQuery("SELECT COUNT(*) as count FROM torneo");
            rs.next();
            if (rs.getInt("count") > 0) {
                System.out.println("Ya existen datos en las tablas.");
                checkStat.close();
                return;
            }
            checkStat.close();

            // Insertar un torneo de ejemplo
            String queryTorneo = "INSERT INTO torneo (nombre, fecha, plazas, inscripcion) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(queryTorneo, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, "Torneo Inicial");
                ps.setString(2, "2025-01-15");
                ps.setInt(3, 5);
                ps.setString(4, "abierto");
                ps.executeUpdate();
                
                // Obtener el ID del torneo insertado
                var rsKeys = ps.getGeneratedKeys();
                if (rsKeys.next()) {
                    int idTorneo = rsKeys.getInt(1);
                    System.out.println("Torneo inicial creado con ID: " + idTorneo);
                    
                    // Insertar trofeos para ese torneo
                    insertarTrofeosIniciales(con, idTorneo);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar datos iniciales: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Inserta los tres tipos de trofeos para un torneo
     */
    private static void insertarTrofeosIniciales(Connection con, int idTorneo) throws SQLException {
        String queryTrofeo = "INSERT INTO trofeo (idTorneo, tipo, modelo) VALUES (?, ?, ?)";
        
        // Trofeo ORO
        try (PreparedStatement ps = con.prepareStatement(queryTrofeo, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idTorneo);
            ps.setString(2, "ORO");
            ps.setString(3, "FIGURA");
            ps.executeUpdate();
            
            var rsKeys = ps.getGeneratedKeys();
            if (rsKeys.next()) {
                int idTrofeoOro = rsKeys.getInt(1);
                insertarBonusOro(con, idTrofeoOro);
                System.out.println("Trofeo ORO creado");
            }
        }
        
        // Trofeo PLATA
        try (PreparedStatement ps = con.prepareStatement(queryTrofeo, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idTorneo);
            ps.setString(2, "PLATA");
            ps.setString(3, "FIGURA");
            ps.executeUpdate();
            
            var rsKeys = ps.getGeneratedKeys();
            if (rsKeys.next()) {
                int idTrofeoPlata = rsKeys.getInt(1);
                insertarBonusPlata(con, idTrofeoPlata);
                System.out.println("Trofeo PLATA creado");
            }
        }
        
        // Trofeo BRONCE
        try (PreparedStatement ps = con.prepareStatement(queryTrofeo, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idTorneo);
            ps.setString(2, "BRONCE");
            ps.setString(3, "FIGURA");
            ps.executeUpdate();
            
            var rsKeys = ps.getGeneratedKeys();
            if (rsKeys.next()) {
                int idTrofeoBronce = rsKeys.getInt(1);
                insertarBonusBronce(con, idTrofeoBronce);
                System.out.println("Trofeo BRONCE creado");
            }
        }
    }

    private static void insertarBonusOro(Connection con, int idTrofeo) throws SQLException {
        String query = "INSERT INTO oro (idTrofeo, manejo, velocidad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idTrofeo);
            ps.setInt(2, 2); // bonus manejo
            ps.setInt(3, 2); // bonus velocidad
            ps.executeUpdate();
        }
    }

    private static void insertarBonusPlata(Connection con, int idTrofeo) throws SQLException {
        String query = "INSERT INTO plata (idTrofeo, manejo, velocidad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idTrofeo);
            ps.setInt(2, 2); // bonus manejo
            ps.setInt(3, 2); // bonus velocidad
            ps.executeUpdate();
        }
    }

    private static void insertarBonusBronce(Connection con, int idTrofeo) throws SQLException {
        String query = "INSERT INTO bronce (idTrofeo, manejo, velocidad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, idTrofeo);
            ps.setInt(2, 1); // bonus manejo
            ps.setInt(3, 1); // bonus velocidad
            ps.executeUpdate();
        }
    }
    

//    public Connection getCon() {
//        return con;
//    }
}
