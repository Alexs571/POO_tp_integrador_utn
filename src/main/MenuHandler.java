package main;

import Models.GrupoSanguineo;
import Models.HistoriaClinica;
import Models.Paciente;
import Service.HistoriaClinicaServiceImpl;
import Service.PacienteServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuHandler {


    private final Scanner scanner;
    private final PacienteServiceImpl pacienteService;
    private final HistoriaClinicaServiceImpl historiaService;


    private Paciente pacienteSeleccionado;

    public MenuHandler(Scanner scanner,
                       PacienteServiceImpl pacienteService,
                       HistoriaClinicaServiceImpl historiaService) {

        if (scanner == null) {
            throw new IllegalArgumentException("Scanner no puede ser null");
        }
        if (pacienteService == null) {
            throw new IllegalArgumentException("PacienteService no puede ser null");
        }
        if (historiaService == null) {
            throw new IllegalArgumentException("HistoriaClinicaService no puede ser null");
        }

        this.scanner = scanner;
        this.pacienteService = pacienteService;
        this.historiaService = historiaService;
    }

    // =========================================================
    //  PACIENTE SELECCIONADO
    // =========================================================

    public boolean hayPacienteSeleccionado() {
        return pacienteSeleccionado != null;
    }

    public void limpiarPacienteSeleccionado() {
        pacienteSeleccionado = null;
        System.out.println("Paciente deseleccionado. Volviendo al menú principal...");
    }

    // OPCIÓN 2: seleccionar paciente por DNI
    public void verPacientePorDni() {
        try {
            System.out.print("Ingrese DNI del paciente: ");
            String dni = scanner.nextLine().trim();


            Paciente p = pacienteService.buscarPorDni(dni);

            if (p == null) {
                System.out.println("No se encontró ningún paciente con DNI " + dni);
                pacienteSeleccionado = null;
                return;
            }

            if (p.getHistoriaClinica() != null && p.getHistoriaClinica().getId() > 0) {
                try {
                    var hcCompleta = historiaService.getById(p.getHistoriaClinica().getId());
                    p.setHistoriaClinica(hcCompleta);
                } catch (Exception ex) {
                    System.out.println("No se pudo cargar la historia clínica completa: " + ex.getMessage());
                }
            }
            // Guardamos el paciente seleccionado
            pacienteSeleccionado = p;

            // Mostramos todos sus datos
            System.out.println("\n=== Paciente seleccionado ===");
            System.out.println("ID: " + p.getId());
            System.out.println("Nombre: " + p.getNombre());
            System.out.println("Apellido: " + p.getApellido());
            System.out.println("DNI: " + p.getDni());
            System.out.println("Fecha de nacimiento: " + p.getFechaNacimiento());


            if (p.getHistoriaClinica() != null) {
                System.out.println("ID Historia Clínica: " + p.getHistoriaClinica().getId());
                System.out.println("Nro Historia: " + p.getHistoriaClinica().getNroHistoria());
                System.out.println("Grupo sanguíneo: " + p.getHistoriaClinica().getGrupoSanguineo());
            } else {
                System.out.println("Historia Clínica: (sin historia asociada o no cargada)");
                System.out.println("Grupo sanguíneo: (sin historia asociada o no cargada)");
            }

            System.out.println("Eliminado: " + p.isEliminado());
            System.out.println("=============================\n");

        } catch (Exception e) {
            System.err.println("Error al seleccionar paciente por DNI: " + e.getMessage());
        }
    }



    // =========================================================
    //  PACIENTES
    // =========================================================

    // OPCIÓN 1: Crear Paciente (+ historia clínica)
    public void crearPaciente() {
        try {
            System.out.println("\n--- Crear Paciente ---");

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();

            System.out.print("Apellido: ");
            String apellido = scanner.nextLine().trim();

            System.out.print("DNI: ");
            String dni = scanner.nextLine().trim();

            System.out.print("Fecha de nacimiento (yyyy-MM-dd, Enter para omitir): ");
            String fechaStr = scanner.nextLine().trim();
            LocalDate fechaNacimiento = null;
            if (!fechaStr.isEmpty()) {
                try {
                    fechaNacimiento = LocalDate.parse(fechaStr);
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de fecha inválido. Se deja null.");
                }
            }

            GrupoSanguineo grupoSanguineo = leerGrupoSanguineo();

            Paciente p = new Paciente();
            p.setNombre(nombre);
            p.setApellido(apellido);
            p.setDni(dni);
            p.setFechaNacimiento(fechaNacimiento);
            p.setGrupoSanguineo(grupoSanguineo);

            // La lógica de crear la historia clínica asociada está en PacienteServiceImpl.insertar()
            pacienteService.insertar(p);

            System.out.println("Paciente creado exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al crear paciente: " + e.getMessage());
        }
    }

    // OPCIÓN 3: Listar Pacientes
    public void listarPacientes() {
        try {
            System.out.println("\n--- Listado de Pacientes ---");
            List<Paciente> pacientes = pacienteService.getAll();
            if (pacientes == null || pacientes.isEmpty()) {
                System.out.println("No hay pacientes cargados.");
                return;
            }
            for (Paciente p : pacientes) {
                System.out.println(p);
            }
        } catch (Exception e) {
            System.err.println("Error al listar pacientes: " + e.getMessage());
        }
    }

    // OPCIÓN 4: Actualizar Paciente
    public void actualizarPaciente() {
        try {
            System.out.println("\n--- Actualizar Paciente ---");
            System.out.print("ID del paciente a actualizar: ");
            Long id = leerLong();

            Paciente p = pacienteService.getById(id);
            if (p == null) {
                System.out.println("Paciente no encontrado.");
                return;
            }

            System.out.println("Paciente actual: " + p);

            System.out.print("Nuevo nombre (actual: " + p.getNombre() + ", Enter para dejar igual): ");
            String nuevoNombre = scanner.nextLine().trim();
            if (!nuevoNombre.isEmpty()) {
                p.setNombre(nuevoNombre);
            }

            System.out.print("Nuevo apellido (actual: " + p.getApellido() + ", Enter para dejar igual): ");
            String nuevoApellido = scanner.nextLine().trim();
            if (!nuevoApellido.isEmpty()) {
                p.setApellido(nuevoApellido);
            }

            System.out.print("Nuevo DNI (actual: " + p.getDni() + ", Enter para dejar igual): ");
            String nuevoDni = scanner.nextLine().trim();
            if (!nuevoDni.isEmpty()) {
                p.setDni(nuevoDni);
            }

            System.out.print("Nueva fecha de nacimiento (yyyy-MM-dd, actual: " + p.getFechaNacimiento() +
                    ", Enter para dejar igual): ");
            String fechaStr = scanner.nextLine().trim();
            if (!fechaStr.isEmpty()) {
                try {
                    p.setFechaNacimiento(LocalDate.parse(fechaStr));
                } catch (DateTimeParseException e) {
                    System.out.println("Formato inválido. Se mantiene la fecha anterior.");
                }
            }

            System.out.println("¿Desea cambiar el grupo sanguíneo? (s/N): ");
            String resp = scanner.nextLine().trim().toLowerCase();
            if (resp.equals("s")) {
                p.setGrupoSanguineo(leerGrupoSanguineo());
            }

            pacienteService.actualizar(p);
            System.out.println("Paciente actualizado correctamente.");


        } catch (Exception e) {
            System.err.println("Error al actualizar paciente: " + e.getMessage());
        }
    }

    // OPCIÓN 5: Eliminar Paciente
    public void eliminarPaciente() {
        try {
            System.out.println("\n--- Eliminar Paciente ---");
            System.out.print("ID del paciente a eliminar: ");
            Long id = leerLong();

            pacienteService.eliminar(id);
            System.out.println("Paciente eliminado exitosamente (borrado lógico si está implementado así).");
        } catch (Exception e) {
            System.err.println("Error al eliminar paciente: " + e.getMessage());
        }
    }

    // =========================================================
    //  HISTORIAS CLÍNICAS
    // =========================================================

    // OPCIÓN 6: Listar s Historias Clínica
    public void listarHistoriasClinicas() {
        try {
            System.out.println("\n--- Historia Clínica del paciente seleccionado ---");

            if (pacienteSeleccionado == null) {
                System.out.println("No hay ningún paciente seleccionado. Seleccione uno primero con la opción 'Ver paciente por DNI'.");
                return;
            }


            HistoriaClinica h = pacienteSeleccionado.getHistoriaClinica();

            // Si por algún motivo no está cargada pero tenemos id, la buscamos en la base
            if (h == null || h.getId() <= 0) {
                System.out.println("El paciente seleccionado no tiene historia clínica asociada o el ID es inválido.");
                return;
            }



            if (h == null) {
                System.out.println("No se encontró historia clínica para el paciente seleccionado.");
                return;
            }

            // Mostramos SOLO la historia clínica de ese paciente
            System.out.println("Paciente: " + pacienteSeleccionado.getNombre() + " " + pacienteSeleccionado.getApellido());
            System.out.println("DNI: " + pacienteSeleccionado.getDni());
            System.out.println("ID Historia Clínica: " + h.getId());
            System.out.println("Nro Historia: " + h.getNroHistoria());
            System.out.println("Grupo sanguíneo: " + h.getGrupoSanguineo());
            System.out.println("Antecedentes: " + h.getAntecedentes());
            System.out.println("Medicación actual: " + h.getMedicacionActual());
            System.out.println("Observaciones: " + h.getObservaciones());
            System.out.println("Eliminado: " + h.isEliminado());

        } catch (Exception e) {
            System.err.println("Error al listar historia clínica: " + e.getMessage());
        }
    }

    // OPCIÓN 7: Actualizar Historia Clínica
    public void actualizarHistoriaClinica() {
        try {
            System.out.println("\n--- Actualizar Historia Clínica ---");

            if (pacienteSeleccionado == null) {
                System.out.println("Debe seleccionar un paciente primero.");
                return;
            }

            HistoriaClinica h = pacienteSeleccionado.getHistoriaClinica();
            if (h == null || h.getId() <= 0) {
                System.out.println("El paciente seleccionado no tiene historia clínica.");
                return;
            }

            Long idHistoria = h.getId();

            // Cargamos la historia actual desde la BD
            HistoriaClinica historiaActual = historiaService.getById(idHistoria);
            if (historiaActual == null) {
                System.out.println("No se encontró la historia clínica.");
                return;
            }

            // Mostrar valores actuales
            System.out.println("Nro Historia: " + historiaActual.getNroHistoria());
            System.out.println("Grupo sanguíneo: " + historiaActual.getGrupoSanguineo());
            System.out.println("Antecedentes: " + historiaActual.getAntecedentes());
            System.out.println("Medicación actual: " + historiaActual.getMedicacionActual());
            System.out.println("Observaciones: " + historiaActual.getObservaciones());

            // Pedir nuevos valores
            System.out.print("Nuevo antecedentes (Enter para dejar igual): ");
            String nuevosAnt = scanner.nextLine().trim();
            if (!nuevosAnt.isEmpty()) historiaActual.setAntecedentes(nuevosAnt);

            System.out.print("Nueva medicación (Enter para dejar igual): ");
            String nuevaMed = scanner.nextLine().trim();
            if (!nuevaMed.isEmpty()) historiaActual.setMedicacionActual(nuevaMed);

            System.out.print("Nuevas observaciones (Enter para dejar igual): ");
            String nuevasObs = scanner.nextLine().trim();
            if (!nuevasObs.isEmpty()) historiaActual.setObservaciones(nuevasObs);

            // Guardar en BD
            historiaService.actualizar(historiaActual);

            // Refrescar sino no se actualiza

            HistoriaClinica historiaRefrescada = historiaService.getById(historiaActual.getId());
            pacienteSeleccionado.setHistoriaClinica(historiaRefrescada);

            System.out.println("Historia clínica actualizada correctamente.");

        } catch (Exception e) {
            System.err.println("Error al actualizar historia clínica: " + e.getMessage());
        }
    }

    // OPCIÓN 8: Eliminar Historia Clínica
//    public void eliminarHistoriaClinica() {
//        try {
//            System.out.println("\n--- Eliminar Historia Clínica ---");
//
//
//            if (pacienteSeleccionado == null) {
//                System.out.println("No hay un paciente seleccionado. Seleccione uno primero (opción 'Ver paciente por DNI').");
//                return;
//            }
//
//
//            HistoriaClinica h = pacienteSeleccionado.getHistoriaClinica();
//            if (h == null || h.getId() <= 0) {
//                System.out.println("El paciente seleccionado no tiene historia clínica asociada.");
//                return;
//            }
//
//            Long id = h.getId();
//
//
//            System.out.print("¿Está seguro que desea eliminar esta historia clínica? (s/N): ");
//            String confirm = scanner.nextLine().trim().toLowerCase();
//
//            if (!confirm.equals("s")) {
//                System.out.println("Operación cancelada.");
//                return;
//            }
//
//
//            historiaService.eliminar(id);
//            System.out.println("Historia clínica eliminada correctamente.");
//
//
//            pacienteSeleccionado.setHistoriaClinica(null);
//
//        } catch (Exception e) {
//            System.err.println("Error al eliminar historia clínica: " + e.getMessage());
//        }
//    }
//

    // =========================================================
    //  MÉTODOS AUXILIARES
    // =========================================================

    private Long leerLong() {
        while (!scanner.hasNextLong()) {
            System.out.print("Ingrese un número válido: ");
            scanner.nextLine(); // descartar
        }
        long valor = scanner.nextLong();
        scanner.nextLine(); // limpiar buffer
        return valor;
    }

    private GrupoSanguineo leerGrupoSanguineo() {
        System.out.println("\nSeleccione grupo sanguíneo:");
        System.out.println("1. A+");
        System.out.println("2. A-");
        System.out.println("3. B+");
        System.out.println("4. B-");
        System.out.println("5. AB+");
        System.out.println("6. AB-");
        System.out.println("7. O+");
        System.out.println("8. O-");
        System.out.print("Opción: ");

        while (true) {
            int opcion;
            while (!scanner.hasNextInt()) {
                System.out.print("Ingrese un número válido (1-8): ");
                scanner.nextLine();
            }
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar

            switch (opcion) {
                case 1: return GrupoSanguineo.A_POS;
                case 2: return GrupoSanguineo.A_NEG;
                case 3: return GrupoSanguineo.B_POS;
                case 4: return GrupoSanguineo.B_NEG;
                case 5: return GrupoSanguineo.AB_POS;
                case 6: return GrupoSanguineo.AB_NEG;
                case 7: return GrupoSanguineo.O_POS;
                case 8: return GrupoSanguineo.O_NEG;
                default:
                    System.out.print("Opción inválida. Ingrese un número (1-8): ");
            }
        }
    }
}
