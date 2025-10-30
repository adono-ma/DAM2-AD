/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package IO.DAO;

import Modelo.Torneo;
import Modelo.Vehiculo;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 *
 * @author ixche
 */
public interface CircuitoDAO {
    
    public boolean guardarResultados(Torneo t, int id, LinkedHashMap<Vehiculo, Integer> resultados);
    /*
        Guardar los resultados de un circuito en un fichero.
        - torneo: nombre del torneo
        - circuito: nombre/código del circuito
        - resultados: Vehiculo -> Rendimiento (ordenados)
     */
}
