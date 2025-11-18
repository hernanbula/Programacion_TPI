package model;

/**
@author Hernan Cóceres
@author Claudio Rodriguez
@author Hernan E.Bula
@author Gaston Alberto Cejas
 */

/**
 * Representa un producto con nombre, marca, precio, peso, stock, categoría y código de barras.
 * Extiende Base para heredar ID y funcionalidad de eliminación lógica.
 */
public class Producto extends Base {
    
    // =========================================
    // DECLARACIÓN DE CLASE Y ATRIBUTOS
    // =========================================
    
    private String nombre;
    private String marca;
    private double precio;
    private double peso;
    private int stock;
    private CategoriaProducto categoria;
    /**
     * Asociación unidireccional 1 a 1 con CodigoBarras. 
     * Un producto puede tener un código de barras, pero el código no conoce al producto. 
     * Relación opcional (puede ser null).
     */
    private CodigoBarras codigoBarras;
    
    // =========================================
    // CONSTRUCTORES
    // =========================================
    
    /**
     * Constructor para crear producto con datos básicos.
     * @param nombre Nombre del producto
     * @param marca Marca del producto
     * @param precio Precio del producto
     * @param peso Peso del producto
     * @param stock Cantidad en stock
     * @param id ID único del producto
     */
    public Producto(String nombre, String marca, double precio, double peso, int stock, long id) {
        super(id, false);
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
        this.peso = peso;
        this.stock = stock;
    }
    
    /**
     * Constructor vacío para inicialización sin parámetros.
     */
    public Producto() {
        super();
    }
    
    // =========================================
    // MÉTODOS GETTER
    // =========================================
    
    /**
     * @return Nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return Marca del producto
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @return Categoría del producto
     */
    public CategoriaProducto getCategoria() {
        return categoria;
    }

    /**
     * @return Precio del producto
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @return Peso del producto
     */
    public double getPeso() {
        return peso;
    }

    /**
     * @return Stock disponible
     */
    public int getStock() {
        return stock;
    }

    /**
     * Obtiene el código de barras asociado al producto (relación unidireccional
     * 1 a 1).
     *
     * @return Código de barras asociado al producto, o null si no tiene
     */
    public CodigoBarras getCodigoBarras() {
        return codigoBarras;
    }

    // =========================================
    // MÉTODOS SETTER
    // =========================================
    
    /**
     * Establece el nombre del producto.
     * @param nombre Nombre del producto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece la marca del producto.
     * @param marca Marca del producto
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Establece la categoría del producto.
     * @param categoria Categoría del producto
     */
    public void setCategoria(CategoriaProducto categoria) {
        this.categoria = categoria;
    }

    /**
     * Establece el precio del producto.
     * @param precio Precio del producto
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    /**
     * Establece el peso del producto.
     * @param peso Peso del producto
     */
    public void setPeso(double peso) {
        this.peso = peso;
    }

    /**
     * Establece el stock del producto.
     * @param stock Cantidad en stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Establece la asociación unidireccional 1 a 1 con código de barras. 
     * El producto referencia al código, pero el código no referencia al producto.
     *
     * @param codigoBarras Código de barras a asociar (puede ser null para eliminar la asociación)
     */
    public void setCodigoBarras(CodigoBarras codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    // =========================================
    // MÉTODOS SOBREESCRITOS
    // =========================================
    
    /**
     * @return Representación en texto del producto con todos sus atributos
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(".".repeat(40));
        sb.append("\nPRODUCTO: ");
        sb.append("\n - ID: ").append(getId());
        sb.append("\n - Nombre: ").append(nombre);
        sb.append("\n - Marca: ").append(marca != null ? marca : "N/A");
        sb.append("\n - Categoria: ").append(categoria != null ? categoria : "N/A");
        sb.append("\n - Precio: ").append(precio);
        sb.append("\n - Peso: ").append(peso);
        sb.append("\n - Stock: ").append(stock);
        
        if (codigoBarras != null) {
            sb.append(codigoBarras.toString());
        } else {
            sb.append("\n---\nCódigo de barras: No asignado");
        }
        
        return sb.toString();
    }
    
    /**
     * Compara este producto con otro por ID, nombre, marca, categoría, precio y stock.
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
        
        Producto producto = (Producto) obj;
        
        if (this.getId() > 0 && producto.getId() > 0) {
            return this.getId() == producto.getId();
        }
        
        if (Double.compare(producto.precio, precio) != 0) {
            return false;
        }
        if (stock != producto.stock) {
            return false;
        }
        if (nombre == null) {
            if (producto.nombre != null) {
                return false;
            }
        } else if (!nombre.equals(producto.nombre)) {
            return false;
        }
        if (marca == null) {
            if (producto.marca != null) {
                return false;
            }
        } else if (!marca.equals(producto.marca)) {
            return false;
        }
        if (categoria != producto.categoria) {
            return false;
        }
        
        return true;
    }

    /**
     * @return Código hash basado en ID, nombre, marca, categoría, precio y stock
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        
        if (this.getId() > 0) {
            return Long.hashCode(this.getId());
        }
        
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((marca == null) ? 0 : marca.hashCode());
        result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
        long temp = Double.doubleToLongBits(precio);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + stock;
        
        return result;
    }
}