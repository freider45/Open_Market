package co.unicauca.openmarket.client.domain.service;

import co.unicauca.openmarket.client.access.IProductAccess;
import co.unicauca.openmarket.commons.observer.Observer;
import java.util.ArrayList;
import java.util.List;
import co.unicauca.openmarket.commons.domain.Product;
import co.unicauca.openmarket.commons.observer.Subject;

/**
 *
 * @author Libardo, Julio
 */
public class ProductService implements Subject {

    private List<Observer> observers = new ArrayList<>();

    public ProductService() {

    }

    private IProductAccess repository;

    /**
     * Inyección de dependencias en el constructor. Ya no conviene que el mismo
     * servicio cree un repositorio concreto
     *
     * @param repository una clase hija de IProductAccess
     */
    public ProductService(IProductAccess repository) {
        this.repository = repository;
    }

    public boolean saveProduct(Long id, String name, String description,Double price, Long categoryId) throws Exception {
        
        Product newProduct = new Product();
        newProduct.setProductId(id);
        newProduct.setName(name);
        newProduct.setDescription(description);
        newProduct.setPrice(price);
        newProduct.setCategoryId(categoryId);


        boolean result = repository.save(newProduct);

        // Notificar a los observadores solo si la categoría se guardó correctamente
        if (result) {
            notifyObservers();
        }

        return result;

    }

    public List<Product> findAllProducts() throws Exception {
        List<Product> products = new ArrayList<>();
        products = repository.findAll();

        return products;
    }

    public Product findProductById(Long id) throws Exception {
        return repository.findById(id);
    }

    public List<Product> findProductsByName(String name) throws Exception {
        List<Product> products = new ArrayList<>();
        products = repository.findByName(name);
        return products;
    }

    public List<Product> findProductsByCategory(String categoryName) throws Exception {
        List<Product> products = new ArrayList<>();
        products = repository.findByCategory(categoryName);

        return products;
    }

    public boolean deleteProduct(Long id) throws Exception {
        boolean result = repository.delete(id);

        // Notificar a los observadores solo si la categoría se guardó correctamente
        if (result) {
            notifyObservers();
        }

        return result;

    }

    public boolean editProduct(Long productId, Product newProd) throws Exception {

        //Validate product
        if (newProd == null || newProd.getName().isBlank() || newProd.getDescription().isBlank()) {
            return false;
        }

        boolean result = repository.edit(productId, newProd);

        // Notificar a los observadores solo si la categoría se guardó correctamente
        if (result) {
            notifyObservers();
        }

        return result;

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
