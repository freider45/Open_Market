package co.unicauca.openmarket.server.access;
import co.unicauca.openmarket.commons.domain.Category;
import co.unicauca.openmarket.commons.domain.Product;
import co.unicauca.openmarket.server.domain.services.CategoryService;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


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
    private ICategoryRepository categoryService;

    public ProductRepositoryImplArrays(ICategoryRepository catRepo) {
        this.categoryService=catRepo;
        if (products == null) {
            products = new ArrayList<>();
        }

        
        if (products.isEmpty()) {
            inicializar();
        }
    }

    public void inicializar() {
        products.add(new Product(1L, "Leche", "rica", 1200,1L));
        products.add(new Product(2L, "Tamal", "Valluno", 4000,1L));
        products.add(new Product(3L, "Atun", "De pescado", 12000,1L));
        products.add(new Product(4L, "Zanahoria", "1 libra", 10000,1L));
    }


    @Override
    public String createProduct(Product newProduct) {
       products.add(newProduct);
        return newProduct.getProductId().toString();
    }

    @Override
    public boolean edit(Long id, Product product) {
      Product productToEdit=findById(id);
      productToEdit.setName(product.getName());
      productToEdit.setPrice(product.getPrice());
       productToEdit.setDescription(product.getDescription());
       productToEdit.setCategoryId(product.getCategoryId());

     
      return true;
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
        try {
            List<Category> allCategories = this.categoryService.findAll();

            Map<Long, Category> categoryMap = allCategories.stream()
                    .collect(Collectors.toMap(Category::getCategoryId, Function.identity()));

            // Ahora puedes buscar las categorías por id en la memoria
            return products.stream()
                    .filter(product -> categoryName.equals(categoryMap.get(product.getCategoryId()).getName()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // maneja la excepción
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Product> findAll() {
        return products;
    }


    
}
