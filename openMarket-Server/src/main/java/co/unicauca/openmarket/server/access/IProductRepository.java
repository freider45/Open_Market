
package co.unicauca.openmarket.server.access;

import co.unicauca.openmarket.server.domain.Product;
import java.util.List;


/**
 *
 * @author Libardo, Julio
 */
public interface IProductRepository {
//    boolean save(Product newProduct,Long categoryId);
    
    boolean edit(Long id, Product product, Long categoryId);
    
    boolean delete(Long id);
    Long createProduct(Product product);
    Product findById(Long id);
    
   List<Product> findByName(String pname);
    List<Product> findByCategory(String categoryName);
    List<Product> findAll();
    

}
