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
 * @author DAM2
 */
public class Config2 {
    private static final File f = new File("src/IO/Ficheros/FConfig.txt");

    public static int leerCodigo() {
        int codigo = 0;
        if (!f.exists()) {
            System.out.println("No se ha encontrado el archivo de codigos. Generando...");
            return codigo;
        }
        try (Scanner sc = new Scanner(f)){
            codigo = Integer.parseInt(sc.nextLine());
            System.out.println(codigo);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            //System.getLogger(FConfig.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return codigo;
    }

    public static int escribirCodigo(int codigo) {
        try (FileWriter fw = new FileWriter(f, false)) {
            fw.write(String.valueOf(codigo));  // convertir el int a String
            fw.flush(); // opcional, el close ya lo hace
            System.out.println("Codigo guardado correctamente en " + f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
