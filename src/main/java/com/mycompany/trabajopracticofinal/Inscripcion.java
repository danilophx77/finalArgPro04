package com.mycompany.trabajopracticofinal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Inscripcion {

    //Atributos
    Materia materia;
    Alumno alumno;
    Date fecha = new Date();

    //Constructor lleno
    public Inscripcion(Alumno alumno, Materia materia) {
        this.alumno = alumno;
        this.materia = materia;
    }

    //Constructor vacio
    public Inscripcion() {
    }

    //Getters y Setters
    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void aprobada(Alumno alumno, Materia materia) {
        if (alumno.getMateriasAprobadas().containsAll(materia.getCorrelativas())) {
            System.out.println("Aprobado. " + "El alumno: " + alumno.getNombre()
                    + ", con Legajo N°: " + alumno.getLegajo()
                    + ", puede inscribirse a la materia: " + materia.getNombre() + ".");
        } else {
            System.out.println("Desaprobado. No posee las correlativas requeridas.");

        }
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Inscripcion{" + "materia=" + materia + ", alumno=" + alumno + ", fecha=" + fecha + '}';
    }

    public static void verificacion() throws SQLException, JsonProcessingException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el Legajo del alumno que quiere inscribir?");
        int legajoAlumno = sc.nextInt();
        System.out.println("¿A que materia se quiere inscribir?");
        String nombreMateria = sc.next();

        Inscripcion insc = new Inscripcion();
        insc.aprobada(traerDatosAlumno(legajoAlumno), traerDatosMateria(nombreMateria));
    }

    public static Alumno traerDatosAlumno(int legajo) throws SQLException, JsonProcessingException {

        Conexion conexion = new Conexion();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        Alumno alumno = new Alumno();


        conexion.estableceConexion();
        PreparedStatement ps = conexion.conectar.prepareStatement("SELECT * FROM alumnos WHERE legajo = ?");
        ps.setInt(1, legajo);
        ResultSet rs = ps.executeQuery();
        rs.next();

        // Traigo nombre y legajo
        alumno.setNombre(rs.getString("nombre"));
        alumno.setLegajo(rs.getInt("legajo"));

        // Transformo json de bd a arraylist y lo paso a alumno
        String jsonTextAprobadas = mapper.writeValueAsString(rs.getString("materias_aprobadas"));
        ArrayList<String> nombreCorrelativas = mapper.readValue(jsonTextAprobadas, ArrayList.class);
        alumno.setMateriasAprobadas(nombreCorrelativas);

        conexion.cerrarConnection();




        return alumno;
    }

    public static Materia traerDatosMateria(String nombre) throws SQLException, JsonProcessingException {

        Conexion conexion = new Conexion();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        conexion.estableceConexion();
        PreparedStatement stmt = conexion.conectar.prepareStatement("SELECT * FROM materias_final WHERE nombre=?");
        stmt.setString(1, nombre);
        ResultSet rs = stmt.executeQuery();
        rs.next();

        Materia materia = new Materia(rs.getString("nombre"));
        materia.setCorrelativas(mapper.readValue(mapper.writeValueAsString(rs.getString("correlativas_json")), ArrayList.class));

        conexion.cerrarConnection();



        return materia;
    }
}
