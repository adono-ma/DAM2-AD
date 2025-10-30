/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author ixchel
 */
import IO.DAO.CSV.FConfig;
import java.util.Scanner;

public class Vehiculo implements Comparable<Vehiculo> {

    private enum TipoVehiculo {
        MOTO, COCHE, BICI;

        public String toStringT() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    };

    private int codigo;
    private TipoVehiculo tipo;
    private int velocidad;
    private int manejo;
    private static int contador = 0;
    private int puntos = 0;
    private int puntosHistoricos = 0;

    /*
    Métodos:
    -Método "generarVehiculos" no recibe nada y devuelve vehículos
        -Tiene que dar las opciones de vehículo
        -Va a generar una moto/ un coche/ una bici
        - vel moto 50   vel coche 100   vel bici 25
        - manejo moto 50    manejo coche 25 manejo bici 100
    -Coeficiente de competición, no va a recibir nada y va a devolver un array, el primero con velocidad y el otro con manejo (array con 2 int)
     */
    public Vehiculo(int codigo, String tipo, int velocidad, int manejo, int puntos, int puntosHistoricos) {
        this.tipo = TipoVehiculo.valueOf(tipo);
        this.velocidad = velocidad;
        this.manejo = manejo;
        this.codigo = codigo;
        this.puntos = puntos;
        this.puntosHistoricos = puntosHistoricos;
    }

    public Vehiculo() {

    }

    public Vehiculo(int codigo, String tipo, int velocidad, int manejo, int puntos) {
        this.tipo = TipoVehiculo.valueOf(tipo);
        this.velocidad = velocidad;
        this.manejo = manejo;
        this.codigo = codigo;
        this.puntos = puntos;
    }

    static {//se ejecuta al principio de todo y carga solo una vez.
        Vehiculo.setContador(FConfig.cargarCodigo());
    }

    public static Vehiculo generarVehiculos() {

        TipoVehiculo tipos[] = TipoVehiculo.values();//Se crea un array con los tipos de vehículos que hay.

        System.out.println("Seleccione el vehiculo que va a competir: ");
        for (int i = 0; i < tipos.length; i++) {//se imprimen
            //enum TipoVehiculo{MOTO, COCHE, BICI};
            System.out.println((i + 1) + ".-" + tipos[i]);
        }

        Scanner sc = new Scanner(System.in);
        int opc = sc.nextInt();

        TipoVehiculo select = tipos[opc - 1];//se iguala la opción a la posición del array

        Vehiculo sl = new Vehiculo();
        sl.tipo = select;
        sl.puntos = sl.getPuntos();

        switch (select) {//menú de elección de vehículo, se le meten los atributos a la opción que sea
            case BICI: {
                sl.tipo = TipoVehiculo.BICI;
                sl.velocidad = 25;
                sl.manejo = 100;
                sl.codigo = ++contador;//contador interno para saber el número de vehículos creados.

                break;
            }
            case COCHE: {
                sl.tipo = TipoVehiculo.COCHE;
                sl.velocidad = 100;
                sl.manejo = 50;
                sl.codigo = ++contador;//contador interno para saber el número de vehículos creados.
                break;
            }
            case MOTO: {
                sl.tipo = TipoVehiculo.MOTO;
                sl.velocidad = 50;
                sl.manejo = 50;
                sl.codigo = ++contador;//contador interno para saber el número de vehículos creados.
                break;
            }
            default: {
                System.out.println("Introduzca un tipo valido de vehiculo.");
            }
        }
        sl.puntos = 0;//para que empieze con 0 puntos siempre
        System.out.println("Vehiculo: " + sl);
        return sl;
    }

    public int[] coeficienteCompeticion() {
//        int cc [] = new int[2];
//        cc[0]=this.velocidad;
//        cc[1]=this.manejo;
//        //int cc[]=new int[this.velocidad, this.manejo]; este sería en 2 líneas
//        return cc;

        return new int[]{this.velocidad, this.manejo}; //este es directamente 1 línea con las 4 líneas originales
    }

//    public int[] genCoeficienteCompeticion(){
//        return new int[]{velocidad, manejo}; //?????
//    }
    /*
    En el main: capturas m1 y ya haces las consultas sobre un mismo array
    Vehiculo.m1()
    int[] arr = C.m1();
    2 <- arr.length;
    50 <- arr[0]
    50 <- arr[1]
    
    Sin embargo, si llamas a m1 constantemente, se crean varios array y se queda el array creado flotando en la llamada.
    Es más cómodo, pero solo se hace si se van a hacer pocas consultas (1 ó 2):
    int n = Vehiculo.m1.length -> 2
    Vehiculo.m1[0] -> 50
    se crean 3 arrays, porque cada vez que se llama a Vehiculo.m1 se crea un array, así que lo mejor sería lo que se ha hecho antes,
    que solo se crea un array y se hacen varias consultas, aunque sea más largo.
    

     */
    public void agregarPtos(int pts) {
        this.puntos += pts;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public int getManejo() {
        return manejo;
    }

    public TipoVehiculo getTipo() {
        return tipo;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;//?????
    }

    public void sumarPuntos(int puntos) {
        this.puntos += puntos;
    }

    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Vehiculo.contador = contador;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public void setManejo(int manejo) {
        this.manejo = manejo;
    }

    public int myCompareTo(Object o) {
        if (o == null) {//si está vacío, pues nada
            throw new NullPointerException();
        }
        if (o.getClass().equals(this.getClass())) {//si nuestro objeto coincide
            Vehiculo v = (Vehiculo) o;//se crea una copia
            return Integer.compare(this.getPuntos(), v.getPuntos());//se comparan los puntos
        }
        return 0;
    }

    @Override
    public int compareTo(Vehiculo o) {
        return Integer.compare(o.puntos, this.puntos);
    }

    public String vehiculoToCsv(Vehiculo v) {
        return v.getCodigo() + "," + String.valueOf(v.getTipo()) + "," + v.getVelocidad() + "," + v.getManejo() + "," + v.getPuntos();
    }

    public static TipoVehiculo stringToTipoV(String tipo) {
        return TipoVehiculo.valueOf(tipo.toUpperCase());
    }

//    public static Vehiculo crearVehiculoFactory(String tipo, int codigo) {
//        switch (tipo.toUpperCase()) {
//            case "COCHE":
//                return new Coche (codigo);
//            case "MOTO":
//                return new Moto (codigo);
//            case "BICICLETA":
//                return new Bicicleta (codigo);
//            default:
//                System.out.println("Tipo no autorizado: " + tipo);
//                return null;
//        }
//    }
    @Override
    public String toString() {
        return "Vehiculo{" + "codigo=" + codigo + ", tipo=" + tipo + ", velocidad=" + velocidad + ", manejo=" + manejo + ", puntos=" + puntos + ", puntosHistoricos=" + puntosHistoricos + '}';
    }

    public String toStringT() {
        return "Vehiculo{" + "tipo=" + tipo + '}';
    }
}
