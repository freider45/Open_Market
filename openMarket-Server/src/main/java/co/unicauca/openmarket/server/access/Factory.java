package co.unicauca.openmarket.server.access;

import co.unicauca.openmarket.commons.infra.Utilities;

/**
 * Fabrica que se encarga de instanciar ProductRepository o cualquier otro que
 * se cree en el futuro.
 *
 * @author Libardo, Julio
 */
public class Factory {

    private static Factory instance;

    private Factory() {
    }

    /**
     * Clase singleton
     *
     * @return
     */
    public static Factory getInstance() {

        if (instance == null) {
            instance = new Factory();
        }
        return instance;

    }


    public IProductRepository getRepository() {
        String type = Utilities.loadProperty("product.repository");
        if (type.isEmpty()) {
            type = "default";
        }
        IProductRepository result = null;

        switch (type) {
            case "default":
                result = new ProductRepositoryImplArrays(getCatRepository());
                break;
            case "mysql":
                result = new ProductRepositoryImplMysql();
                break;
        }
        return result;
    }
    
    public ICategoryRepository getCatRepository() {
        String type = Utilities.loadProperty("category.repository");
        if (type.isEmpty()) {
            type = "default";
        }
        ICategoryRepository result = null;

        switch (type) {
            case "default":
                result = new CategoryRepositoryImplArrays();
                break;
            case "mysql":
                result = new CategoryRepositoryImplMysql();
                break;
        }
        return result;
        
    }
}
