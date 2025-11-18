package model;

/**
 * @author Hernan Cóceres
 * @author Claudio Rodriguez  
 * @author Hernan E.Bula
 * @author Gaston Alberto Cejas
 */

/**
 * Clase base abstracta para entidades del sistema.
 * Proporciona propiedades comunes: identificador y bandera de eliminación lógica.
 */
public abstract class Base {

    // =========================================
    // ATRIBUTOS
    // =========================================
    
    private long id;
    private boolean eliminado;
    
    // =========================================
    // CONSTRUCTORES
    // =========================================

    /**
     * Constructor por defecto. Inicializa entidad como no eliminada.
     */
    protected Base() {
        this.eliminado = false;
    }
    
    /**
     * Constructor con parámetros de identificación y estado.
     * 
     * @param id Identificador único de la entidad
     * @param eliminado Estado de eliminación lógica
     */
    protected Base(long id, boolean eliminado) {
        this.id = id;
        this.eliminado = eliminado;
    }

    // =========================================
    // GETTERS Y SETTERS
    // =========================================
    
    /**
     * Obtiene el identificador único de la entidad.
     * 
     * @return ID de la entidad, 0 si no ha sido persistida
     */
    public long getId() {
        return id;
    }

    /**
     * Establece el identificador único de la entidad.
     * 
     * @param id Nuevo ID de la entidad
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Verifica si la entidad está marcada como eliminada lógicamente.
     * 
     * @return true si está eliminada, false si está activa
     */
    public boolean isEliminado() {
        return eliminado;
    }

    /**
     * Establece el estado de eliminación lógica de la entidad.
     * 
     * @param eliminado true para marcar como eliminada, false para activar
     */
    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
}