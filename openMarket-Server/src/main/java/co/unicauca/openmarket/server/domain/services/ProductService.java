/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.openmarket.server.domain.services;

import co.unicauca.openmarket.commons.infra.JsonError;
import co.unicauca.openmarket.server.access.IProductRepository;
import co.unicauca.openmarket.commons.domain.Product;
import com.google.gson.Gson;
//import com.google.gson.Gson;
//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author brayan
 */
public class ProductService {
 
    IProductRepository repo;

    /**
     * Constructor parametrizado. Hace inyeccion de dependencias
     *
     * @param repo repositorio de tipo ICustomerRepository
     */
    public ProductService(IProductRepository repo) {
        this.repo = repo;
    }


    
    public synchronized String createProduct(Product product) {
        List<JsonError> errors = new ArrayList<>();

        // Validaciones y reglas de negocio
        if (product.getProductId()==null || product.getDescription().isEmpty()
                || product.getName().isEmpty()) {
            errors.add(new JsonError("400", "BAD_REQUEST","id, nombres, apellidos, email y género son obligatorios. "));
        }

        // Que no esté repetido

        Product productSearched = this.findById(product.getProductId());
        if (productSearched != null){
            errors.add(new JsonError("400", "BAD_REQUEST","El producto ya existe. "));
        }

        if (!errors.isEmpty()) {
            Gson gson = new Gson();
            return gson.toJson(errors);
        }

        return repo.createProduct(product);
    }
    
    public synchronized boolean edit(Long id, Product newProduct){
        return repo.edit(id, newProduct);
    }
    
    public synchronized boolean delete(Long id){
        return repo.delete(id);
    }
    public synchronized Product findById(Long id){
        return repo.findById(id);
    };
    public synchronized List<Product> findAll(){
        return repo.findAll();
    };
    public synchronized List<Product> findByCategory(String categoryName){
        return repo.findByCategory(categoryName);
    };
    public synchronized List<Product> findByName(String name){
        return repo.findByName(name);
    };

      
}
