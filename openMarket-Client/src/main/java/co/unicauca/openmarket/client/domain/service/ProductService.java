package co.unicauca.openmarket.client.domain.service;

import java.util.ArrayList;
import java.util.List;
import co.unicauca.openmarket.client.access.IProductAccess;
import co.unicauca.openmarket.client.domain.Product;

/**
 *
 * @author Libardo, Julio
 */
public class ProductService  {
      
    private final IProductAccess access;

    /**
     * Constructor privado que evita que otros objetos instancien
     * @param service implementacion de tipo IProductAccess
     */
    public ProductService(IProductAccess access) {
        this.access = access;
    }

    /**
     * Busca un producto en el servidor remoto
     *
     * @param id identificador del producto
     * @return Objeto tipo Product, null si no lo encuentra
     * @throws java.lang.Exception la excepcio se lanza cuando no logra conexión
     * con el servidor
     */
    public Product findById(Long id) throws Exception {
        return access.findById(id);
    }
    
    public boolean createProduct(Product product, Long categoryId) throws Exception {
        return access.createProduct(product, categoryId);
    }

}