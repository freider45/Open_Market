
package co.unicauca.openmarket.client.domain.service;

import co.unicauca.openmarket.client.access.ICategoryAccess;
import co.unicauca.openmarket.client.domain.Category;

import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author brayan
 */
public class CategoryService {
    
    private final ICategoryAccess access;

    /**
     * Constructor privado que evita que otros objetos instancien
     * @param service implementacion de tipo ICategoryAccess
     */
    public CategoryService(ICategoryAccess access) {
        this.access = access;
    }

    /**
     * Busca una categoria en el servidor remoto
     *
     * @param id identificador de la categoria
     * @return Objeto tipo Category, null si no lo encuentra
     * @throws java.lang.Exception la excepcio se lanza cuando no logra conexión
     * con el servidor
     */
    public Category findById(Long id) throws Exception {
        return access.findById(id);
    }
    
    public boolean createCategory(Category category) throws Exception {
        return access.createCategory(category);

    }
}  
        
