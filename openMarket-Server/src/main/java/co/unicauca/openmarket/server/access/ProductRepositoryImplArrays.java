package co.unicauca.openmarket.server.access;
import co.unicauca.openmarket.server.access.IProductRepository;
import co.unicauca.openmarket.commons.domain.Product;
//import co.unicauca.travelagency.commons.domain.Customer;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementación de IProductRepository. Utilliza arreglos en memoria
 *
 * @author Libardo Pantoja, Julio Hurtado
 */
public final class ProductRepositoryImplArrays implements IProductRepository {

    /**
     * Array List de clientes
     */
    private static List<Product> products;
    private Connection conn;

    public ProductRepositoryImplArrays() {
        if (products == null) {
            products = new ArrayList();
        }

        if (products.isEmpty()) {
            inicializar();
        }
    }

    public void inicializar() {
//        customers.add(new Customer("98000010", "Alexander", "Ponce Yepes", "Calle 12 No 12-12 Popayan", "3154575845", "fer@hotmail.com", "Masculino"));
        products.add(new Product(1L, "Leche", "rica", 1200));
        products.add(new Product(2L, "Tamal", "Valluno", 4000));
        products.add(new Product(3L, "Atun", "De pescado", 12000));
        products.add(new Product(4L, "Zanahoria", "1 libra", 10000));
    }

    @Override
    public boolean edit(Long id, Product product, Long categoryId) {
       if( this.delete(id)==true){
           products.add(product);
           return true;
       }
        return false;
    }

    @Override
    public boolean delete(Long id) {
       Product productToDelete=findById(id);
       if(productToDelete==null){
           return false;
       }
    
        products.remove(productToDelete);
        
        return true;
       
       
    }

    @Override
    public Product findById(Long id) {
        for (Product product : products) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public List<Product> findByName(String pname) {
        List<Product> listaProductos = new ArrayList<>();
         for (Product product : products) {
            if (product.getName().equals(pname)) {
                listaProductos.add(product);
            }
        }
        return listaProductos;
    }

    @Override
    public List<Product> findByCategory(String categoryName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Product> findAll() {
        return products;
    }
    
     @Override
    public Long createProduct(Product product) {
        products.add(product);
        return product.getProductId();
    }
}
