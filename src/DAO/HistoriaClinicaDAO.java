package DAO;

import Models.GrupoSanguineo;
import Models.HistoriaClinica;
import config.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoriaClinicaDAO implements DAO<HistoriaClinica> {

    private static final String INSERT_SQL = """
        INSERT INTO clinica2.historia_clinica
            (nro_historia, eliminado, grupo_sanguineo, antecedentes, medicacion_actual, observaciones)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

    private static final String UPDATE_SQL = """
        UPDATE clinica2.historia_clinica
           SET eliminado = ?, grupo_sanguineo = ?, antecedentes = ?, 
               medicacion_actual = ?, observaciones = ?
         WHERE id = ?
        """;

    // DELETE lógico
    private static final String SOFT_DELETE_SQL = """
        UPDATE clinica2.historia_clinica SET eliminado = 1 WHERE id = ?
        """;

    private static final String SELECT_BY_ID_SQL = """
        SELECT id, nro_historia, eliminado, grupo_sanguineo, antecedentes, medicacion_actual, observaciones
          FROM clinica2.historia_clinica
         WHERE id = ?
        """;

    // Solo activos (eliminado = 0)
    private static final String SELECT_ALL_SQL = """
        SELECT id, nro_historia, eliminado, grupo_sanguineo, antecedentes, medicacion_actual, observaciones
          FROM clinica2.historia_clinica
         WHERE eliminado = 0
         ORDER BY nro_historia
        """;

    private static final String SELECT_ALL_SQL_ID = """
        SELECT id, nro_historia, eliminado, grupo_sanguineo, antecedentes, medicacion_actual, observaciones
          FROM clinica2.historia_clinica
         WHERE eliminado = 0
         ORDER BY id
        """;

    // =========================================================
    // CREATE
    // =========================================================

    @Override
    public void insertar(HistoriaClinica h) throws Exception {
        if (h == null) throw new IllegalArgumentException("historia clínica no puede ser null");

        try (Connection conn = DataBaseConnection.getConnection()) {
            System.out.println("Conectado a: " + conn.getCatalog());
            insertarInterno(h, conn);
        }
    }

    @Override
    public void insertTx(HistoriaClinica h, Connection conn) throws Exception {
        if (h == null) throw new IllegalArgumentException("historia clínica no puede ser null");
        if (conn == null) throw new IllegalArgumentException("conn no puede ser null");
        insertarInterno(h, conn);
    }

    // Método auxiliar para inserción de datos
    private void insertarInterno(HistoriaClinica h, Connection conn) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, h.getNroHistoria());
            ps.setBoolean(2, h.isEliminado());
            ps.setString(3, h.getGrupoSanguineo() != null ? h.getGrupoSanguineo().name() : null);
            ps.setString(4, h.getAntecedentes());
            ps.setString(5, h.getMedicacionActual());
            ps.setString(6, h.getObservaciones());

            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("No se insertó una historia clínica");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) h.setId(rs.getLong(1));
            }
        }
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @Override
    public void actualizar(HistoriaClinica h) throws Exception {
        if (h == null) throw new IllegalArgumentException("historia clínica no puede ser null");
        if (h.getId() <= 0) throw new IllegalArgumentException("id inválido para actualizar");

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setBoolean(1, h.isEliminado());
            ps.setString(2, h.getGrupoSanguineo() != null ? h.getGrupoSanguineo().name() : null);
            ps.setString(3, h.getAntecedentes());
            ps.setString(4, h.getMedicacionActual());
            ps.setString(5, h.getObservaciones());
            ps.setLong(6, h.getId());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("No se actualizó ninguna historia clínica (id=" + h.getId() + ")");
            }
        }
    }

    // =========================================================
    // DELETE lógico
    // =========================================================

    @Override
    public void eliminar(long id) throws Exception {
        if (id <= 0) throw new IllegalArgumentException("id inválido");
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SOFT_DELETE_SQL)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) throw new SQLException("No se eliminó ninguna historia clínica (id=" + id + ")");
        }
    }

    // =========================================================
    // READ
    // =========================================================

    @Override
    public HistoriaClinica getById(long id) throws Exception {
        if (id <= 0) throw new IllegalArgumentException("id inválido");
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapRow(rs) : null;
            }
        }
    }

    @Override
    public List<HistoriaClinica> getAll() throws Exception {
        List<HistoriaClinica> list = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    public List<HistoriaClinica> getAllById() throws Exception {
        List<HistoriaClinica> list = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL_ID);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    // =========================
    // Mapper
    // =========================
    private HistoriaClinica mapRow(ResultSet rs) throws SQLException {
        HistoriaClinica h = new HistoriaClinica();
        h.setId(rs.getLong("id"));
        h.setNroHistoria(rs.getString("nro_historia"));
        h.setEliminado(rs.getBoolean("eliminado"));

        String grupo = rs.getString("grupo_sanguineo");
        if (grupo != null) {
            h.setGrupoSanguineo(GrupoSanguineo.valueOf(grupo));
        }

        h.setAntecedentes(rs.getString("antecedentes"));
        h.setMedicacionActual(rs.getString("medicacion_actual"));
        h.setObservaciones(rs.getString("observaciones"));

        return h;
    }
}
