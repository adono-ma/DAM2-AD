/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IO.DAO.CSV;

import Modelo.Vehiculo;
import java.util.ArrayList;
import IO.DAO.VehiculoDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author ixchel
 */
public class DAOCSVVehiculo implements VehiculoDAO {

    private File f = null;

    public DAOCSVVehiculo() {
        f = new File("src/IO/Ficheros/FicheroVehiculos.csv");
    }

    @Override
    public boolean create(Vehiculo v) {
        try (FileWriter fw = new FileWriter(f, true)) {//se abre  en modo append. por defecto es false, pero al hacerlo en true, no sobreescribe, sino que escribe a continuación de lo que había          
            fw.write(v.vehiculoToCsv(v));
            fw.write("\n");
            fw.flush();//para vaciar el buffer (en nextline no hace falta, pero por si acaso hay que hacerlo).es como hacer un sc.nextLine() vacío, que quitaba lo que hubiese en buffer.
        } catch (IOException ex) {
            System.out.println("Error al encontrar el fichero.");
        }
        return false;
    }

    @Override
    public boolean delete(Vehiculo v) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Vehiculo read(int pk) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Vehiculo> readAll() {
        ArrayList<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
        String intro;
        String[] vals;
        try (Scanner sc = new Scanner(f)) {//abre el fichero
            //sc.useDelimiter(",");//para poner como separador, en lugar del espacio, lo que queramos, también sirve para expresiones regulares
            intro = sc.nextLine(); //se salta el header
            while (sc.hasNext() == true) {
                intro = sc.nextLine();
                vals = intro.split(";");
                vehiculos.add(csvToVehiculo(vals));
            }
        } catch (FileNotFoundException ex) {//(IOException ex) también entraría porque está un nivel por encima, por lo que abarca más que el FileNotFound
            System.out.println("Error al encontrar el fichero.");
            ex.printStackTrace();
        }
        return vehiculos;
    }

    @Override
    public boolean update(Vehiculo v) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Vehiculo csvToVehiculo(String[] atr) {
        return new Vehiculo(Integer.parseInt(atr[0]), atr[1], Integer.parseInt(atr[2]), Integer.parseInt(atr[3]), Integer.parseInt(atr[4]));

    }

}
