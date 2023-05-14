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

  
    public synchronized Product findProduct(Long id) {
        return repo.findById(id);
    }
    
    /**
     * Crea un nuevo customer.Aplica validaciones de negocio
     *
     * @param product
     * @param customer cliente
     * @return devuelve la cedula del customer creado
     */
    
    public synchronized String createProduct(Product product) {
        List<JsonError> errors = new ArrayList<>();
  
        // Validaciones y reglas de negocio
        if (product.getProductId()==null || product.getDescription().isEmpty()) {
           errors.add(new JsonError("400", "BAD_REQUEST","id y descripcion son obligatorios. "));
        }
      
        // Que no esté repetido
      
        Product productSearched = this.findProduct(product.getProductId());
        if (productSearched != null){
            errors.add(new JsonError("400", "BAD_REQUEST","El producto ya existe. "));
        }
        
       if (!errors.isEmpty()) {
            Gson gson = new Gson();
            String errorsJson = gson.toJson(errors);
            return errorsJson;
        }     
        return null;
        //return repo.createProduct(product);
    }

      
}
