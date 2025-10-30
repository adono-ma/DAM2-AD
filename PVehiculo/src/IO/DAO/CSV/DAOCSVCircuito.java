/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IO.DAO.CSV;

import IO.DAO.CircuitoDAO;
import Modelo.Torneo;
import Modelo.Vehiculo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author DAM2
 */
public class DAOCSVCircuito implements CircuitoDAO{
    //private File f = null;

//    public static void crearFichero(Torneo t, int id, ArrayList<Vehiculo> vehiculos) {
//        
//        String ruta_directorio = "src/IO/Ficheros/";
//        File f = new File(ruta_directorio);
//        LocalDateTime fecha = t.getHora();
//        
//        
//        String nombreFile = fecha + "_" + id + ".txt";
//        File f_path = new File(f.getAbsolutePath() + "/" + nombreFile);
//
//        try {
//            if (f_path.createNewFile()) {
//                System.out.println("Fichero creado: " + ruta_directorio + nombreFile);
//            } else {
//                System.out.println("No se ha podido crear el fichero.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
    private static void cabecera(File archivo, Torneo t, int id) {
        try (FileWriter fw = new FileWriter(archivo, false)) {//si se pone true, se abre  en modo append. por defecto es false, pero al hacerlo en true, no sobreescribe, sino que escribe a continuación de lo que había          
            DateTimeFormatter fMod = DateTimeFormatter.ofPattern("dd/MM/yyyy");//al dejar solo LocalDateTime peta
            String fecha = t.getFecha().format(fMod);

            String header = "Resultados de la carrera " + id + " del " + t.getNombre() + " celebrado el día " + fecha + "\n";
            fw.write(header);

            fw.write("\n");

            //fw.write("Posicion,Codigo,Tipo,Velocidad,Manejo,Puntos\n");
            fw.write("Posicion,Codigo,Tipo,Rendimiento\n");
            
            fw.write("\n");
            
            fw.flush();//para vaciar el buffer (en nextline no hace falta, pero por si acaso hay que hacerlo).es como hacer un sc.nextLine() vacío, que quitaba lo que hubiese en buffer.

        } catch (IOException ex) {
            System.out.println("Error al encontrar el fichero.");
        }
    }
    
    @Override
    public boolean guardarResultados(Torneo t, int id, LinkedHashMap<Vehiculo, Integer> resultados) {

        String ruta_directorio = "src/IO/Ficheros/";
        File dir = new File(ruta_directorio);
        
        DateTimeFormatter fMod = DateTimeFormatter.ofPattern("dd-MM-yyyy");//al dejar solo LocalDateTime peta
        String fecha = t.getFecha().format(fMod);

        if (!dir.exists()) {
            dir.mkdirs();//te crea toda la ruta si no existía
        }

        String nombreFile = fecha + "_" + id + ".txt";
        File f = new File(ruta_directorio + nombreFile);
        
        try {
            if (!f.exists()) {
                f.createNewFile();
                System.out.println("Fichero creado en: " + f.getAbsolutePath());
                cabecera(f, t, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al crear el fichero: " + f.getAbsolutePath());
        }

        try (FileWriter fw = new FileWriter(f, true)) {//se abre  en modo append. por defecto es false, pero al hacerlo en true, no sobreescribe, sino que escribe a continuación de lo que había          

            //Lo hemos puesto en el header:
//                String header = "Resultados de la carrera " + id + "del Torneo " + t.getNombre() + "del día " + t.getHora();
//                fw.write(header);
//                //si quieremos que esto solo lo escriba una vez, lo ponemos en un método a parte.
//
//                fw.write("\n");
//
//                fw.write("Posicion,Codigo,Tipo,Velocidad,Manejo,Puntos\n");
            int posicion = 1;
            for (Map.Entry<Vehiculo, Integer> datos : resultados.entrySet()) {
                Vehiculo v = datos.getKey();
                //String lineaIntro = (i + 1) + "," + v.getTipo() + "," + v.vehiculoToCsv(v);
                Vehiculo vehiculo = datos.getKey();
                Integer rendimiento = datos.getValue();
                
                String lineaIntro = posicion + ".- " + vehiculo.getCodigo() + "," + vehiculo.getTipo() + "," + rendimiento;
                fw.write(lineaIntro);
                fw.write("\n");
                posicion++;
            }

            //fw.write("\n"); 
            fw.flush();//para vaciar el buffer (en nextline no hace falta, pero por si acaso hay que hacerlo).es como hacer un sc.nextLine() vacío, que quitaba lo que hubiese en buffer.

            return true;
        } catch (IOException ex) {
            System.out.println("Error al encontrar el fichero: " + f.getAbsolutePath());
            ex.printStackTrace();
            return false;

        }
    }
}
