
package co.unicauca.openmarket.client.domain.service;

import co.unicauca.openmarket.client.access.ICategoryAccess;
import co.unicauca.openmarket.client.presentation.GUICategoriesFind;
import co.unicauca.openmarket.commons.domain.Category;
import co.unicauca.openmarket.commons.domain.Observer;
import co.unicauca.openmarket.commons.domain.Subject;
import java.util.ArrayList;

import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author brayan
 */
public class CategoryService implements Subject{
    private List<Observer> observers = new ArrayList<>();
    public CategoryService(){
        
    }
    private ICategoryAccess repository;
    
    public CategoryService(ICategoryAccess repository){
        this.repository=repository;
    }
    public boolean saveCategory (Long id,String name)throws Exception{
        Category newCategory=new Category();
        newCategory.setCategoryId(id);
        newCategory.setName(name);
        if(newCategory.getName().isBlank()){
            return false;
        }
          boolean result = repository.save(newCategory);

        // Notificar a los observadores solo si la categoría se guardó correctamente
        if (result) {
            notifyObservers();
        }

        return result;
    }
    public boolean editCategory(Long categoryId,Category cat) throws Exception {
        
        //Validate product
        if(cat==null || cat.getName().isBlank()){
            System.out.println("---------------------Entra---------------------");
            return false;
        }
      
       
 
       boolean result =  repository.edit(categoryId,cat);

        // Notificar a los observadores solo si la categoría se guardó correctamente
        if (result) {
            notifyObservers();
        }

        return result;
    }
    
    public boolean deleteCategory(Long id) throws Exception{
       
            boolean result =  repository.delete(id);

        // Notificar a los observadores solo si la categoría se guardó correctamente
        if (result) {
            notifyObservers();
        }

        return result;
    }  
    public Category findCategoryById(Long id)throws Exception{
        return repository.findById(id);
        
    }
       public List<Category> findAllCategories() throws Exception{
        return repository.findAll();
    }
       
       public List<Category> findCategoriesByName(String name)throws Exception{
        return repository.findByName(name);
    }

   

    @Override
    public void registerObserver(Observer catGui) {
           observers.add(catGui);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
         for (Observer observer : observers) {
            observer.update();
        }
    }


}  
        
