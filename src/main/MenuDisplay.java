package main;

public class MenuDisplay {

    public static void mostrarMenuPrincipal() {
        mostrarMenuPrincipal(false);
    }

    public static void mostrarMenuPrincipal(boolean mostrarOpcionesHistoria) {
        System.out.println("\n========= MENU CLÍNICA =========");

        System.out.println("PACIENTES");
        System.out.println("1. Crear paciente (+ historia clínica)");
        System.out.println("2. Ver paciente (seleccionar por DNI)");
        System.out.println("3. Listar pacientes");
        System.out.println("4. Actualizar paciente");
        System.out.println("5. Eliminar paciente");

        if (mostrarOpcionesHistoria) {
            System.out.println("\nHISTORIA CLÍNICA (paciente seleccionado)");
            System.out.println("6. Lista historia clínica");
            System.out.println("7. Actualizar historia clínica");

            System.out.println("9. Volver atrás (deseleccionar paciente)");
        } else {
            System.out.println("\n[Seleccione un paciente con la opción 2 para ver las opciones de historia clínica]");
        }

        System.out.println("0. Salir");
        System.out.print("Ingrese una opción: ");
    }
}
