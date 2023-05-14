/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.openmarket.server.infra.tcpip;

import co.unicauca.openmarket.commons.infra.*;
import co.unicauca.openmarket.server.domain.services.ProductService;
import co.unicauca.openmarket.commons.domain.Product;
import co.unicauca.strategyserver.infra.ServerHandler;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author brayan
 */
public class OpenMarketHandler extends ServerHandler  {
       private static ProductService service;
       
       public OpenMarketHandler(){
           
       }
       
       @Override
    public String processRequest(String requestJson) {
        // Convertir la solicitud a objeto Protocol para poderlo procesar
        Gson gson = new Gson();  
        Protocol protocolRequest;
        protocolRequest = gson.fromJson(requestJson, Protocol.class);
        String response="";
        switch (protocolRequest.getResource()) {
            case "customer":
                if (protocolRequest.getAction().equals("get")) {
                    // Consultar un customer
                    response = processGetProduct(protocolRequest);
                }

                if (protocolRequest.getAction().equals("post")) {
                    // Agregar un customer    
                    response = processPostProduct(protocolRequest);

                }
                break;
        }
        return response;

    }

    /**
     * Procesa la solicitud de consultar un customer
     *
     * @param protocolRequest Protocolo de la solicitud
     */
    private String processGetProduct(Protocol protocolRequest) {
        // Extraer el  del primer parámetro
        Long id =  Long.valueOf(protocolRequest.getParameters().get(0).getValue());
       
        Product product = this.service.findProductById(id);
        if (product == null) {
            String errorJson = generateNotFoundErrorJson();
            return errorJson;
        } else {
            return objectToJSON(product);
        }
    }

    /**
     * Procesa la solicitud de agregar un customer
     *
     * @param protocolRequest Protocolo de la solicitud
     */
    private String processPostProduct(Protocol protocolRequest) {
        Product product = new Product();
        // Reconstruir el customer a partid de lo que viene en los parámetros
        product.setProductId(Long.valueOf(protocolRequest.getParameters().get(0).getValue()));
        product.setName(protocolRequest.getParameters().get(1).getValue());
        product.setDescription(protocolRequest.getParameters().get(2).getValue());
    
        String response = getService().createCustomer(product);
        return response;
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
        String errorsJson = gson.toJson(errors);

        return errorsJson;
    }

    /**
     * @return the service
     */
    public ProductService getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(ProductService service) {
        this.service = service;
    } 
       
}
