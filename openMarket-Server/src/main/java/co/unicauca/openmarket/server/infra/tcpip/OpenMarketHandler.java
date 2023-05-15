/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.openmarket.server.infra.tcpip;

import co.unicauca.openmarket.commons.domain.Category;
import co.unicauca.openmarket.commons.infra.*;
import co.unicauca.openmarket.server.domain.services.CategoryService;
import co.unicauca.openmarket.server.domain.services.ProductService;
import co.unicauca.openmarket.commons.domain.Product;
import co.unicauca.strategyserver.infra.ServerHandler;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author brayan
 */
public class OpenMarketHandler extends ServerHandler {
    private static ProductService productService;
    private static CategoryService categoryService;

    public OpenMarketHandler() {

    }

    @Override
    public String processRequest(String requestJson) {
        // Convertir la solicitud a objeto Protocol para poderlo procesar
        Gson gson = new Gson();
        Protocol protocolRequest;
        protocolRequest = gson.fromJson(requestJson, Protocol.class);
        String response = "";
        switch (protocolRequest.getResource()) {
            case "product":
                if (protocolRequest.getAction().equals("get")) {
                    // Consultar un customer
                    response = processGetProduct(protocolRequest);
                }

                if (protocolRequest.getAction().equals("post")) {
                    // Agregar un customer    
                    response = processPostProduct(protocolRequest);
                }
                if (protocolRequest.getAction().equals("edit")) {
                    // Editar un producto
                    response = processEditProduct(protocolRequest);
                }
                break;
            case "category":
                if (protocolRequest.getAction().equals("get")) {
                    // Consultar una categoria
                    response = processGetCategory(protocolRequest);
                }

                if (protocolRequest.getAction().equals("post")) {
                    // Agregar una categoria
                    response = processPostCategory(protocolRequest);
                }
                if (protocolRequest.getAction().equals("edit")) {
                    // Editar categoria
                    response = processEditCategory(protocolRequest);
                }
                if (protocolRequest.getAction().equals("delete")) {
                    //Eliminar categoria
                    response = processDeleteCategory(protocolRequest);
                }
                if (protocolRequest.getAction().equals("listCategory")) {
                    //Lista de las categoria
                    response = processListCategory();
                }
                if (protocolRequest.getAction().equals("getListCategory")) {
                    //Listar categorias por nombre
                    response = processGetListCategory(protocolRequest);
                }

        }


        return response;

    }

    private String processEditProduct(Protocol protocolRequest) {
        Product producto = new Product();
        producto.setProductId(Long.parseLong(protocolRequest.getParameters().get(0).getValue()));
        producto.setName(protocolRequest.getParameters().get(1).getValue());
        producto.setDescription(protocolRequest.getParameters().get(2).getValue());
        producto.setCategoryId(Long.parseLong(protocolRequest.getParameters().get(3).getValue()));

        return getProductService().edit(Long.parseLong(protocolRequest.getParameters().get(0).getValue()), producto, Long.parseLong(protocolRequest.getParameters().get(3).getValue()));
    }

    /**
     * Procesa la solicitud de consultar un customer
     *
     * @param protocolRequest Protocolo de la solicitud
     */
    private String processGetProduct(Protocol protocolRequest) {
        // Extraer el del primer parámetro
        Long id = Long.valueOf(protocolRequest.getParameters().get(0).getValue());

        Product product = productService.findById(id);
        if (product == null) {
            return generateNotFoundErrorJson();
        } else {
            return objectToJSON(product);
        }
    }

    /**
     * Procesa la solicitud de agregar un customer
     *
     * @param protocolRequest Protocolo de la solicitud
     */
    private String processListCategory() {
        // Lista de todas las categorias
        List<Category> category;
        category = getCategoryService().findAll();
        return objectToJSON(category);
    }

    private String processPostProduct(Protocol protocolRequest) {
        Product product = new Product();
        // Reconstruir el customer a partir de lo que viene en los parámetros
        product.setProductId(Long.valueOf(protocolRequest.getParameters().get(0).getValue()));
        product.setName(protocolRequest.getParameters().get(1).getValue());
        product.setDescription(protocolRequest.getParameters().get(2).getValue());

        return getProductService().createProduct(product);
    }

    private String processGetCategory(Protocol protocolRequest) {
        Long id = Long.parseLong(protocolRequest.getParameters().get(0).getValue());
        Category category = getCategoryService().findById(id);
        if (category == null) {
            return generateNotFoundErrorJson();
        } else {
            return objectToJSON(category);
        }
    }

    private String processGetListCategory(Protocol protocolRequest) {
        //Listar categorias por nombre
        String name = protocolRequest.getParameters().get(0).getValue();
        List<Category> category;
        category = getCategoryService().findByName(name);
        return objectToJSON(category);
    }

    private String processDeleteCategory(Protocol protocolRequest) {
        // Eliminar una categoria
        Long id = Long.parseLong(protocolRequest.getParameters().get(0).getValue());
        boolean response = getCategoryService().delete(id);
        return String.valueOf(response);
    }

    private String processEditCategory(Protocol protocolRequest) {
        // Editar el name de la categoria
        Long id = Long.parseLong(protocolRequest.getParameters().get(0).getValue());
        String name = protocolRequest.getParameters().get(1).getValue();
        Category newCategory = new Category(id, name);
        boolean response = getCategoryService().edit(id, newCategory);
        return String.valueOf(response);
    }

    private String processPostCategory(Protocol protocolRequest) {
        Category category = new Category();
        // Reconstruir La categoria a partir de lo que viene en los parámetros
        category.setCategoryId(Long.parseLong(protocolRequest.getParameters().get(0).getValue()));
        category.setName(protocolRequest.getParameters().get(1).getValue());
        boolean response = getCategoryService().save(category);
        return String.valueOf(response);
    }

    /**
     * Genera un ErrorJson de cliente no encontrado
     *
     * @return error en formato json
     */
    private String generateNotFoundErrorJson() {
        List<JsonError> errors = new ArrayList<>();
        JsonError error = new JsonError();
        error.setCode("404");
        error.setError("NOT_FOUND");
        error.setMessage("Producto no encontrado. Id no existe");
        errors.add(error);

        Gson gson = new Gson();

        return gson.toJson(errors);
    }


    public CategoryService getCategoryService() {
        return categoryService;
    }

    public ProductService getProductService() {
        return productService;
    }

    /**
     * @param service the service to set
     */
    public void setProductService(ProductService service) {
        productService = service;
    }

    public void setCategoryService(CategoryService service) {
        categoryService = service;
    }


}