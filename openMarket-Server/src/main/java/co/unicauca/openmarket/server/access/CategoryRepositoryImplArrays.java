/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.openmarket.server.access;

import co.unicauca.openmarket.commons.domain.Category;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fre90
 */
public class CategoryRepositoryImplArrays implements ICategoryRepository{

    private static List<Category> category;
    public CategoryRepositoryImplArrays() {
        if (category == null){
            category = new ArrayList();
        }
        
        if (category.size() == 0){
           inicializar();
        }
    }
   
    public void inicializar() {
        category.add(new Category(1L, "Bebidas"));
        category.add(new Category(2L, "Lacteos"));
        category.add(new Category(3L, "Carnicos"));
    }
    @Override
    public boolean save(Category newCategory) {
        category.add(newCategory);
        return true;
    }

    @Override
    public boolean edit(Long id, Category prmCategory) {
       for (int i = 0; i < category.size(); i++) {
            if (category.get(i).getCategoryId().equals(id)) {
                category.set(i, prmCategory);
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean delete(Long id) {
       for (int i = 0; i < category.size(); i++) {
            if (category.get(i).getCategoryId().equals(id)) {
                category.remove(i);
                return true;
            }
        }
        return false;
    }


    @Override
    public Category findById(Long id) {
       for (Category OCategory : category) {
            if (OCategory.getCategoryId().equals(id)) {
                return OCategory;
            }
        }
        return null;
    }

    @Override
    public List<Category> findAll() {
        if (category.isEmpty())
            return null;      
        return category;
    }

    @Override
    public List<Category> findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
