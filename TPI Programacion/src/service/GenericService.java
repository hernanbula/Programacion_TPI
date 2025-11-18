package service;

/**
@author Hernan Cóceres
@author Claudio Rodriguez
@author Hernan E.Bula
@author Gaston Alberto Cejas
 */

import java.util.List;

/**
 * Interfaz genérica para operaciones CRUD básicas de servicios del sistema.
 * Define operaciones estándar de inserción, actualización, eliminación y consulta.
 * 
 * @param <T> Tipo de entidad que manejará el servicio
 */
public interface GenericService<T> {
    
    /**
     * Inserta una nueva entidad en el sistema.
     * @param entidad Entidad a insertar
     * @throws IllegalArgumentException Si la entidad no pasa las validaciones
     * @throws Exception Si ocurre un error durante la inserción
     */
    void insertar(T entidad) throws Exception;
    
    /**
     * Actualiza una entidad existente en el sistema.
     * @param entidad Entidad con datos actualizados
     * @throws IllegalArgumentException Si la entidad no existe o no pasa validaciones
     * @throws Exception Si ocurre un error durante la actualización
     */
    void actualizar(T entidad) throws Exception;
    
    /**
     * Elimina una entidad del sistema mediante soft delete.
     * @param id ID de la entidad a eliminar
     * @throws IllegalArgumentException Si no existe entidad con el ID proporcionado
     * @throws Exception Si ocurre un error durante la eliminación
     */
    void eliminar(long id) throws Exception;
    
    /**
     * Obtiene una entidad por su ID.
     * @param id ID de la entidad a buscar
     * @return Entidad encontrada o null si no existe
     * @throws Exception Si ocurre un error durante la consulta
     */
    T getById(long id) throws Exception;
    
    /**
     * Obtiene todas las entidades activas del sistema.
     * @return Lista de entidades activas, lista vacía si no hay entidades
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<T> getAll() throws Exception;
}