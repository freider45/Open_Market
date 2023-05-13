/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.openmarket.commons.domain;

/**
 * La categoria del producto
 * @author brayan
 */
public class Category {
    private Long categoryId;
    private String name;
    
    public Category(Long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }
    public Category() {
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
     
}
