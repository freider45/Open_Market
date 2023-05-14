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
      
    public ProductService(){
    
   }
   
    private IProductAccess repository;

    /**
     * Inyección de dependencias en el constructor. Ya no conviene que el mismo
     * servicio cree un repositorio concreto
     *
     * @param repository una clase hija de IProductAccess
     */
    public ProductService(IProductAccess repository) {
        this.repository = repository;
    }
    

    public boolean saveProduct(String name, String description,Long categoryId) throws Exception{
        
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setDescription(description);
        newProduct.setCategoryId(categoryId);
        
        
        //Validate product
        if (newProduct.getName().isBlank() ) {
            return false;
        }

        return repository.save(newProduct,categoryId);

    }

    public List<Product> findAllProducts() {
        List<Product> products = new ArrayList<>();
        products = repository.findAll();

        return products;
    }
    
    public Product findProductById(Long id) throws Exception{
        return repository.findById(id);
    }
    public List<Product> findProductsByName(String name) {
        List<Product> products = new ArrayList<>();
        products = repository.findByName(name);

        return products;
    }
    public List<Product> findProductsByCategory(String categoryName) {
        List<Product> products = new ArrayList<>();
        products = repository.findByCategory(categoryName);

        return products;
    }
    public boolean deleteProduct(Long id){
        
        return repository.delete(id);
       
    }

    public boolean editProduct(Long productId, Product prod,Long categoryId) {
     
        //Validate product
        if (prod == null || prod.getName().isBlank() ) {
            return false;
        }
        return repository.edit(productId, prod,categoryId);

    }

}