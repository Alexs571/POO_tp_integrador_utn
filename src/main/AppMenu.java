
package main;

import Service.HistoriaClinicaServiceImpl;
import Service.PacienteServiceImpl;
import DAO.HistoriaClinicaDAO;
import DAO.PacienteDAO;

import java.util.Scanner;

public class AppMenu {

    private final Scanner scanner ;
    private final MenuHandler menuHandler;

    public AppMenu() {
        scanner = new Scanner(System.in);
        PacienteDAO pacienteDAO = new PacienteDAO();
        HistoriaClinicaDAO historiaClinicaDAO = new HistoriaClinicaDAO();

        PacienteServiceImpl pacienteService = new PacienteServiceImpl(pacienteDAO, historiaClinicaDAO);
        HistoriaClinicaServiceImpl historiaClinicaService = new HistoriaClinicaServiceImpl(historiaClinicaDAO);

        menuHandler = new MenuHandler(scanner, pacienteService, historiaClinicaService);
    }

    public void run() {
        boolean running = true;

        while (running) {
            MenuDisplay.mostrarMenuPrincipal(menuHandler.hayPacienteSeleccionado());
            int opcion = leerOpcion();
            running = manejarOpcion(opcion);
        }

        scanner.close();
    }

    private int leerOpcion() {
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            scanner.nextLine();
        }
        int op = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer
        return op;
    }

    private boolean manejarOpcion(int opcion) {
        switch (opcion) {

            case 1 -> menuHandler.crearPaciente();
            case 2 -> menuHandler.verPacientePorDni();
            case 3 -> menuHandler.listarPacientes();
            case 4 -> menuHandler.actualizarPaciente();
            case 5 -> menuHandler.eliminarPaciente();

            case 6 -> {
                if (menuHandler.hayPacienteSeleccionado()) {
                    menuHandler.listarHistoriasClinicas();
                } else {
                    System.out.println("Debe seleccionar un paciente primero (opción 2).");
                }
            }

            case 7 -> {
                if (menuHandler.hayPacienteSeleccionado()) {
                    menuHandler.actualizarHistoriaClinica();
                } else {
                    System.out.println("Debe seleccionar un paciente primero (opción 2).");
                }
            }

            case 8 -> {
                if (menuHandler.hayPacienteSeleccionado()) {
                    menuHandler.eliminarHistoriaClinica();
                } else {
                    System.out.println("Debe seleccionar un paciente primero (opción 2).");
                }
            }

            case 9 -> menuHandler.limpiarPacienteSeleccionado();

            case 0 -> {
                System.out.println("Saliendo...");
                return false;
            }

            default -> System.out.println("Opción no válida.");
        }

        return true;
    }
}
