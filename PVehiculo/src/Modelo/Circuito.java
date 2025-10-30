/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import IO.DAO.BBDD;
import IO.DAO.CSV.DAOCSVCircuito;
import IO.DAO.CircuitoDAO;
import IO.DAO.JDBC.DAOJDBCTorneo;
import static Utils.Utils.randInt;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author ixchel
 */
public class Circuito {

    private int codigo;
    private int curva;
    private int recta;
    private final int[] PUNTOS_POSICION = {20, 15, 10, 5, 4, 3, 2, 1};
    private int codT;

    public Circuito(int codigo, int curva, int recta) {
        this.codigo = codigo;
        this.curva = curva;
        this.recta = recta;
    }
    public Circuito() {
        
    }
    
    public void generarCircuito(int codT) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Cuantos circuitos vas a usar");
        Circuito[] cs = new Circuito[sc.nextInt()];
        Circuito c = new Circuito();
        DAOJDBCTorneo daoT = new DAOJDBCTorneo();
        for (int i = 0; i < cs.length; i++) {
            cs[i] = daoT.conseguirCircuitos(codT);
        }
    }

    public void competir(ArrayList<Vehiculo> vehiculos, Torneo t) {
        //int aleatorio = randInt(1, 3);
        Map<Vehiculo, Integer> resul = new HashMap<>();
        LinkedHashMap<Vehiculo, Integer> rFinal = new LinkedHashMap<>();//??


        for (Vehiculo v : vehiculos) {
            int aleatorio = randInt(1, 3);
            int velocidad = v.coeficienteCompeticion()[0];
            int manejo = v.coeficienteCompeticion()[1];
            int rendimiento = aleatorio + (velocidad * recta) + (manejo * curva);
            resul.put(v, rendimiento); //mete en el Mapa final para que lo use PuntosFinales (o en el HashMap??)
        }

        // ordenar de mayor a menor
        ArrayList<Integer> listaOrdenada = new ArrayList<>(resul.values()); //coge de resultados el value (rend) y los mete en el arraylist
        Collections.sort(listaOrdenada);
        Collections.reverse(listaOrdenada);

        for (Integer rendimiento : listaOrdenada) {
            for (Map.Entry<Vehiculo, Integer> entry : resul.entrySet()) {
                Vehiculo v = entry.getKey();
                Integer valor = entry.getValue();

                if (rendimiento.equals(valor) && !rFinal.containsKey(v)) {
                    rFinal.put(v, valor);
                }
            }
        }

        //Imprimr resul
        System.out.println("Resultados del Circuito " + codigo + ":");
        for (Map.Entry<Vehiculo, Integer> entry : rFinal.entrySet()) {
            Vehiculo v = entry.getKey();
            int rendimiento = entry.getValue();
            System.out.println(v.getTipo() + " (" + v.getCodigo() + ") -- " + rendimiento);
        }
        listarPuntos(rFinal);
        
        ArrayList<Vehiculo> vsOrden = new ArrayList<>(rFinal.keySet());
        CircuitoDAO dao = new DAOCSVCircuito();
       
        dao.guardarResultados(t, this.codigo, rFinal);
    }
    
    
    private void listarPuntos(LinkedHashMap<Vehiculo, Integer> rFinal) {
        int n = rFinal.size(); // primer puesto = num más alto

        System.out.println("");
        for (Vehiculo v : rFinal.keySet()) {
            v.sumarPuntos(n);
            n--; // el siguiente puesto recibe menos puntos
        }
    }

    public int getCodigo() {
        return codigo;
    }

    public int getCurva() {
        return curva;
    }

    public int getRecta() {
        return recta;
    }

    public int[] getPUNTOS_POSICION() {
        return PUNTOS_POSICION;
    }

    public int getCodT() {
        return codT;
    }
    
    
    
}
