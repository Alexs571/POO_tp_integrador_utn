package Service;

import java.sql.Connection;
import java.util.List;

import DAO.HistoriaClinicaDAO;
import DAO.PacienteDAO;
import Models.HistoriaClinica;
import Models.Paciente;
import config.DataBaseConnection;


public class PacienteServiceImpl implements Service<Paciente> {


    private final PacienteDAO pacienteDAO;
    private final HistoriaClinicaDAO historiaClinicaDAO;


    public PacienteServiceImpl(PacienteDAO pacienteDAO, HistoriaClinicaDAO historiaClinicaDAO) {
        if (pacienteDAO == null) throw new IllegalArgumentException("PacienteDAO no puede ser null");
        if (historiaClinicaDAO == null) throw new IllegalArgumentException("HistoriaClinicaDAO no puede ser null");
        this.pacienteDAO = pacienteDAO;
        this.historiaClinicaDAO = historiaClinicaDAO;
    }

    /** Inserta un nuevo paciente. */
    @Override
    public void insertar(Paciente paciente) throws Exception {
        validatePaciente(paciente);

        try (Connection conn = DataBaseConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);

                // 1) Crear historia clínica
                HistoriaClinica hc = new HistoriaClinica();
                hc.setEliminado(false);
                hc.setNroHistoria(generarNumeroHistoria(paciente)); // debe ser NOT NULL y UNIQUE
                hc.setGrupoSanguineo(paciente.getGrupoSanguineo());
                hc.setAntecedentes("");
                hc.setMedicacionActual("");
                hc.setObservaciones("");


                historiaClinicaDAO.insertTx(hc, conn);


                paciente.setHistoriaClinica(hc);
                paciente.setEliminado(false);


                pacienteDAO.insertTx(paciente, conn);

                conn.commit();
                System.out.println("Paciente y historia clínica creados correctamente.");

            } catch (Exception e) {
                conn.rollback();
                throw new RuntimeException("Error en la transacción al crear paciente + historia clínica", e);
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }


    @Override
    public void actualizar(Paciente paciente) throws Exception {
        validatePaciente(paciente);
        if (paciente.getId() <= 0) {
            throw new IllegalArgumentException("El ID del paciente debe ser mayor a 0");
        }
        pacienteDAO.actualizar(paciente);
    }


    @Override
    public void eliminar(Long id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        pacienteDAO.eliminar(id);
    }


    @Override
    public Paciente getById(Long id) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("El paciente no puede ser null");
        }

        if(id <= 0){
            throw new IllegalArgumentException("El ID  de paciente debe ser mayor a 0");
        }

        Paciente paciente = pacienteDAO.getById(id);

        if (paciente == null) {
            throw new Exception("No existe ningún paciente con el ID " + id);
        }


        return pacienteDAO.getById(id);
    }


    @Override
    public List<Paciente> getAll() throws Exception {

        List<Paciente> lista = pacienteDAO.getAll();

        if( lista == null || lista.isEmpty()){
            throw new Exception("No hay pacientes cargados en el sistema");
        }


        return pacienteDAO.getAll();
    }


    private void validatePaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("El paciente no puede ser null");
        }
        if (paciente.getNombre() == null || paciente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del paciente es obligatorio");
        }
        if (paciente.getDni() == null || paciente.getDni().trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI del paciente es obligatorio");
        }

    }
    public Paciente buscarPorDni(String dni) throws Exception {
        if (dni == null || dni.trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI del paciente es obligatorio");
        }

        return pacienteDAO.buscarPorDni(dni.trim());
    }


    private String generarNumeroHistoria(Paciente p) {
        //Dara formato al nro_hc  'HC-12345678-123'
        String millis = String.valueOf(System.currentTimeMillis());
        String sufijo = millis.substring(millis.length() - 4);
        return "HC-" + p.getDni() + "-" + sufijo;
    }
}
