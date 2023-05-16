
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
    private double price;

    public Product(Long productId, String name, String description, double price,Long categoryId) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.categoryId=categoryId;
        this.price=price;
    }
    

    
    public Product(){
        this.categoryId=null;
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


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
