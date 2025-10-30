/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author ixchel
 */
public class Trofeo {

    public enum TipoTrofeo {
        ORO, PLATA, BRONCE
    };

    public enum ModeloTrofeo {
        MEDALLA, COPA, PLATO, FIGURA
    };

    private int codigo;
    private TipoTrofeo tipo;
    private ModeloTrofeo modelo;
    private int idTorneo;
    private LocalDate fecha;
    private int contador = 0;

    private int bonusManejo;
    private int bonusVelocidad;
    
    
    public Trofeo() {

    }

    public Trofeo(int pk, TipoTrofeo tipo, ModeloTrofeo modelo) {//sin bbdd
        this.codigo = pk;
        this.tipo = tipo;
        this.modelo = modelo;
    }

    public Trofeo(int pk, int fkT, TipoTrofeo tipo, ModeloTrofeo modelo) {//con bbdd
        this.codigo = pk;
        this.idTorneo = fkT;
        this.tipo = tipo;
        this.modelo = modelo;
    }
    
    public Trofeo(Trofeo t, int bonusM, int bonusV) {
        this.codigo = t.codigo;
        this.bonusManejo = bonusM;
        this.bonusVelocidad = bonusV;
    }
    
    public Trofeo(int codigo, TipoTrofeo tipo, ModeloTrofeo modelo, int idTorneo, LocalDate fecha, int bonusManejo, int bonusVelocidad) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.modelo = modelo;
        this.idTorneo = idTorneo;
        this.fecha = fecha;
        this.bonusManejo = bonusManejo;
        this.bonusVelocidad = bonusVelocidad;
    }

    public void otorgarTrofeo(Vehiculo v) {
        Random rand = new Random();

        /*
        Los puntos otorgados en velocidad y manejo no pueden estar hardcodeados, tiene que cogerlo de las tablas sql
         */
        
        switch (tipo) {
            case ORO:
                v.setManejo(v.getManejo() + this.bonusManejo);
                v.setVelocidad(v.getVelocidad() + this.bonusVelocidad);
                
                System.out.println("Categoria Oro " + modelo + ": " + this.bonusManejo + " manejo y " + this.bonusVelocidad + " velocidad al vehiculo " + v.getCodigo());
                break;

            case PLATA:
                if (rand.nextInt(2) == 0) {//se pone 0 o 1 para que tenga 50% de posibilidades cada uno de ellos
                    v.setManejo(v.getManejo() + this.bonusManejo);
                    System.out.println("Categoria Plata " + modelo + ": " + this.bonusManejo + " manejo al vehiculo " + v.getCodigo());
                } else {
                    v.setVelocidad(v.getVelocidad() + this.bonusVelocidad);
                    System.out.println("Categoria Plata " + modelo + ": " + this.bonusVelocidad + " velocidad al vehiculo " + v.getCodigo());
                }
                break;

            case BRONCE:
                if (rand.nextInt(2) == 0) {//se pone 0 o 1 para que tenga 50% de posibilidades cada uno de ellos
                    v.setManejo(v.getManejo() + this.bonusManejo);
                    System.out.println("Categoria Bronce: " + modelo + " " + this.bonusManejo + " manejo al vehiculo " + v.getCodigo());
                } else {
                    v.setVelocidad(v.getVelocidad() + this.bonusVelocidad);
                    System.out.println("Categoria Bronce " + modelo + ": " + this.bonusVelocidad + " velocidad al vehiculo " + v.getCodigo());
                }
                break;

            default:
                System.out.println("Ese tipo de trofeo no es valido.");

        }
    }
//    public static Trofeo generarTrofeo(String s) {
//        ModeloTrofeo model[] = ModeloTrofeo.values();
//        
//        
//    }
    
    
    public Trofeo generarTrofeo(String s) {

        TipoTrofeo tps[] = TipoTrofeo.values();//Se crea un array con los tipos de trofeo que hay.
        ModeloTrofeo model[] = ModeloTrofeo.values();//Se crea un array con los modelos de trofeo que hay.

        System.out.println("Seleccione el Modelo de trofeo para el Torneo: ");
        for (int i = 0; i < model.length; i++) {//se imprimen
            //enum ModeloTrofeo{MEDALLA, COPA, PLATO, FIGURA};
            System.out.println((i + 1) + ".-" + model[i]);
        }

        Scanner sc = new Scanner(System.in);
        int opc = sc.nextInt();

        ModeloTrofeo select = model[opc - 1];//se iguala la opción a la posición del array

        Trofeo trof = new Trofeo();
        trof.modelo = select;
        //trof.puntos = trof.getPuntos();

        switch (select) {//menú de elección de vehículo, se le meten los atributos a la opción que sea
            case MEDALLA: {
                trof.modelo = ModeloTrofeo.MEDALLA;
                trof.codigo = ++contador;//contador interno para saber el número de trofeos creados.

                break;
            }
            case COPA: {
                trof.modelo = ModeloTrofeo.MEDALLA;
                trof.codigo = ++contador;//contador interno para saber el número de trofeos creados.
                break;
            }
            case PLATO: {
                trof.modelo = ModeloTrofeo.PLATO;
                trof.codigo = ++contador;//contador interno para saber el número de trofeos creados.
                break;
            }
            case FIGURA: {
                trof.modelo = ModeloTrofeo.FIGURA;
                trof.codigo = ++contador;//contador interno para saber el número de trofeos creados.
                break;
            }
            default: {
                System.out.println("Introduzca un tipo valido de trofeo.");
            }
        }
        //System.out.println("Trofeos: " + trof);
        return trof;
    }
    
    public static Trofeo crearTrofeo(int idTorneo, String tipo) {
        Trofeo t = new Trofeo();
        t.idTorneo = idTorneo;
        t.tipo = TipoTrofeo.valueOf(tipo);
        t.modelo = ModeloTrofeo.valueOf("FIGURA");
        return t;
    }

    public static Trofeo recibirTrofeo(int id, int idTorneo, TipoTrofeo tipo, ModeloTrofeo modelo) {
        return new Trofeo(id, idTorneo, tipo, modelo);
    }
    
    public static Trofeo bonusTrofeo(Trofeo f, int bonusManejo, int bonusVel) {
        return new Trofeo(f, bonusManejo, bonusVel);
    }
    
    public static TipoTrofeo stringToTipoT(String tipo) {
        return TipoTrofeo.valueOf(tipo.toUpperCase());
    }
    public static ModeloTrofeo stringToModeloT(String modelo) {
        return ModeloTrofeo.valueOf(modelo.toUpperCase());
    }
    
    
    /*
    generarTrofeo le va a entrar un string y devuelve un trofeo. Lee de la Base de Datos y te crea el tipo de trofeo (al final del torneo, que te tiene que dar uno de cada).
     */

    public int getCodigo() {
        return codigo;
    }

    public ModeloTrofeo getModelo() {
        return modelo;
    }
    public TipoTrofeo getTipo() {
        return tipo;
    }

    public int getBonusManejo() {
        return bonusManejo;
    }

    public int getBonusVelocidad() {
        return bonusVelocidad;
    }
    
    public int getIdTorneo() {
        return idTorneo;
    }

    public LocalDate getFecha() {
        return fecha;
    }


    public String toStringM() {
        return "Trofeo{" + "modelo=" + modelo + '}';
    }


    public String toStringT() {
        return "Trofeo{" + "tipo=" + tipo + '}';
    }

    @Override
    public String toString() {
        return "Trofeo{" + "codigo=" + codigo + ", tipo=" + tipo + ", modelo=" + modelo + ", idTorneo=" + idTorneo + ", fecha=" + fecha + ", contador=" + contador + ", bonusManejo=" + bonusManejo + ", bonusVelocidad=" + bonusVelocidad + '}';
    }

    
    
}
