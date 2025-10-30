/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import IO.DAO.BBDD;
import IO.DAO.JDBC.DAOJDBCTorneo;
import IO.DAO.JDBC.DAOJDBCVehiculo;
import Modelo.Torneo;
import Modelo.Vehiculo;
import Utils.Utils;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ixchel
 */

public class MainMenu {

    public static void main(String[] args) {
        boolean crearBBDD = BBDD.crearBBDD(); 

        DAOJDBCVehiculo vjdbc = new DAOJDBCVehiculo();
        DAOJDBCTorneo torjdbc = new DAOJDBCTorneo();

        ArrayList<Vehiculo> vehiculos = vjdbc.readAll();
        ArrayList<Torneo> torneos = torjdbc.readAll();
        
        Scanner sc = new Scanner(System.in);
        int opcion, op, n;
        do {
            mostrarMenu();
            opcion = Utils.entradasMenu(3);
            switch (opcion) {
                case 1:
                    menuVehiculo();
                    switch (op = Utils.entradasMenu(4)) {
                        case 1:
                            if (vjdbc.create(Vehiculo.generarVehiculos())) {
                                System.out.println("Vehiculo insertado correctamente.");
                                vehiculos = vjdbc.readAll();
                            } else {
                                System.out.println("No se ha podido insertar el vehiculo.");
                            }
                            break;
                        case 2:
                            mostrarVehiculos(vehiculos);
                            break;

                        case 3:
                            n = seleccionVehiculo(vehiculos);
                            if (n == -1) {
                                break;
                            }
                            if (vjdbc.delete(vjdbc.read(n))) {
                                System.out.println("El vehiculo ha sido eliminado.");
                                vehiculos = vjdbc.readAll();
                            } else {
                                System.out.println("No se ha podido borrar el vehiculo.");
                            }

                            break;
                        case 4:
                            n = seleccionVehiculo(vehiculos);
                            if (n == -1) {
                                break;
                            }
                            if (vjdbc.modificar(vjdbc.read(n))) {
                                System.out.println("El vehiculo ha sido modificado.");
                                vehiculos = vjdbc.readAll();
                            } else {
                                System.out.println("No se ha podido modificar el vehiculo.");
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case 2:
                    menuTorneo();
                    switch (op = Utils.entradasMenu(4)) {
                        case 1:
                            Torneo taux = Torneo.crearTorneo();
                            taux.actualizaTrofeos(torjdbc.alta(taux) - 1);
                            torjdbc.guardarTrofeo(taux.getTrofeos());
                            torneos = torjdbc.leerTodo();

                            break;
                        case 2:
                            mostrarTorneos(torneos);
                            break;
                        case 3:
                            n = seleccionTorneo(torneos);
                            if (n == -1) {
                                break;
                            }
                            if (torjdbc.baja(torjdbc.buscar(n))) {
                                System.out.println("Se ha borrado el torneo");
                                torneos = torjdbc.leerTodo();
                            } else {
                                System.out.println("No se ha podido borrar el torneo");
                            }

                            break;
                        case 4:
                            n = seleccionTorneo(torneos);
                            if (n == -1) {
                                break;
                            }
                            if (torjdbc.modificar(torjdbc.buscar(n))) {
                                System.out.println("Se ha modificado el torneo");
                                torneos = torjdbc.leerTodo();
                            } else {
                                System.out.println("No se ha podido modificar el torneo");
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case 3:
                    n = seleccionTorneoAbiertos(torneos);
                    Torneo t1 = buscarTorneos(n, torneos);
                    if (t1 != null) {
                        boolean completado = false;
                        do {
                            menuParticipar();
                            switch (op = Utils.entradasMenu(2)) {
                                case 1:
                                    boolean incompleto;
                                    do {
                                        n = seleccionVehiculo(vehiculos);
                                        if (n == -1) {
                                            incompleto = false;
                                        } else {
                                            incompleto = t1.inscribir(buscarVehiculo(n, vehiculos));
                                        }
                                    } while (incompleto);
                                    break;
                                case 2:
                                    t1.inciarTorneo();
                                    completado = true;
                                    break;

                                default:
                                    completado = true;
                                    break;
                            }
                        } while (!completado);
                    }
                    break;

                default:
                    System.out.println("Saliendo del programa");
                    break;

            }

        } while (opcion != 0);
        //DAO_CSV_Vehiculo vficheros = null;

        //vjdbc = new DAO_JDBC_Vehiculo();
        //vficheros = new DAO_CSV_Vehiculo();        
        //vjdbc.alta(Vehiculo.crearVehiculo());
        //ArrayList<Vehiculo> vehiculos = vjdbc.leerTodo();
        //System.out.println(vehiculos);
        //Vehiculo v1 = vjdbc.buscar(2);
        /*vficheros.alta(Vehiculo.crearVehiculo());
      ArrayList<Vehiculo> vehiculos = vficheros.leerTodo();
      System.out.println(vehiculos);*/
 /*Vehiculo v1 = Vehiculo.crearVehiculo();
        System.out.println(v1);*/
 /*torjdbc.alta(new Torneo("Primero",5,LocalDate.now()));
       torjdbc.alta(new Torneo("Segundo",3,LocalDate.now()));*/
        //Torneo t1 = torjdbc.buscar(1);
        //System.out.println(t1);
        /*ArrayList<Torneo> torneos = torjdbc.leerTodo();
       System.out.println(torneos);*/
 /*for (Vehiculo vehiculo : vehiculos) {
            t1.inscribir(vehiculo);
        }
        t1.inciarTorneo();*/
        Bbdd.getInstancia().closeConnection();

    }

    public static void mostrarMenu() {
        System.out.println();
        System.out.println("1. Gestionar Vehiculos");
        System.out.println("2. Gestionar Torneos");
        System.out.println("3. Participar");
        System.out.println("4. Salir");
        System.out.println();
    }

    ;
    public static void menuVehiculo() {
        System.out.println();
        System.out.println("1. Alta vehiculo");
        System.out.println("2. Mostrar Vehiculos");
        System.out.println("3. Baja Vehiculo");
        System.out.println("4. Modificar Vehiculo");
        System.out.println("5. Salir");
        System.out.println();
    }

    public static void menuTorneo() {
        System.out.println();
        System.out.println("1. Alta Torneo");
        System.out.println("2. Mostrar Torneos");
        System.out.println("3. Borrar Torneo");
        System.out.println("4. Modificar Torneo");
        System.out.println("5. Salir");
        System.out.println();
    }

    public static void menuParticipar() {
        System.out.println("1. Inscribir Vehiculo");
        System.out.println("2. Iniciar Torneo");
        System.out.println("3. Salir");
        System.out.println();
    }

    public static void mostrarVehiculos(ArrayList<Vehiculo> vehiculos) {
        int num = 1;
        for (Vehiculo vehiculo : vehiculos) {
            System.out.println(" " + num + ": " + vehiculo.toString());
            System.out.println("");
            num++;
        }
    }

    public static int seleccionVehiculo(ArrayList<Vehiculo> vehiculos) {
        System.out.println("Selecciona el numero del vehiculo");
        System.out.println();
        mostrarVehiculos(vehiculos);
        System.out.println(" 0: Salir");
        int n = Utils.entradasMenu(vehiculos.size());
        if (n == 0) {
            return -1;
        }
        return vehiculos.get(n - 1).getCodigo();
    }

    public static void mostrarTorneos(ArrayList<Torneo> torneos) {
        int num = 1;
        for (Torneo torneo : torneos) {
            System.out.println(" " + num + ": " + torneo.toString());
            System.out.println("");
            num++;
        }
    }

    public static int seleccionTorneo(ArrayList<Torneo> torneos) {
        System.out.println("Selecciona el numero del torneo");
        System.out.println();
        mostrarTorneos(torneos);
        System.out.println(" 0: Salir");
        int n = Utils.entradasMenu(torneos.size());
        if (n == 0) {
            return -1;
        }
        return torneos.get(n - 1).getId();
    }

    public static int seleccionTorneoAbiertos(ArrayList<Torneo> torneos) {
        System.out.println("Selecciona el numero del torneo");
        System.out.println();
        ArrayList<Torneo> torneosAbi = new ArrayList<>();
        for (Torneo torneo : torneos) {
            if (torneo.isInscripcion()) {
                torneosAbi.add(torneo);
            }
        }
        mostrarTorneos(torneosAbi);
        System.out.println(" 0: Salir");
        int n = Utils.entradasMenu(torneosAbi.size());
        if (n == 0) {
            return 0;
        }

        return torneosAbi.get(n - 1).getId();
    }

    public static Torneo buscarTorneos(int id, ArrayList<Torneo> torneos) {
        Torneo t1 = null;
        for (Torneo torneo : torneos) {
            if (torneo.getId() == id) {
                return torneo;
            }
        }
        return t1;
    }

    public static Vehiculo buscarVehiculo(int id, ArrayList<Vehiculo> vehiculos) {
        Vehiculo v1 = null;
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getCodigo() == id) {
                return vehiculo;
            }
        }
        return v1;
    }


    }
    
}
