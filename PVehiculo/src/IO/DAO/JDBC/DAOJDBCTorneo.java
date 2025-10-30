/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IO.DAO.JDBC;

import IO.DAO.BBDD;
import IO.DAO.TorneoDAO;
import Modelo.Circuito;
import Modelo.Torneo;
import Modelo.Trofeo;
import Modelo.Vehiculo;
import Utils.Utils;
import static Utils.Utils.stringToBoolean;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

//import java.util.Collections;
/**
 *
 * @author ixchel
 */
public class DAOJDBCTorneo implements TorneoDAO {

    private Connection con = null;

    public DAOJDBCTorneo() {
        this.con = BBDD.getConnectionSingleton();
    }

    @Override
    public boolean create(Torneo t) throws SQLException {
        String query = "INSERT INTO torneo (nombre, fecha, plazas, inscripcion) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, t.getNombre());
            ps.setObject(2, java.sql.Date.valueOf(t.getFecha()));
            ps.setInt(3, t.getnPlazas());
            if (t.getnPlazas() > t.getV_participantes().size()) {
                ps.setBoolean(4, true);
            } else {
                ps.setBoolean(4, false);
            }
            ps.setString(4, Utils.booleanToString(t.isInscripcion()));
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
    public boolean delete(int pk) throws SQLException {
        String query = "DELETE FROM torneo WHERE codigo = ?";

        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, pk);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Torneo read(int pk) throws SQLException {
        String query = "SELECT * FROM torneo WHERE codigo = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, pk);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                String inscripcionStr = rs.getString("inscripcion");
                boolean inscripcion = stringToBoolean(inscripcionStr);
                int nPlazas = rs.getInt("plazas");

                Torneo t = new Torneo(codigo, nombre, fecha, nPlazas, inscripcion);

                return t;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error de busqueda.");
        }
        return null;
        /*
        select asterisco from usuarios
        el result-set se itera con el while .next funciona como los cursores,
        es un fetch, como solo hay 1 resultado,
        se one resultset.next directamente y a tirar.
        con el usr.get pues ya te saca el id que sea.
         */
    }

    @Override
    public ArrayList<Torneo> readAll() {
        ArrayList<Torneo> ts = new ArrayList<>();
        String query = "SELECT * FROM torneo";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                int nPlazas = rs.getInt("plazas");
                String inscripcionStr = rs.getString("inscripcion");
                boolean inscripcion = stringToBoolean(inscripcionStr);

                Torneo t = new Torneo(codigo, nombre, fecha, nPlazas, inscripcion);
                ts.add(t);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error de lista.");
        }
        return ts;
        /*
        el listado lo mete en un arraylist
         */
    }

    @Override
    public boolean update(Torneo t) throws SQLException {
        String query = "UPDATE torneo SET nombre = ?, fecha = ?, plazas = ?, inscripcion = ? WHERE codigo = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, t.getNombre());
            ps.setObject(2, t.getFecha());
            ps.setInt(3, t.getnPlazas());
            ps.setString(4, Utils.booleanToString(t.isInscripcion()));
            ps.setInt(5, t.getCodigo());

            ps.executeUpdate();
            ps.close();

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al introducir los datos.");
            return false;
        }
    }

//    @Override
//    public boolean guardarParticipacion(int codV, int codT, int posicion, int puntos) throws SQLException {
//        String query = "INSERT INTO participa (cod_vehiculo, cod_torneo, posicion, puntos) VALUES (?, ?, ?, ?)";
//        try (PreparedStatement ps = con.prepareStatement(query)) {
//            ps.setInt(1, codV);
//            ps.setInt(2, codT);
//            ps.setInt(3, posicion);
//            ps.setInt(4, puntos);
//
//            ps.executeUpdate();
//            ps.close();
//
//            return true;
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            System.out.println("Error al introducir los datos.");
//            return false;
//        }
//    }
    @Override
    public boolean guardarResultadosTorneo(Torneo t) throws SQLException {
        Connection conn = null;
        PreparedStatement psTorneo = null;
        PreparedStatement psVehiculo = null;
        PreparedStatement psParticipa = null;
        PreparedStatement psCheckVehiculo = null;
        PreparedStatement psUpdateVehiculo = null;

        try {
            conn = BBDD.getConnectionSingleton();
            conn.setAutoCommit(false); // Iniciar transacción

            // 1. Guardar o actualizar el torneo
            if (t.getCodigo() <= 0) {
                // Si el torneo no existe, crearlo
                String sqlTorneo = "INSERT INTO torneo (nombre, fecha, plazas, inscripcion) VALUES (?, ?, ?, ?)";
                psTorneo = conn.prepareStatement(sqlTorneo, Statement.RETURN_GENERATED_KEYS);
                psTorneo.setString(1, t.getNombre());
                psTorneo.setObject(2, t.getFecha());
                psTorneo.setInt(3, t.getnPlazas());
                psTorneo.setString(4, Utils.booleanToString(t.isInscripcion()));
                psTorneo.executeUpdate();

                ResultSet rs = psTorneo.getGeneratedKeys();
                if (rs.next()) {
                    t.setCodigo(rs.getInt(1));
                    System.out.println("Torneo guardado en BD con código: " + t.getCodigo());
                }
                rs.close();
            }

            // 2. Guardar o actualizar cada vehículo participante
            String sqlCheckVehiculo = "SELECT codigo FROM vehiculo WHERE codigo = ?";
            String sqlInsertVehiculo = "INSERT INTO vehiculo (codigo, tipo, velocidad, manejo, puntos, puntosHistoricos) VALUES (?, ?, ?, ?, ?, ?) " +
                                       "ON DUPLICATE KEY UPDATE puntos = ?, puntosHistoricos = puntosHistoricos + ?";
            
            psCheckVehiculo = conn.prepareStatement(sqlCheckVehiculo);
            psVehiculo = conn.prepareStatement(sqlInsertVehiculo);

            for (Vehiculo v : t.getV_participantes()) {
                // Verificar si el vehículo existe
                psCheckVehiculo.setInt(1, v.getCodigo());
                ResultSet rsCheck = psCheckVehiculo.executeQuery();
                
                if (!rsCheck.next()) {
                    // El vehículo no existe, insertarlo
                    psVehiculo.setInt(1, v.getCodigo());
                    psVehiculo.setString(2, String.valueOf(v.getTipo()));
                    psVehiculo.setInt(3, v.getVelocidad());
                    psVehiculo.setInt(4, v.getManejo());
                    psVehiculo.setInt(5, v.getPuntos());
                    psVehiculo.setInt(6, v.getPuntos()); // puntosHistoricos
                    psVehiculo.setInt(7, v.getPuntos()); // para el UPDATE (ON DUPLICATE KEY)
                    psVehiculo.setInt(8, v.getPuntos()); // para sumar a puntosHistoricos
                    psVehiculo.executeUpdate();
                    System.out.println("Vehículo " + v.getCodigo() + " insertado en BD");
                } else {
                    // El vehículo existe, actualizarlo
                    String sqlUpdate = "UPDATE vehiculo SET puntos = ?, puntosHistoricos = puntosHistoricos + ? WHERE codigo = ?";
                    psUpdateVehiculo = conn.prepareStatement(sqlUpdate);
                    psUpdateVehiculo.setInt(1, v.getPuntos());
                    psUpdateVehiculo.setInt(2, v.getPuntos());
                    psUpdateVehiculo.setInt(3, v.getCodigo());
                    psUpdateVehiculo.executeUpdate();
                    System.out.println("Vehículo " + v.getCodigo() + " actualizado en BD");
                }
                rsCheck.close();
            }

            // 3. Guardar las participaciones
            String sqlParticipa = "INSERT INTO participa (cod_vehiculo, cod_torneo, posicion, puntos) VALUES (?, ?, ?, ?)";
            psParticipa = conn.prepareStatement(sqlParticipa);

            for (int i = 0; i < t.getV_participantes().size(); i++) {
                Vehiculo v = t.getV_participantes().get(i);
                psParticipa.setInt(1, v.getCodigo());
                psParticipa.setInt(2, t.getCodigo());
                psParticipa.setInt(3, i + 1); // posición
                psParticipa.setInt(4, v.getPuntos());
                psParticipa.addBatch();
            }
            
            psParticipa.executeBatch();
            System.out.println("Participaciones guardadas en BD");

            // Commit de la transacción
            conn.commit();
            System.out.println("Resultados del torneo guardados.");
            return true;

        } catch (SQLException ex) {
            System.out.println("Error al guardar resultados del torneo: " + ex.getMessage());
            ex.printStackTrace();
            
            // Hacer rollback en caso de error
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Rollback realizado");
                } catch (SQLException e) {
                    System.out.println("Error al hacer rollback: " + e.getMessage());
                }
            }
            return false;
            
        } finally {
            // Restaurar autocommit y cerrar recursos
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
                if (psTorneo != null) psTorneo.close();
                if (psVehiculo != null) psVehiculo.close();
                if (psParticipa != null) psParticipa.close();
                if (psCheckVehiculo != null) psCheckVehiculo.close();
                if (psUpdateVehiculo != null) psUpdateVehiculo.close();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public Circuito conseguirCircuitos(int codT) {
        ArrayList<Circuito> cs = new ArrayList<>();
        String sql = "SELECT codigo, curvas, rectas FROM circuito WHERE cod_torneo=?";
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = BBDD.getConnectionSingleton().prepareStatement(sql);
            ps.setInt(1, codT);
            rs = ps.executeQuery();

            while (rs.next()) {
                Circuito c = new Circuito(rs.getInt("codigo"), rs.getInt("curvas"), rs.getInt("rectas"));
                cs.add(c);
            }
            for (int i = 0; i < cs.size(); i++) {
                System.out.println((i + 1) + " " + cs.get(i).toString());
            }

            Scanner aux = new Scanner(System.in);
            while (true) {
                System.out.println("introduzca el numero del circuitos para el torneo:");
                int op = aux.nextInt() - 1;
                if (op < cs.size()) {
                    return cs.get(op);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error");
        }
        return null;
    }

    public void crearCircuitos(int codT) {
        Circuito c = new Circuito();
        ArrayList<Circuito> cs = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            c.generarCircuito(codT);
            cs.add(c);
        }
        String sql = "INSERT INTO circuito (codigo,cod_torneo,curva,recta) VALUES(?,?,?,?)";
        PreparedStatement ps;
        try {
            for (Circuito circuito : cs) {
                ps = BBDD.getConnectionSingleton().prepareStatement(sql);
                ps.setInt(1, codT);
                ps.setInt(2, circuito.getCodT());
                ps.setInt(3, circuito.getCurva());
                ps.setInt(4, circuito.getRecta());
                ps.execute();
                ps.close();
            }

        } catch (SQLException ex) {
            System.out.println("Error al insertar circuito en BBDD.");
        }
    }

    public Trofeo[] consegirTrofeo(int idTorneo) {
        Trofeo[] trofeos = new Trofeo[3];
        String sql = "SELECT * FROM trofeo WHERE idTorneo = ?";
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = BBDD.getConnectionSingleton().prepareStatement(sql);
            ps.setInt(1, idTorneo);
            rs = ps.executeQuery();

            int i = 0;
            while (rs.next() && i < 3) {
                int id = rs.getInt("id");
                int torneoId = rs.getInt("idTorneo");
                String tipoS = rs.getString("tipo");
                String modeloS = rs.getString("modelo");
                Trofeo.TipoTrofeo t = Trofeo.stringToTipoT(tipoS);
                Trofeo.ModeloTrofeo m = Trofeo.stringToModeloT(modeloS);
                trofeos[i] = Trofeo.recibirTrofeo(id, torneoId, t, m);
                i++;
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener los trofeos del torneo.");
        }
        return null;
    }

    public void guardarTrofeo(Trofeo t) {
        String sql = "INSERT INTO trofeo(id,idTorneo,tipo,modelo) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = BBDD.getConnectionSingleton().prepareStatement(sql)){
            ps.setInt(1, t.getIdTorneo());
            ps.setString(2, t.getTipo().toString());
            ps.setString(3, t.getModelo().toString());
            ps.execute();
        } catch (SQLException ex) {
            System.out.println("Error al insertar trofeo en torneo.");
        }
    }

//    public static Trofeo adjudicarTrofeo(int id, int idTorneo, String tipo, String modelo) {
//        Trofeo t = new Trofeo();
//        tipo = t.getTipo().toString();
//        modelo = t.getModelo().toString();
//        return new Trofeo(id, idTorneo, modelo, tipo);
//    }
    /*
    public Trofeos<> trofeosTorneo (Torneo t) {
        al torneo le entra el id del torneo (va a haber 3)
    Trofeo[]
    for(trofeos.size
        trofeos.getTipo();
    }
    
    ResultSet rs = null;
        try {

            //insertar trofeo padre oro
            String insertTrofeoPadre = """
                               INSERT INTO trofeo (modelo, tipo, id_torneo) VALUES (?,?,?);
                               """;
            pstmPadre = con.prepareCall(insertTrofeoPadre);// .prepareStatement() ????

            pstmPadre.setString(1, "copa");
            pstmPadre.setString(2, "oro");
            pstmPadre.setInt(3, id_torneo);

            pstmPadre.executeUpdate();//hace el insert

            rs = pstmPadre.getGeneratedKeys();// esto nos da el idTorneo, que no lo teníamos
            rs.next();

            int id_oro = rs.getInt(1);

            rs.close();
    
    
     */

//    public boolean guardarResultadosTorneo2(Torneo t) throws SQLException {
//        String sql = "INSERT INTO participa (cod_vehiculo, cod_torneo, posicion, puntos) VALUES (?, ?, ?, ?)";
//        for (int i = 0; i < t.getV_participantes().size(); i++) {
//            Vehiculo v = t.getV_participantes().get(i);
//        }
//        return true;
//    }
}
