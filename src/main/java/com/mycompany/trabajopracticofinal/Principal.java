package com.mycompany.trabajopracticofinal;

import static com.mycompany.trabajopracticofinal.Alumno.agregarAlumnos;
import static com.mycompany.trabajopracticofinal.Inscripcion.verificacion;
import static com.mycompany.trabajopracticofinal.Materia.crearMateria;
import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {

    public static void principal() throws SQLException, IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("************ Bienvenido al Validador de Correlativas ************");
        while (true) {

            //int opcion = 0;
            try {
                System.out.println("\n¿Que desea realizar? Elija una opcion:");
                System.out.println("1- Agregar Materias\n2- Agregar Alumnos\n3- Inscripciones\n0- Salir");

                int opcion = sc.nextInt();

                switch (opcion) {
                    case 0 -> {
                        System.out.println("\nGracias por usar el programa ¡Hasta luego!");
                        System.exit(0);
                    }

                    case 1 -> {
                        do {
                            crearMateria();
                            System.out.println("\n¿Desea crear otra materia? Elija una opcion:");
                            System.out.println("1- SI\n2- No. Volver al menu principal\n");
                            opcion = sc.nextInt();
                        } while (opcion == 1);
                    }

                    case 2 -> {
                        do {
                            agregarAlumnos();
                            System.out.println("\n¿Desea registrar otro alumno? Elija una opcion:");
                            System.out.println("1- SI\n2- No. Volver al menu principal\n");
                            opcion = sc.nextInt();
                        } while (opcion == 1);
                    }

                    case 3 -> {
                        do {
                            verificacion();
                            System.out.println("\n¿Desea realizar otra inscripcion? Elija una opcion:");
                            System.out.println("1- SI\n2- No. Volver al menu principal\n");
                            opcion = sc.nextInt();
                        } while (opcion == 1);
                    }
                    default -> {
                        System.out.println("\nOpcion no valida\n");
                        System.out.println("---------------------------------------------------------------------------------------");
                        System.out.println("---------------------------------------------------------------------------------------\n");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("\nOpcion no valida\n");
                System.out.println("---------------------------------------------------------------------------------------");
                System.out.println("---------------------------------------------------------------------------------------\n");
                sc.nextLine();
            }
        }
    }
}
