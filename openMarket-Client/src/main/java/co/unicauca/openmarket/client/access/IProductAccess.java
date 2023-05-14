
package co.unicauca.openmarket.client.access;


import co.unicauca.openmarket.client.domain.Product;
import java.util.List;


/**
 *
 * @author Libardo, Julio
 */
public interface IProductAccess {
    boolean createProduct(Product newProduct,Long categoryId)throws Exception;
    
    boolean edit(Long id, Product product, Long categoryId);
    
    boolean delete(Long id);

    Product findById(Long id)throws Exception;
    
    List<Product> findByName(String pname);
    List<Product> findByCategory(String categoryName);
    List<Product> findAll();

}
