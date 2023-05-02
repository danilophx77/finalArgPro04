package com.mycompany.trabajopracticofinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    Connection conectar = null;
    String usuario = "root";
    String contraseña = "Larateamo77";
    String bd = "arg_programa";
    String ip = "localhost";
    String puerto = "3306";
    //ruta a DB
    String ruta = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd;

    public Connection estableceConexion() {

        try {
            //para establecer la conexion
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection(ruta, usuario, contraseña);
            System.out.println("\n*********** Se conecto exitosamente a la DB **************");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(" No se conecto correctamente a la DB" + e);
        }
        return conectar;
    }

    public void cerrarConnection() throws SQLException {
        try {
            conectar.close();
            System.out.println("****************** Desconexion con DB ********************\n");
        } catch (SQLException e) {
        }
    }
}
