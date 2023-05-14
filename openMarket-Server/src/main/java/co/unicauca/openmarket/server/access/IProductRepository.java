
package co.unicauca.openmarket.server.access;

import co.unicauca.openmarket.commons.domain.Product;
import java.util.List;


/**
 *
 * @author Libardo, Julio
 */
public interface IProductRepository {
    String createProduct(Product newProduct);
    
    String edit(Long id, Product product, Long categoryId);
    
    boolean delete(Long id);
    //Long createProduct(Product product);
    Product findById(Long id);
    
   List<Product> findByName(String pname);
   List<Product> findByCategory(String categoryName);
   List<Product> findAll();
    

}
