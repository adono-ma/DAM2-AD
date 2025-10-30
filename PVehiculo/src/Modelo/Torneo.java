/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Beans/Bean.java to edit this template
 */
package Modelo;

import IO.DAO.JDBC.DAOJDBCTorneo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.SQLException;
import Modelo.Trofeo;
import java.util.Scanner;

/**
 *
 * @author ixchel
 */
public class Torneo {

    private String nombre;
    private Circuito[] circuitos;
    private LocalDate fecha;
    //private enum Num_plazas{};
    private int nPlazas;
    private ArrayList<Vehiculo> v_participantes = new ArrayList<Vehiculo>();
    private int codigo;
    private boolean inscripcion;
    private ArrayList<Trofeo> arr_trofeos = new ArrayList<Trofeo>();

    public Torneo(LocalDate hora, String nombre, int plazas) {
        this.fecha = LocalDate.now();
        this.nombre = nombre;
        this.nPlazas = plazas;
        //this.codigo = -1; //el torneo no va a tener código hasta que empiece a guardarse en la BD
        //this.inscripcion = false;

        iniciarCircuitosHC();
    }

    public Torneo(String nombre, Circuito[] circuitos, LocalDate hora, int nPlazas, int codigo, boolean inscripcion) {
        this.nombre = nombre;
        this.circuitos = circuitos;
        this.fecha = hora;
        this.nPlazas = nPlazas;
        this.codigo = codigo;
        this.inscripcion = inscripcion;
        iniciarCircuitosHC();
    }

    /**
     *
     * @param codigo
     * @param nombre
     * @param hora
     * @param nPlazas
     * @param inscripcion
     */
    public Torneo(int codigo, String nombre, LocalDate hora, int nPlazas, boolean inscripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fecha = hora;
        this.nPlazas = nPlazas;
        this.inscripcion = inscripcion;
        //iniciarCircuitosHC();
    }
    
    public Torneo(String nombre, int nPla) {
        this.nombre = nombre;
        this.nPlazas = nPla;
    }

    private void iniciarCircuitosHC() {
        // crear 2 circuitos fijos: 1 con muchas rectas, pocas curvas, y viceversa
        this.circuitos = new Circuito[2];
        this.circuitos[0] = new Circuito(1, 10, 3); // muchas rectas
        this.circuitos[1] = new Circuito(2, 3, 10); // muchas curvas
    }
    private void iniciarCircuitos() {
        Circuito circuitos = new Circuito();
        
        this.circuitos = new Circuito[2];
        this.circuitos[0] = new Circuito(1, 10, 3); // muchas rectas
        this.circuitos[1] = new Circuito(2, 3, 10); // muchas curvas
    }

    public boolean inscripcion(Vehiculo v) {
        //FALSE: vehiculo duplicado o más plazas de Num_plazas  
        if (v_participantes.contains(v)) {
            System.out.println("Este vehiculo ya esta inscrito en el torneo.");
            return false;
        }
        if (v_participantes.size() >= nPlazas) {
            System.out.println("No se ha podido inscribir el vehiculo en el torneo. No quedan plazas disponibles.");          
            return false;
        }

        v_participantes.add(v);
        System.out.println("Vehiculo " + v.getCodigo() + " inscrito correctamente en el torneo.");
        return true;
    }
    
    public static Torneo crearTorneo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca el nombre del torneo:");
        String nombre = sc.nextLine();
        System.out.println("Introduzca el numero de plazas:");
        int nPla = sc.nextInt();
        
        return new Torneo(nombre, nPla);
    }

    public void iniciarTorneo() {
        if (v_participantes.isEmpty()) {
            System.out.println("No hay vehiculos inscritos en el torneo.");
            return;
        }
        for (Circuito c : circuitos) {
            System.out.println("\nCircuito " + c.getCodigo());
            c.competir(v_participantes, this);
        }
        ranking();
        
        DAOJDBCTorneo tDAO = new DAOJDBCTorneo();
        
        try {
            tDAO.guardarResultadosTorneo(this);
        } catch (SQLException e) {
            System.out.println("Error al guardar datos del Torneo en tabla Participa.");
        }
        
        System.out.println("\nEl " + nombre + " ha finalizado!");
    }

    public void ranking() {
        if (v_participantes.isEmpty()) {
            System.out.println("No hay ranking que mostrar.");
            return;
        }

        Collections.sort(v_participantes);//recoge los vehículos inscritos
        Collections.reverse(v_participantes);//Le da la vuelta, para que salga primero el 1, y no el último que se ha inscrito.
        System.out.println("Ranking de vehiculos:");
        System.out.println("");

        for (int i = 0; i < v_participantes.size(); i++) {
            Vehiculo v = v_participantes.get(i);//Se coge el elemento de la posición
            System.out.println(i + 1 + ".- " + v);//se imprime el vehículo seleccionado
        }
    }
    
    public boolean torneoLleno() {
        return v_participantes.size() >= nPlazas;
    }
    
    private void asignarTrofeo() {
        
        if (v_participantes.size() < 3) {
            System.out.println("No se pueden otorgar trofeos. Participantes insuficientes.");
            return;
        }
        
        Vehiculo v1 = v_participantes.get(0);
        Trofeo oro = new Trofeo (1, this.codigo, Trofeo.TipoTrofeo.ORO, Trofeo.ModeloTrofeo.FIGURA);
        oro.otorgarTrofeo(v1);
        
        Vehiculo v2 = v_participantes.get(1);
        Trofeo plata = new Trofeo (2, this.codigo, Trofeo.TipoTrofeo.PLATA, Trofeo.ModeloTrofeo.FIGURA);
        plata.otorgarTrofeo(v2);
        
        Vehiculo v3 = v_participantes.get(2);
        Trofeo bronce = new Trofeo (3, this.codigo, Trofeo.TipoTrofeo.BRONCE, Trofeo.ModeloTrofeo.FIGURA);
        bronce.otorgarTrofeo(v3);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public boolean isInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(boolean inscripcion) {
        this.inscripcion = inscripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getnPlazas() {
        return nPlazas;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Circuito[] getCircuitos() {
        return circuitos;
    }

    public ArrayList<Vehiculo> getV_participantes() {
        return v_participantes;
    }

    /*
    void ranking
    inscribir
    iniciarTorneo() solo ejecuta:
    -Recorre todos los circuitos: llama al método competir, que saca el array de vehículos.
     */

    @Override
    public String toString() {
        return "Torneo{" + "nombre=" + nombre + ", circuitos=" + circuitos + ", fecha=" + fecha + ", nPlazas=" + nPlazas + ", vehiculos=" + v_participantes + ", codigo=" + codigo + ", inscripcion=" + inscripcion + '}';
    }
    public String torneoToCsv(Torneo t) {
        return t.getCodigo() + "," + t.getNombre() + "," + t.getFecha() + "," + t.getnPlazas() + "," + t.isInscripcion();
    }
}
