package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigDB {

    //Este atributo tendrá el estado de la conexión
    public static Connection objConnection = null;

    //Método para conectar la BD
    public static Connection openConnection(){

        try{
            //Llamamos el driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //Creamos las variables de conexión

            String url = "jdbc:mysql://b9nr8lhpf3xawlgylly6-mysql.services.clever-cloud.com:3306/b9nr8lhpf3xawlgylly6";
            String user = "uqdpmy04paxd3v1w";
            String password = "HsP2BJBbVqKnmQfqQSto";

            //Que devuelva la conexión de tipo Connection
            objConnection = (Connection) DriverManager.getConnection(url,user,password);
            System.out.println("Me conecté perfectamente!!!!!");


        }catch(ClassNotFoundException error){
            System.out.println("ERROR >> Driver no Instalado " + error.getMessage());
        }catch(SQLException error){
            System.out.println("ERROR >> error al conectar con la base de datos" + error.getMessage());
        }
        return objConnection;
    }

    //Metodo para finalizar una conexión
    public static void closeConnection(){
        try{
            //Si hay una conexión activa entonces la cierra
            if(objConnection != null){
                objConnection.close();
                System.out.println("Se finalizó la conexión con exito");
            }


        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

}
