/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.openmarket.server.domain.services;

import co.unicauca.openmarket.commons.infra.JsonError;
import co.unicauca.openmarket.server.access.IProductRepository;
import co.unicauca.openmarket.commons.domain.Product;
//import com.google.gson.Gson;
//import java.util.ArrayList;
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

    /**
     * Crea un nuevo customer.Aplica validaciones de negocio
     *
     * @param product
     * @param customer cliente
     * @return devuelve la cedula del customer creado
     */
    
    public synchronized boolean save(Product newProduct, Long categoryId) {
        return repo.save(newProduct, categoryId);
    }
    
    public synchronized boolean edit(Long id, Product newPrduct, Long categoryId){
        return repo.edit(id, newPrduct, categoryId);
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
