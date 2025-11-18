package model;

import java.time.LocalDate;

/**
@author Hernan Cóceres
@author Claudio Rodriguez
@author Hernan E.Bula
@author Gaston Alberto Cejas
 */

/**
 * Representa un código de barras con tipo, valor, fecha de asignación y observaciones.
 * Extiende Base para heredar ID y funcionalidad de eliminación lógica.
 * Parte de una relación unidireccional 1 a 1 con Producto (solo Producto referencia a CodigoBarras).
 */
public class CodigoBarras extends Base {
    
    // =========================================
    // DECLARACIÓN DE CLASE Y ATRIBUTOS
    // =========================================
    
    private EnumTipo tipo;
    private String valor;
    private LocalDate fechaAsignacion;
    private String observaciones;

    // =========================================
    // CONSTRUCTORES
    // =========================================

    /**
     * Constructor completo para crear un código de barras con todos los atributos.
     * @param id ID único del código
     * @param eliminado Estado de eliminación lógica
     * @param tipo Tipo de código (EAN13, EAN8, UPC)
     * @param valor Valor numérico del código
     * @param fechaAsignacion Fecha de asignación
     * @param observaciones Observaciones adicionales
     */
    public CodigoBarras(long id, boolean eliminado, EnumTipo tipo, String valor, LocalDate fechaAsignacion, String observaciones) {
        super(id, eliminado);
        this.tipo = tipo;
        this.valor = valor;
        this.fechaAsignacion = fechaAsignacion;
        this.observaciones = observaciones;
    }
    
    /**
     * Constructor vacío para inicialización sin parámetros.
     */
    public CodigoBarras() {
    }
    
    // =========================================
    // MÉTODOS GETTER
    // =========================================
    
    /**
     * @return Tipo del código de barras
     */
    public EnumTipo getTipo() {
        return tipo;
    }

    /**
     * @return Valor numérico del código
     */
    public String getValor() {
        return valor;
    }

    /**
     * @return Fecha de asignación del código
     */
    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    /**
     * @return Observaciones adicionales
     */
    public String getObservaciones() {
        return observaciones;
    }

    // =========================================
    // MÉTODOS SETTER
    // =========================================

    /**
     * Establece el tipo de código de barras.
     * @param tipo Tipo de código (EAN13, EAN8, UPC)
     */
    public void setTipo(EnumTipo tipo) {
        this.tipo = tipo;
    }
    
    /**
     * Establece el valor numérico del código.
     * @param valor Valor del código de barras
     */
    public void setValor(String valor) {
        this.valor = valor;
    }
    
    /**
     * Establece la fecha de asignación.
     * @param fechaAsignacion Fecha de asignación
     */
    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    /**
     * Establece observaciones adicionales.
     * @param observaciones Texto de observaciones
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // =========================================
    // MÉTODOS SOBREESCRITOS
    // =========================================

    /**
     * @return Representación en texto del código de barras
     */
    @Override
    public String toString() {
        String obsTexto = (observaciones != null && !observaciones.trim().isEmpty()) 
            ? observaciones 
            : "(sin observaciones)";
        return "\n---\nCódigo de barras:\n - ID: " + getId() + "\n - Tipo: " + tipo + "\n - Valor: " + valor + "\n - Fecha de asignacion: " + fechaAsignacion + "\n - Observaciones: " + obsTexto;
    }
    
    /**
     * Compara este código con otro objeto por ID, valor y tipo.
     * @param obj Objeto a comparar
     * @return true si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        CodigoBarras codigo = (CodigoBarras) obj;
        
        if (this.getId() > 0 && codigo.getId() > 0) {
            return this.getId() == codigo.getId();
        }
        
        if (valor == null) {
            if (codigo.valor != null) {
                return false;
            }
        } else if (!valor.equals(codigo.valor)) {
            return false;
        }
        
        if (tipo != codigo.tipo) {
            return false;
        }
        
        return true;
    }

    /**
     * @return Código hash basado en ID, valor y tipo
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        
        if (this.getId() > 0) {
            return Long.hashCode(this.getId());
        }
        
        result = prime * result + ((valor == null) ? 0 : valor.hashCode());
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
        
        return result;
    }
}