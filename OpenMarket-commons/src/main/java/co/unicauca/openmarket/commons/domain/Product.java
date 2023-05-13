
package co.unicauca.openmarket.commons.domain;

/**
 *producto recibido
 * @author brayan
 */
public class Product {
    private Long productId;

    private String name;
    
    private String description;
    private Long categoryId;
    
    public Product(Long productId, String name, String description, double price,Long categoryId) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.categoryId=categoryId;
    }
    public Product(){
        
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
     

}
