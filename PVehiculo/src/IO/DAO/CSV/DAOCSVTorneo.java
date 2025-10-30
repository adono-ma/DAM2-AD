/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IO.DAO.CSV;

import IO.DAO.TorneoDAO;
import Modelo.Torneo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ixchel
 */
public class DAOCSVTorneo implements TorneoDAO {

    private File f = null;
    private static final String separador = ",";

    public DAOCSVTorneo() {
        f = new File("src/IO/Ficheros/FicheroTorneo.csv");
        if (!f.exists()) {
            try (FileWriter fw = new FileWriter(f)) {
                fw.write("codigo,nombre,fecha,inscripcion,plazas\n");
            } catch (IOException e) {
                System.out.println("No se ha podido crear el archivo Torneo.");
            }
        }
    }

    @Override
    public boolean create(Torneo t) {
        try (FileWriter fw = new FileWriter(f, true)) {//se abre  en modo append. por defecto es false, pero al hacerlo en true, no sobreescribe, sino que escribe a continuación de lo que había          
            fw.write(t.torneoToCsv(t));
            fw.write("\n");
            fw.flush();//para vaciar el buffer (en nextline no hace falta, pero por si acaso hay que hacerlo).es como hacer un sc.nextLine() vacío, que quitaba lo que hubiese en buffer.

            return true;
        } catch (IOException ex) {
            System.out.println("Error al encontrar el fichero.");
            return false;
        }
    }

    @Override
    public Torneo read(int pk) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Torneo> readAll() {
        ArrayList<Torneo> lista = new ArrayList<>();

        try (Scanner sc = new Scanner(f)) {//abre el fichero
            //sc.useDelimiter(",");//para poner como separador, en lugar del espacio, lo que queramos, también sirve para expresiones regulares
            sc.nextLine(); //se salta el header
            while (sc.hasNext() == true) {
                String linea = sc.nextLine();
                String[] datos = linea.split(separador);
                Torneo t = csvToTorneo(datos);
                if (t != null) {
                    lista.add(t);
                }
            }
        } catch (FileNotFoundException ex) {//(IOException ex) también entraría porque está un nivel por encima, por lo que abarca más que el FileNotFound
            System.out.println("Error al encontrar el fichero de torneo.");
            ex.printStackTrace();
        }
        return lista;
    }

    @Override
    public boolean update(Torneo t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean delete(int pk) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

//    public Torneo csvToTorneo(String[] atr) {
//        try {
//            int codigo = Integer.parseInt(atr[0]);
//            String nombre = atr[1];
//            LocalDateTime fecha = LocalDateTime.parse(atr[2]);
//            boolean inscripcion = Boolean.parseBoolean(atr[3]);
//            int plazas = Integer.parseInt(atr[4]);
//
//            return new Torneo(codigo + "," + nombre + "," + fecha + "," + plazas + "," + inscripcion);
//        } catch (Exception e) {
//            System.out.println("Error al convertir CSV a Torneo");
//        }
//    }
    public Torneo csvToTorneo(String[] atr) {
        return new Torneo(Integer.parseInt(atr[0]), atr[1], LocalDate.parse(atr[2]), Integer.parseInt(atr[3]), Boolean.parseBoolean(atr[4]));
    }

    @Override
    public boolean guardarResultadosTorneo(Torneo t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
