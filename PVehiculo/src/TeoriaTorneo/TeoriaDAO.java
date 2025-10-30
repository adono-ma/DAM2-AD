/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TeoriaTorneo;

/**
 *
 * @author ixchel
 */
public class TeoriaDAO {
    /*
    Patrón de diseño es la estandarización de un proceso
    DAO (Data Access Object): separa la capa de lógica de la capa de datos.
    De este modo, se puede adaptar esta lógica a cualquier tipo de datos, ya sea
    una BBDD, ficheros o lo que sea.
    
    El desarrollador no accede a los datos, sino que se hace a través de una capa intermedia.
    Tendremos:
    - VehiculosDAO
    - CircuitosDAO
    *De Torneo no vamos a hacer dao probablemente.
    
    Vamos a necesitar:
    - Objeto DAO: un objeto "simple" o POJO
    - InterfazDAO: métodos abstractos
    (no hace falta que se ponga abstract, además tiran throws para las excepciones)
    para que lo implementen los otros
        -Alta
        - Baja
        - Buscar
        - Modificar
    - ClaseDAO: los que implementan los métodos de la interfaz
        -Atributo único: conexión.
        -los atributos nunca serán estáticos.
        - 
    *Mirar el try catch with resources
    
    
    /*
    Un patron de diseño se usa para homogenizar el diseño (Para que todos lo hagan parecidos.)
    
    DAO (Data Access Object).
    POJO (Un Pojo es algo sin nada raro (Abstracto, interfaces, etc...)) -> CRUD (Create Read Update Delete)
    
    CRUD:
        - Object:   Create  Read    Update  Delete.
        - BBDD:     Insert  Select  Update  Delete.
    El Create/Insert, Update y Delete Devuelve Boolean y Recibe un coche.
    
    Tengo tres carpetas de Objeto:
     _____
    |DAO_Implements |
    |_____|
     _
    |DAO ---> 
    |_| Interfaces
     ___
    |Negocio ---> Algoritmo de Programa
    |____|
    
Estructura:
            - Crear paquete llamado Modelo donde se metera todo que no sea Main.
            - Crear paquete de Utilidades o Utils -> Aqui e.j metimos Random
                - EntradaControlable: Convierte cadena de texto...
            - Crear paquete IO (Entrada/Salida)-> BaseDatosGestion y Ficheros...
                - Dentro crear paquete llamado DAO, donde estaran nuestras Interfaces
    
        ¿Que queremos hacer:?
            - Contra Vehiculo: CRUD (Create, Read, Update, Delete)
                - En la Interfaz dentro del paquete DAO, mismo nivel que paquetes JDBC, Ficheros: VehiculoDAO
                    - public abstract boolean Create(Vehiculo v) throws SQLException; -> Entra Vehiculo y Devuelve Boolean
                    - public abstract boolean Delete(Vehiculo v) throws SQLException; -> Entra Vehiculo y Devuelve Boolean
                    - public abstract Vehiculo Read(int cod) throws SQLException; -> Entra codigo(pk) y Devuelve Vehiculo
                    - public abstract AL<Vehiculos> ReadAll() throws SQLException; -> Devuelve AL de Vehiculos sin que entre nada
                    - public abstract boolean Modificar(Vehiculo v) throws SQLException; -> Entra Vehiculo y Devuelve Boolean
                    - public abstract boolean Delete(Vehiculo v) throws SQLException; -> Entra Vehiculo y Devuelve Boolean
            
                    Crear solo Create, Read, ReadAll, tanto en JDBC como en Ficheros
                        - Llamar las clases: DAOJDBCVehiculo y DAOCSVVehiculo

__________________________

    REFACTORING GURU -> Pagina de ejemplos que le gusta a Ivan. https://refactoring.guru/es/design-patterns/factory-method
    
    Patron de diseño: DAO y MVC, Factory, Singleton
    
    DAO: Data Access Object. Para programar desde DAO a las capas superiores.
        - Estandariza AD utilizando Objetos
        - Desarollador no accede a datos sino por la capa intermedia DAO.
        - DAO encapsula codigo necesario para localizar y acceder a fuente de datos.
        - Interfaz no cambia, sin importar estructura de datos que exista por debajo del DAO, asi es transparente
          para el desarollador estar trabajando con una BBDD SQL que con un fichero plano.
    
    DAO Estructura: 
        - Objeto DAO
        - Interfaz -> Donde estan los metodos (No se escribe nada en teoria)
            - public abstract (abstract no hace falta porque todos los metodos son abstractos por defecto)
        - Clase -> XML (iterar por nodos hijos, etc...), JDBC
            - Hacen todas lsa operaciones, las que no se realizan en metodos estaticos.
                - Esto es porque no se llaman de aqui, sino de un objeto de esta clase.
                        Ejecutable: en un try catch : exe = new JDBCUusarioDAO();
            - JDBC solo tiene 1 atributo, que es la conexion
    
    Try Catch -> Por si explota todo
   
    
    DAO metodos: devuelve boolean o enteros // CRUD -> Insert, Read (Select), Update, Delete.
        - getObject()
         getObjectByAttribute()
    
    * Las operaciones no se realizan con static metodos sino que pertenecen a un objeto de la implementacion.
    
    Create Datebase if not exist [Nombre_BBDD];
    USE [Nombre_BBDD];
    Create table if not exist [Nombre_tabla];
    getConnection()
    CloseConnection()
    
    https://www.arquitecturajava.com/patron-factory-para-que-sirve/
    FACTORIZACIÓN: proporciona una interfaz para crear objetos en una superclase que permite, a su vez,
    a las subclases alterar el tipo de objetos que se crearán.
    
    La clase abstracta/interfaz es de la que heredan el resto de clases
    un factory sin herencia es el generarVehiculo
    
    
    https://refactoring.guru/es/design-patterns/singleton
    SINGLETON: Garantiza que una clase tenga una única instancia (ejemplo, la conexión), por lo que las otras clases
    no podrán crear neww Objeto. Además, proporciona un punto de acceso global a la instancia
    
    private static Connection conexionSingleton = null; //conexion única
    private myConexionSingleton(){} //creamos un constructor privado para que no se pueda llamar desde fuera.
    
    public static Connection (EntityManager) getConnection(){
        if (conexionSingleton == null) {
            ConexionSingletonFactori csf = Persistence.createConexionSingletonFactori("persistence");
            
*/
    
    /*
    Al finalizar Torneo insertar en la tabla Participa los resultados
    Menu Vehiculo y Torneo
        - Torneos Abiertos o todos
    */
    
    
//    
//    Meter en torneo t1 y que no deje meter t2 y t3 (rollback)
//            try {
//                conection.setAutoCommit(false);
//                s.executeUpdate("INSERT NTO...");
//                conexion.commit();
//                ...
//            } catch (SQLException e) {
//                if(conexion!=null) {
//                    try {
//                        conexion.rollback();
//                    }catch (SQLException ex) {
//                        System.out.println(ex.toString());
//                    }
//                }
//            }
    
    /*
    public class miEntityManager {

    // Patron 'Singleton'
    private static EntityManager entityManager = null; // Instancia única

    private miEntityManager() {} // Creamos el constructor privado para que no se pueda llamar desde fuera.

    // Este metodo es la unica forma de llamar a la instancia de miEntityManager
    // De esta forma nos aseguramos que tenemos una instancia única y no la repetimos
    public static EntityManager getEntityManager() {
        if (entityManager == null) {
                    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");// NUESTRO JDBC 
                    
    
                    NEW CONNECTION
    
    
                    entityManager = emf.createEntityManager();
        }
        return entityManager;
    }
}

//Persistence  Nombre de la unidad de persistencia [HibernateTest_PU]

========EJEMPLO DE USO===================

Para usarlo:
miEntityManager.getEntityManager()

Para cerrarlo:
miEntityManager.getEntityManager().close()


    */
    
}
