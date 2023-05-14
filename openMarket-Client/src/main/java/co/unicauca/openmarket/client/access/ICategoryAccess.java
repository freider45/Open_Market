/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.openmarket.client.access;


import co.unicauca.openmarket.client.domain.Category;
import java.util.List;


/**
 *
 * @author brayan
 */
public interface ICategoryAccess {
    
   boolean save(Category newCategory)throws Exception;
   boolean edit(Long id, Category category);
   boolean delete(Long id);
   Category findById(Long id)throws Exception;
   List<Category> findAll();
   public List<Category> findByName(String name);

}
