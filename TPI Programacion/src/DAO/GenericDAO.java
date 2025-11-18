package DAO;

/**
@author Hernan Cóceres
@author Claudio Rodriguez
@author Hernan E.Bula
@author Gaston Alberto Cejas
 */

import java.util.List;

/**
 * Interfaz genérica para operaciones CRUD básicas de acceso a datos.
 * Define operaciones estándar de persistencia para entidades del sistema.
 * 
 * @param <T> Tipo de entidad que manejará el DAO
 */
public interface GenericDAO<T> {

    // =========================================
    // MÉTODOS CRUD
    // =========================================

    /**
     * Inserta una nueva entidad en la fuente de datos.
     * @param entidad Entidad a insertar
     * @throws Exception Si ocurre error al acceder a la fuente de datos
     */
    void insertar(T entidad) throws Exception;
    
    /**
     * Actualiza una entidad existente en la fuente de datos.
     * @param entidad Entidad a actualizar
     * @throws Exception Si ocurre error al acceder a la fuente de datos
     */
    void actualizar(T entidad) throws Exception;
    
    /**
     * Elimina una entidad de la fuente de datos.
     * @param id Identificador de la entidad a eliminar
     * @throws Exception Si ocurre error al acceder a la fuente de datos
     */
    void eliminar(long id) throws Exception;
    
    /**
     * Obtiene una entidad por su identificador.
     * @param id Identificador de la entidad
     * @return Entidad encontrada o null si no existe
     * @throws Exception Si ocurre error al acceder a la fuente de datos
     */
    T getById(long id) throws Exception;
    
    /**
     * Obtiene todas las entidades de la fuente de datos.
     * @return Lista de entidades
     * @throws Exception Si ocurre error al acceder a la fuente de datos
     */
    List<T> getAll() throws Exception;
}