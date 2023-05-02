package com.mycompany.trabajopracticofinal;

import com.google.gson.Gson;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Alumno {

    //Atributos de clase
    String nombre;
    int legajo;

    //Lista de materias aprobadas por cada alumno
    ArrayList<String> materiasAprobadas = new ArrayList<>();

    //Constructor lleno
    public Alumno(String nombre, int legajo) {
        this.nombre = nombre;
        this.legajo = legajo;
    }

    //Constructor vacio
    public Alumno() {
    }

    //Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public ArrayList<String> getMateriasAprobadas() {
        return materiasAprobadas;
    }

    public void setMateriasAprobadas(ArrayList<String> materiasAprobadas) {
        this.materiasAprobadas = materiasAprobadas;
    }

    @Override
    public String toString() {
        return "Alumno{" + "nombre=" + nombre + ", legajo=" + legajo + ", materiasAprobadas=" + materiasAprobadas + '}';
    }

    public static void agregarAlumnos() throws SQLException {
        Scanner sc = new Scanner(System.in);
        Conexion conexion = new Conexion();
        int legajo;

        //Validacion ingreso nombre
        System.out.println("Ingrese el nombre del alumno: ");
        String nombre = sc.nextLine();
        //Si da enter sin cargar algo le vuelve a realizar la solicitud
        while (!nombre.matches("^[a-zA-Z\\s]+$")) {
            System.out.println("Formato no valido.Por favor, ingrese nombre del alumno: ");
            nombre = sc.nextLine();
        }
        //Validacion ingreso legajo
        do {
            System.out.println("Ingrese el numero de legajo de 5 digitos: ");
            while (!sc.hasNextInt()) {
                System.out.println("Formato no valido. Ingrese legajo de 5 digitos: ");
                sc.next();
            }
            legajo = sc.nextInt();
        } while (!String.valueOf(legajo).matches("[0-9]{5}"));

        System.out.println("++++++ Datos Validados ++++++\nAlumno: " + nombre
                + "\nLegajo: " + legajo
                + "\n+++++++++++++++++++++++++++++");

        System.out.println("¿Cuantas materias tiene aprobadas?");
        int numMatApr = sc.nextInt();

        ArrayList<String> materiasAprobadas = new ArrayList<>();
        System.out.println("¿Que materias tiene aprobadas?");
        for (int i = 0; i < numMatApr; i++) {
            materiasAprobadas.add(sc.next());
        }

        String aprobadasJson = new Gson().toJson(materiasAprobadas);



        conexion.estableceConexion();
        PreparedStatement stmt = conexion.conectar.prepareStatement("INSERT INTO alumnos VALUES (?, ?, ?)");
        stmt.setString(1, nombre);
        stmt.setInt(2, legajo);
        stmt.setString(3, aprobadasJson);
        int filasAfectadas = stmt.executeUpdate();

        if (filasAfectadas == 1) {
            System.out.println("\n+++++ Se ha cargado exitosamente a la DB +++++\nAlumno: " + nombre
                    + "\nLegajo N°: " + legajo
                    + "\nMaterias Aprobadas: " + materiasAprobadas);
        } else {
            System.out.println("\n+++++ Hubo un error al cargar en la DB +++++\n");
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++\n");
        //Cerrar DB
        stmt.close();
        conexion.cerrarConnection();

    }
      }

  