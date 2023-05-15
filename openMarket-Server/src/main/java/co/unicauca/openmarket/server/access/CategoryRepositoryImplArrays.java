/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.openmarket.server.access;

import co.unicauca.openmarket.commons.domain.Category;
import co.unicauca.openmarket.commons.domain.Product;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fre90
 */
public class CategoryRepositoryImplArrays implements ICategoryRepository{

    private static List<Category> categories;
    public CategoryRepositoryImplArrays() {
        if (categories == null){
            categories = new ArrayList<>();
        }
        
        if (categories.size() == 0){
           inicializar();
        }
    }
   
    public void inicializar() {
        categories.add(new Category(1L, "Bebidas"));
        categories.add(new Category(2L, "Lacteos"));
        categories.add(new Category(3L, "Carnicos"));
    }
    @Override
    public boolean save(Category newCategory) {
        categories.add(newCategory);
        return true;
    }

    @Override
    public boolean edit(Long id, Category prmCategory) {
       for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getCategoryId().equals(id)) {
                categories.set(i, prmCategory);
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean delete(Long id) {
       for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getCategoryId().equals(id)) {
                categories.remove(i);
                return true;
            }
        }
        return false;
    }


    @Override
    public Category findById(Long id) {
       for (Category OCategory : categories) {
            if (OCategory.getCategoryId().equals(id)) {
                return OCategory;
            }
        }
        return null;
    }

    @Override
    public List<Category> findAll() {
        if (categories.isEmpty())
            return null;      
        return categories;
    }

    @Override
    public List<Category> findByName(String name) {
        List<Category> listaCategorias = new ArrayList<>();
        for (Category category : categories) {
            if (category.getName().equals(name)) {
                listaCategorias.add(category);
            }
        }
        return listaCategorias;
    }
    
}
