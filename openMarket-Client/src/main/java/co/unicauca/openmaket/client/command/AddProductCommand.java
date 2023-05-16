/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.openmaket.client.command;

import co.unicauca.openmarket.client.domain.service.ProductService;
import co.unicauca.openmarket.commons.domain.Product;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge
 */
public class AddProductCommand implements Command {
    
    private final ProductService productService;
    private final Product product;

    public AddProductCommand(ProductService productService, Product product) {
        this.productService = productService;
        this.product = product;
    }

    @Override
    public boolean execute() {
        Long prodId=product.getProductId();
        String name= product.getName();
        String desc= product.getDescription();
        Double price = product.getPrice();
        Long catId=product.getCategoryId();
        try {
            return productService.saveProduct(prodId, name, desc, price, catId);
        } catch (Exception ex) {
            Logger.getLogger(AddProductCommand.class.getName()).log(Level.SEVERE, null, ex);
                return false;
        }
    
    }

    @Override
    public void undo() {
        try {
            productService.deleteProduct(product.getProductId());
        } catch (Exception ex) {
            Logger.getLogger(AddProductCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}



