/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.openmarket.client.access;


import co.unicauca.openmarket.commons.domain.Category;
import java.util.List;


/**
 *
 * @author brayan
 */
public interface ICategoryAccess {
    
   boolean save(Category newCategory)throws Exception;
   boolean edit(Long id, Category category)throws Exception;
   boolean delete(Long id)throws Exception;
   Category findById(Long id)throws Exception;
   List<Category> findAll()throws Exception;
   public List<Category> findByName(String name)throws Exception;

}
