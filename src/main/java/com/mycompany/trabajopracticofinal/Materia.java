package com.mycompany.trabajopracticofinal;

import com.google.gson.Gson;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Materia {

    //Atributos      
    String nombre;
    List<String> correlativas = new ArrayList<>();

    //Constructor lleno
    public Materia(String nombre) {
        this.nombre = nombre;
    }

    //Constructor vacio
    public Materia() {
    }

    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getCorrelativas() {
        return correlativas;
    }

    public void setCorrelativas(List<String> correlativas) {
        this.correlativas = correlativas;
    }

    /**
     * *******************************************************************
     * Metodo para crear materias
     *
     * @throws java.sql.SQLException
     * *********************************************************************
     */
    public static void crearMateria() throws SQLException {

        Scanner sc = new Scanner(System.in).useDelimiter("\n");
        Conexion conexion = new Conexion();

        Materia materia = new Materia();

        System.out.println("Que nombre quiere que tenga la materia?");
        String nombre = sc.next();
        materia.setNombre(nombre);

        System.out.println("Cuantas correlativas tiene?");

        int numero = sc.nextInt();

        System.out.println("Que materias desea agregar a las correlativas?");
        ArrayList<String> correlativas = new ArrayList<>();

        String input;

        for (int i = 0; i < numero; i++) {
            input = sc.next();
            correlativas.add(input);
        }

        String correlativasJson = new Gson().toJson(correlativas);

        conexion.estableceConexion();
        PreparedStatement pstmt = conexion.conectar.prepareStatement("INSERT INTO materias_final VALUES (?, ?)");
        pstmt.setString(1, nombre);
        pstmt.setString(2, correlativasJson);
        pstmt.executeUpdate();
        System.out.println("++++++ Materia y correlativas creadas correctamente ++++++");
        conexion.cerrarConnection();



    }
}
