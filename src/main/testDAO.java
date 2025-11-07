package main;
import DAO.PacienteDAO;
import Models.Paciente;
import Models.HistoriaClinica;


import java.time.LocalDate;
import java.util.List;

public class testDAO {

    private static Paciente nuevoPaciente(String dni) {
        Paciente p = new Paciente();
        p.setNombre("Juan");
        p.setApellido("Pérez");
        p.setDni(dni);
        p.setFechaNacimiento(LocalDate.of(1990, 5, 20));
        // si no tenés HC creada, dejá null y el DAO setea NULL en DB
        p.setHistoriaClinica(null);
        p.setEliminado(false);
        return p;
    }


    public static void main(String[] args) {

            PacienteDAO dao = new PacienteDAO();



            try {
                System.out.println("=== 1) INSERTAR ===");
                Paciente p = nuevoPaciente("dni-571-000");
                dao.insertar(p);
                System.out.println("Insertado con id: " + p.getId());
                System.out.println(p.toString());





            }catch (Exception e) {
                System.err.println("ERROR en prueba manual: " + e.getMessage());
                e.printStackTrace();
            }






    }
}
