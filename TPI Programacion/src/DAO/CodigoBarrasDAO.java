package DAO;

/**
@author Hernan Cóceres
@author Claudio Rodriguez
@author Hernan E.Bula
@author Gaston Alberto Cejas
 */

import config.DatabaseConnection;
import model.CodigoBarras;
import model.EnumTipo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de DAO para operaciones CRUD de códigos de barras.
 * Gestra inserción, actualización y consultas de códigos de barras.
 */
public class CodigoBarrasDAO implements GenericDAO<CodigoBarras> {

    // =========================================
    // MÉTODOS DE INSERCIÓN
    // =========================================

    /**
     * Inserta código de barras usando conexión automática.
     * 
     * @param entidad Código de barras a insertar
     * @throws Exception Si ocurre error de base de datos
     */
    @Override
    public void insertar(CodigoBarras entidad) throws Exception {
        insertar(entidad, null);
    }
    
    /**
     * Inserta código de barras usando conexión existente o nueva.
     * 
     * @param entidad Código de barras a insertar
     * @param conn Conexión existente o null para nueva
     * @throws Exception Si ocurre error en la ejecución SQL
     */
    public void insertar(CodigoBarras entidad, Connection conn) throws Exception {
        String sql = "INSERT INTO codigo_barras (tipo, valor, fecha_asignacion, observaciones) VALUES (?, ?, ?, ?)";
        boolean usarConexionExterna = (conn != null);
        
        if (!usarConexionExterna) {
            conn = DatabaseConnection.getConnection();
        }
        
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entidad.getTipo().name());
            stmt.setString(2, entidad.getValor());
            stmt.setDate(3, Date.valueOf(entidad.getFechaAsignacion()));
            
            String obsValue = entidad.getObservaciones();
            if (obsValue != null && !obsValue.trim().isEmpty()) {
                stmt.setString(4, obsValue.trim());
            } else {
                stmt.setNull(4, Types.VARCHAR);
            }

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    entidad.setId(rs.getLong(1));
                }
            }
            
            if (!usarConexionExterna) {
                conn.commit();
            }
        } finally {
            if (!usarConexionExterna && conn != null) {
                conn.close();
            }
        }
    }

    // =========================================
    // MÉTODOS DE ACTUALIZACIÓN
    // =========================================

    /**
     * Actualiza código de barras usando conexión automática.
     * 
     * @param entidad Código de barras con datos actualizados
     * @throws Exception Si ocurre error de base de datos
     */
    @Override
    public void actualizar(CodigoBarras entidad) throws Exception {
        actualizar(entidad, null);
    }
    
    /**
     * Actualiza código de barras usando conexión existente o nueva.
     * 
     * @param entidad Código de barras a actualizar
     * @param conn Conexión existente o null para nueva
     * @throws Exception Si ocurre error en la ejecución SQL
     */
    public void actualizar(CodigoBarras entidad, Connection conn) throws Exception {
        String sql = "UPDATE codigo_barras SET tipo = ?, valor = ?, fecha_asignacion = ?, observaciones = ? WHERE id = ?";
        boolean usarConexionExterna = (conn != null);
        
        if (!usarConexionExterna) {
            conn = DatabaseConnection.getConnection();
        }
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entidad.getTipo().name());
            stmt.setString(2, entidad.getValor());
            stmt.setDate(3, Date.valueOf(entidad.getFechaAsignacion()));
            
            if (entidad.getObservaciones() != null && !entidad.getObservaciones().trim().isEmpty()) {
                stmt.setString(4, entidad.getObservaciones());
            } else {
                stmt.setNull(4, Types.VARCHAR);
            }
            
            stmt.setLong(5, entidad.getId());
            stmt.executeUpdate();
            
            if (!usarConexionExterna) {
                conn.commit();
            }
        } finally {
            if (!usarConexionExterna && conn != null) {
                conn.close();
            }
        }
    }

    // =========================================
    // MÉTODOS DE ELIMINACIÓN Y RECUPERACIÓN
    // =========================================

    /**
     * Eliminación lógica de código de barras usando conexión automática.
     * 
     * @param id ID del código de barras a eliminar
     * @throws Exception Si ocurre error de base de datos
     */
    @Override
    public void eliminar(long id) throws Exception {
        eliminar(id, null);
    }
    
    /**
     * Eliminación lógica de código de barras usando conexión existente o nueva.
     * 
     * @param id ID del código de barras a eliminar
     * @param conn Conexión existente o null para nueva
     * @throws Exception Si ocurre error en la ejecución SQL
     */
    public void eliminar(long id, Connection conn) throws Exception {
        String sql = "UPDATE codigo_barras SET eliminado = true WHERE id = ? AND eliminado = false";
        boolean usarConexionExterna = (conn != null);
        
        if (!usarConexionExterna) {
            conn = DatabaseConnection.getConnection();
        }
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            
            if (!usarConexionExterna) {
                conn.commit();
            }
        } finally {
            if (!usarConexionExterna && conn != null) {
                conn.close();
            }
        }
    }
    
    /**
     * Recupera código de barras eliminado usando conexión automática.
     * 
     * @param id ID del código de barras a recuperar
     * @throws Exception Si ocurre error de base de datos
     */
    public void recuperar(long id) throws Exception {
        recuperar(id, null);
    }

    /**
     * Recupera código de barras eliminado usando conexión existente o nueva.
     * 
     * @param id ID del código de barras a recuperar
     * @param conn Conexión existente o null para nueva
     * @throws Exception Si ocurre error en la ejecución SQL
     */
    public void recuperar(long id, Connection conn) throws Exception {
        String sql = "UPDATE codigo_barras SET eliminado = false WHERE id = ? AND eliminado = true";
        boolean usarConexionExterna = (conn != null);

        if (!usarConexionExterna) {
            conn = DatabaseConnection.getConnection();
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();

            if (!usarConexionExterna) {
                conn.commit();
            }
        } finally {
            if (!usarConexionExterna && conn != null) {
                conn.close();
            }
        }
    }

    // =========================================
    // MÉTODOS DE CONSULTA
    // =========================================

    /**
     * Obtiene código de barras por ID.
     * 
     * @param id ID del código de barras a buscar
     * @return Código de barras encontrado o null si no existe
     * @throws Exception Si ocurre error de base de datos
     */
    @Override
    public CodigoBarras getById(long id) throws Exception {
        String sql = "SELECT * FROM codigo_barras WHERE id = ? AND eliminado = false";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    /**
     * Obtiene todos los códigos de barras activos.
     * 
     * @return Lista de códigos de barras no eliminados
     * @throws Exception Si ocurre error de base de datos
     */
    @Override
    public List<CodigoBarras> getAll() throws Exception {
        List<CodigoBarras> lista = new ArrayList<>();
        String sql = "SELECT * FROM codigo_barras WHERE eliminado = false";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        }
        return lista;
    }

    /**
     * Busca código de barras por valor usando conexión automática.
     * 
     * @param valor Valor exacto del código de barras
     * @return Código de barras encontrado o null si no existe
     * @throws Exception Si ocurre error de base de datos
     */
    public CodigoBarras getByValor(String valor) throws Exception {
        return getByValor(valor, null);
    }
    
    /**
     * Busca código de barras por valor usando conexión existente o nueva.
     * 
     * @param valor Valor exacto del código de barras
     * @param conn Conexión existente o null para nueva
     * @return Código de barras encontrado o null si no existe
     * @throws Exception Si ocurre error en la consulta SQL
     */
    public CodigoBarras getByValor(String valor, Connection conn) throws Exception {
        String sql = "SELECT * FROM codigo_barras WHERE valor = ? AND eliminado = false";
        boolean usarConexionExterna = (conn != null);
        
        if (!usarConexionExterna) {
            conn = DatabaseConnection.getConnection();
        }
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, valor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } finally {
            if (!usarConexionExterna && conn != null) {
                conn.close();
            }
        }
        return null;
    }

    // =========================================
    // MÉTODOS AUXILIARES
    // =========================================

    /**
     * Mapea ResultSet a objeto CodigoBarras.
     * 
     * @param rs ResultSet posicionado en fila válida
     * @return Código de barras mapeado
     * @throws SQLException Si hay error al leer datos del ResultSet
     */
    private CodigoBarras mapRow(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        boolean eliminado = rs.getBoolean("eliminado");

        String tipoStr = rs.getString("tipo");
        EnumTipo tipo = (tipoStr != null && !tipoStr.trim().isEmpty()) 
                ? EnumTipo.valueOf(tipoStr.trim().toUpperCase())
                : null;

        String valor = rs.getString("valor");

        java.sql.Date sqlDate = rs.getDate("fecha_asignacion");
        LocalDate fecha = (sqlDate != null) ? sqlDate.toLocalDate() : null;

        String observaciones = rs.getString("observaciones");

        return new CodigoBarras(id, eliminado, tipo, valor, fecha, observaciones);
    }
}