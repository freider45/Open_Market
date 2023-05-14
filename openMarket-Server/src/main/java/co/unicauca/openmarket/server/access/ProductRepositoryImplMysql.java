package co.unicauca.openmarket.server.access;
import co.unicauca.openmarket.commons.infra.Utilities;

import co.unicauca.openmarket.commons.domain.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Repositorio de Clientes en MySWL
 *
 * @author Libardo, Julio
 */
public class ProductRepositoryImplMysql implements IProductRepository {

    /**
     * Conección con Mysql
     */
    private Connection conn;

    public ProductRepositoryImplMysql() {

    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try {
            this.connect();
            String sql = "SELECT * FROM products";
           
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet res = pstmt.executeQuery();
            while (res.next()) {
                Product newProduct = new Product();
                newProduct.setProductId(res.getLong("productId"));
                newProduct.setName(res.getString("name"));
                newProduct.setDescription(res.getString("description"));

                products.add(newProduct);
            }
              pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(ProductRepositoryImplMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }


       public int connect() {
        try {
            Class.forName(Utilities.loadProperty("server.db.driver"));
            //crea una instancia de la controlador de la base de datos
            String url = Utilities.loadProperty("server.db.url");
            String username = Utilities.loadProperty("server.db.username");
            String pwd = Utilities.loadProperty("server.db.password");
            conn = DriverManager.getConnection(url, username, pwd);
            return 1;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ProductRepositoryImplMysql.class.getName()).log(Level.SEVERE, "Error al consultar Customer de la base de datos", ex);
        }
        return -1;
    }

    /**
     * Cierra la conexion con la base de datos
     *
     */
    public void disconnect() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepositoryImplMysql.class.getName()).log(Level.FINER, "Error al cerrar Connection", ex);
        }
    }

    @Override
    public String createProduct(Product newProduct) {
        try {
            this.connect();
            String sql = "INSERT INTO products ( name, description ) "
                    + "VALUES ( ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newProduct.getName());
            pstmt.setString(2, newProduct.getDescription());
            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(ProductRepositoryImplMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newProduct.getProductId().toString();
    }

    public String edit(Long id, Product product, Long categoryId) {
       
        try {
              this.connect();
            String sql = "UPDATE  products "
                    + "SET name=?, description=?, categoryId=? "
                    + "WHERE productId = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getDescription());
            pstmt.setLong(3, categoryId);
            pstmt.setLong(4, id);
            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepositoryImplMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return product.getProductId().toString();
    }

    @Override
    public boolean delete(Long id) {
        try {
              this.connect();
            String sql = "DELETE FROM products "
                    + "WHERE productId = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            this.disconnect();
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepositoryImplMysql.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public Product findById(Long id) {
         Product product = null;
     
        try {
                this.connect();

            String sql = "SELECT * FROM products  "
                    + "WHERE productId = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                Product prod = new Product();
                prod.setProductId(res.getLong("productId"));
                prod.setName(res.getString("name"));
                prod.setDescription(res.getString("description"));
                return prod;
            }
            pstmt.close();
            this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(ProductRepositoryImplMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return product;
    }

    @Override
    public List<Product> findByName(String pname) {
        List<Product> products = new ArrayList<>();
      
        try {

              this.connect();
            String sql = "SELECT * FROM products"
                    + " WHERE name = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, pname);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product newProduct = new Product();
                newProduct.setProductId(rs.getLong("productId"));
                newProduct.setName(rs.getString("name"));
                newProduct.setDescription(rs.getString("description"));

                products.add(newProduct);
            }
            
            stmt.close();
            this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(ProductRepositoryImplMysql.class.getName()).log(Level.SEVERE, null, ex);
        }

        return products;
    }

    @Override
    public List<Product> findByCategory(String categoryName) {
        List<Product> products = new ArrayList<>();
       
        try {
              this.connect();
            // Get the categoryId for the given categoryName
            String categorySql = "SELECT categoryId FROM categories WHERE name = ?";
            PreparedStatement categoryStmt = conn.prepareStatement(categorySql);
            categoryStmt.setString(1, categoryName);
            ResultSet categoryRs = categoryStmt.executeQuery();

            if (categoryRs.next()) {
                long categoryId = categoryRs.getLong("categoryId");

                // Find products with the given categoryId
                String productSql = "SELECT * FROM products WHERE categoryId = ?";
                PreparedStatement productStmt = conn.prepareStatement(productSql);
                productStmt.setLong(1, categoryId);
                ResultSet productRs = productStmt.executeQuery();

                while (productRs.next()) {
                    Product newProduct = new Product();
                    newProduct.setProductId(productRs.getLong("productId"));
                    newProduct.setName(productRs.getString("name"));
                    newProduct.setDescription(productRs.getString("description"));
                    //newProduct.setPrice(productRs.getDouble("price"));
                    products.add(newProduct);
                }
            } 
            
             categoryStmt.close();
            this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(ProductRepositoryImplMysql.class.getName()).log(Level.SEVERE, null, ex);
        }

        return products;
    }

 

}
