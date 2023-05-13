/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.openmarket.domain.services;

import co.unicauca.openmarket.server.access.IProductRepository;
import co.unicauca.openmarket.server.domain.Product;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author brayan
 */
public class ProductService {
      private IProductRepository repository;
         public ProductService(IProductRepository repository) {
        this.repository = repository;
    }
         
         
             public List<Product> findAllProducts() {
        List<Product> products = new ArrayList<>();
        products = repository.findAll();

        return products;
    }
    
    public Product findProductById(Long id){
        return repository.findById(id);
    }
    
    public boolean deleteProduct(Long id){
       return repository.delete(id);
    }

    public boolean editProduct(Long productId, Product prod) {
        
         return repository.edit(productId, prod, productId);
    }

      
}
