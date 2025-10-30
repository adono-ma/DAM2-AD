/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IO.DAO.CSV;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author ixchel
 */
public class FConfig {
    private static final File f = new File("src/IO/Ficheros/FConfig.txt");

    public static int cargarCodigo() {
        int codigo = 0;
        if (f.exists()) {
            System.out.println("Se ha encontrado el archivo de codigos: " + f.getAbsolutePath());
        }
        try (Scanner sc = new Scanner(f)){
            codigo = Integer.parseInt(sc.nextLine());
        } catch (FileNotFoundException ex) {
            //ex.printStackTrace();
            //System.getLogger(FConfig.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return codigo;
    }

    public static boolean guardarCodigo(int codigo) {
        try (FileWriter fw = new FileWriter(f, false)) {
            fw.write(String.valueOf(codigo));  // convertir el int a String
            fw.flush(); // opcional, el close ya lo hace
            System.out.println("Codigo guardado correctamente en " + f);
        } catch (IOException e) {
            //e.printStackTrace();
        }

        return false;
    }
    
    public static int codigoInc(){//todo en uno
        int cod = cargarCodigo();
        int nuevo = cod + 1;
        guardarCodigo(nuevo);
        return nuevo;
    }
} 
