/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.util.Scanner;

/**
 *
 * @author ixchel
 */
public class Utils {
    public static int randInt(int limiteInferior, int limiteSuperior) {
        int min = limiteInferior;
        int max = limiteSuperior;

        int resultado = (int) (Math.random() * (max - min + 1) + min);

        return resultado;
    }
    //Métodos para el check de inscripciones (abiertp/cerrado)
    public static boolean stringToBoolean (String s) {
        if ("abierto".equals(s)) {
            return true;
        } else {
            return false;
        }
    }
    
    public static String booleanToString (boolean b) {
        if (b) {
            return "abierto";
        } else {
            return "cerrado";
        }
    }
    
    public static int entradasMenu (int n) {
        Scanner sc = new Scanner(System.in);
        int opc = 0;
        boolean entrada = true;
        do {
            try {
                opc = Integer.parseInt(sc.nextLine());
                if (opc >= 0 && opc <= n) {
                    entrada = false;
                } else {
                    System.out.println("Por favor, inserte un numero entre 0 y " + n + ".");
                }
            } catch (NumberFormatException ne){
                System.out.println("Debe introducirse un valor numerico.");
            }
        } while (entrada);
        return opc;
    }

    
}
