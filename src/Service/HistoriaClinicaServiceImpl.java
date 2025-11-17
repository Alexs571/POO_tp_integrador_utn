package Service;

import DAO.HistoriaClinicaDAO;
import Models.HistoriaClinica;

import java.util.List;

public class HistoriaClinicaServiceImpl implements Service<HistoriaClinica> {

    private final HistoriaClinicaDAO historiaClinicaDAO;

    public HistoriaClinicaServiceImpl(HistoriaClinicaDAO historiaClinicaDAO) {
        if (historiaClinicaDAO == null) {
            throw new IllegalArgumentException("HistoriaClinicaDAO no puede ser null");
        }
        this.historiaClinicaDAO = historiaClinicaDAO;
    }

    /** Inserta una nueva historia clínica. */
    @Override
    public void insertar(HistoriaClinica historia) throws Exception {
        validateHistoria(historia);
        historiaClinicaDAO.insertar(historia);
    }

    private void validateHistoria(HistoriaClinica historia) {
        if (historia == null) {
            throw new IllegalArgumentException("La historia no puede ser null");
        }
        if (historia.getNroHistoria() == null || historia.getNroHistoria().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de historia clínica es obligatorio");
        }
        if (historia.getGrupoSanguineo() == null) {
            throw new IllegalArgumentException("El grupo sanguíneo no puede estar vacío");
        }
    }

    /** Actualiza una historia clínica existente. */
    @Override
    public void actualizar(HistoriaClinica historia) throws Exception {
        validateHistoria(historia);
        if (historia.getId() <= 0) {
            throw new IllegalArgumentException("El ID de la historia clínica debe ser mayor a 0");
        }
        historiaClinicaDAO.actualizar(historia);
    }

    /** Elimina (soft delete) una historia clínica. */
    @Override
    public void eliminar(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        historiaClinicaDAO.eliminar(id);
    }

    /** Obtiene una historia clínica por ID. */
    @Override
    public HistoriaClinica getById(Long id) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede estar vacío");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }

        HistoriaClinica historia = historiaClinicaDAO.getById(id);

        if (historia == null) {
            throw new Exception("No se encontró ninguna historia clínica con el ID " + id);
        }

        return historia;
    }

    @Override
    public List<HistoriaClinica> getAll() throws Exception {
        List<HistoriaClinica> lista = historiaClinicaDAO.getAll();

        if (lista == null || lista.isEmpty()) {
            throw new Exception("No hay historias clínicas cargadas en el sistema");
        }

        return lista;
    }
}
