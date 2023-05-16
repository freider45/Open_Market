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
import java.util.Objects;

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
                    response = processGetProduct(protocolRequest);
                }
                if (protocolRequest.getAction().equals("deleteProduct")) {
                    response = String.valueOf(processDeleteProduct(protocolRequest));
                }
                if (protocolRequest.getAction().equals("getProductsByName")) {
                    response = processGetProductsByName(protocolRequest);
                }
                if (protocolRequest.getAction().equals("getProductsByCategory")) {
                    response = processGetProductsByCategory(protocolRequest);
                }
                if (protocolRequest.getAction().equals("post")) {
                    response = processPostProduct(protocolRequest);
                }
                if (protocolRequest.getAction().equals("getListProducts")) {
                    response = processGetListProductos();
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
                if (protocolRequest.getAction().equals("getListCategoryByName")) {
                    //Listar categorias por nombre
                    response = processGetListCategoryByName(protocolRequest);
                }
                if (protocolRequest.getAction().equals("getListCategory")) {
                    //Listar categorias por nombre
                    response = processGetListCategory();
                }
        }


        return response;

    }

    private String processGetProductsByCategory(Protocol protocolRequest) {
        String categoryName = protocolRequest.getParameters().get(0).getValue();
        List<Product> products=getProductService().findByCategory(categoryName);
        return objectToJSON(products);
    }

    private String processGetProductsByName(Protocol protocolRequest) {
        String productName = protocolRequest.getParameters().get(0).getValue();
        List<Product> products=getProductService().findByName(productName);
        return objectToJSON(products);
    }

    private String processGetListCategory() {
        List<Category> categories;
        categories = getCategoryService().findAll();
        return objectToJSON(categories);
    }

    private String processDeleteProduct(Protocol protocolRequest) {
        String productId = protocolRequest.getParameters().get(0).getValue();
        System.out.println("borrado");
        Product product = getProductService().findById(Long.valueOf(productId));
        if (product == null) {
            return generateNotFoundErrorJson();
        }
        getProductService().delete(Long.valueOf(productId));

        return objectToJSON(true);

    }

    private String processGetListProductos() {
        List<Product> products;
        products = getProductService().findAll();
        return objectToJSON(products);
    }

    private String processEditProduct(Protocol protocolRequest) {
        Long id = Long.valueOf(protocolRequest.getParameters().get(0).getValue());
        Product product = getProductService().findById(id);
        if (product == null) {
            return generateNotFoundErrorJson();
        }

         product = new Product();
        product.setProductId(Long.parseLong(protocolRequest.getParameters().get(0).getValue()));
        product.setName(protocolRequest.getParameters().get(1).getValue());
        product.setDescription(protocolRequest.getParameters().get(2).getValue());
        product.setCategoryId(Long.parseLong(protocolRequest.getParameters().get(3).getValue()));

        boolean response = getProductService().edit(Long.parseLong(protocolRequest.getParameters().get(0).getValue()),product);
        return String.valueOf(response);
    }

    /**
     * Procesa la solicitud de consultar un customer
     *
     * @param protocolRequest Protocolo de la solicitud
     */
    private String processGetProduct(Protocol protocolRequest) {
        // Extraer el del primer parámetro
        Long id = Long.valueOf(protocolRequest.getParameters().get(0).getValue());

        Product product = getProductService().findById(id);
        if (product == null) {
            return generateNotFoundErrorJson();
        } else {
            return objectToJSON(product);
        }
    }


    private String processListCategory() {
        // Lista de todas las categorias
        List<Category> category;
        category = getCategoryService().findAll();
        return objectToJSON(category);
    }

    private String processPostProduct(Protocol protocolRequest) {
        Long id = Long.valueOf(protocolRequest.getParameters().get(0).getValue());

        Product product= getProductService().findById(id);

        if(!(product == null)){
            return generateBadRequestJson("product");

        }

         product = new Product();
        // Reconstruir el customer a partir de lo que viene en los parámetros
        product.setProductId(Long.valueOf(protocolRequest.getParameters().get(0).getValue()));
        product.setName(protocolRequest.getParameters().get(1).getValue());
        product.setDescription(protocolRequest.getParameters().get(2).getValue());
        String category=protocolRequest.getParameters().get(2).getValue();

        if(!category.isEmpty()){
            product.setCategoryId(Long.valueOf(category));
        }

        getProductService().createProduct(product);

        return objectToJSON(product);
    }

    private String processGetCategory(Protocol protocolRequest) {
        Long id = Long.valueOf(protocolRequest.getParameters().get(0).getValue());
        Category category = getCategoryService().findById(id);
        if (category == null) {
            return generateNotFoundErrorJson();
        } else {
            return objectToJSON(category);
        }
    }

    private String processGetListCategoryByName(Protocol protocolRequest) {
        String name = protocolRequest.getParameters().get(0).getValue();
        List<Category> categories;
        categories = getCategoryService().findByName(name);
        return objectToJSON(categories);
    }

    private String processDeleteCategory(Protocol protocolRequest) {
        // Eliminar una categoria
        Long id = Long.valueOf(protocolRequest.getParameters().get(0).getValue());
        boolean response = getCategoryService().delete(id);
        return String.valueOf(response);
    }

    private String processEditCategory(Protocol protocolRequest) {
        // Editar el name de la categoria
        Long id = Long.valueOf(protocolRequest.getParameters().get(0).getValue());
        Category category = getCategoryService().findById(id);
        if (category == null) {
            return generateNotFoundErrorJson();
        }

        category = new Category();
        category.setCategoryId(Long.parseLong(protocolRequest.getParameters().get(0).getValue()));
        category.setName(protocolRequest.getParameters().get(1).getValue());
        boolean response = getCategoryService().edit(Long.parseLong(protocolRequest.getParameters().get(0).getValue()),category);
        return String.valueOf(response);
    }

    private String processPostCategory(Protocol protocolRequest) {
        Long id = Long.valueOf(protocolRequest.getParameters().get(0).getValue());

        Category category= getCategoryService().findById(id);

        if(!(category == null)){
            return generateBadRequestJson("category");
        }

         category = new Category();
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

    private String generateBadRequestJson(String context) {
        List<JsonError> errors = new ArrayList<>();
        JsonError error = new JsonError();
        error.setCode("400");
        error.setError("BAD_REQUEST");

        if(Objects.equals(context, "product")){
            error.setMessage("Error, un producto con ese id ya existe");
        }else{
            error.setMessage("Error, una categoria con ese id ya existe");
        }
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