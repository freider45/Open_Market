/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.strategyserver.infra;

import co.unicauca.strategyserver.helpers.JsonError;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahurtado
 */
public abstract class ServerHandler extends Thread{  
    private Socket s;
    private Scanner input;
    private PrintStream output;
    
    /**
     * Método abstracto para procesar la petición
     * @param request corresponde a la perición en forma de String
     * @return response que corresponde a la respuesta para el cliente
     */
    public abstract String processRequest(String request);
  
    /**
     * Constructor del manejador de la petición
     */
    public ServerHandler(){
    }
    
    /**
     * Código de ejecucuón del hilo
     */
    @Override
    public void run(){
        try {
            createStreams();
            String request = readStream();
            if(!request.equals("")){
                String response = processRequest(request);
                respond(response);     
            }
            closeStream();

        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Eror al leer el flujo", ex);
        }
    }

    /**
     * @return the s
     */
    public Socket getS() {
        return s;
    }

    /**
     * @param s the s to set
     */
    public void setS(Socket s) {
        this.s = s;
    }

    /**
     * @return the input
     */
    public Scanner getInput() {
        return input;
    }

    /**
     * @param input the input to set
     */
    public void setInput(Scanner input) {
        this.input = input;
    }

    /**
     * @return the output
     */
    public PrintStream getOutput() {
        return output;
    }

    /**
     * @param output the output to set
     */
    public void setOutput(PrintStream output) {
        this.output = output;
    }
    
        
    /**
     * Lee el flujo del socket
     */
    public String readStream() {
        if (getInput().hasNextLine()) {
            // Extrae el flujo que envió la aplicación cliente
            String request = getInput().nextLine();
            return request;
        } else {
            getOutput().flush();
            String errorJson = generateErrorJson();
            getOutput().println(errorJson);
            return "";
        }
    }
  
    
    /**
     * Cierra los flujos de entrada y salida
     *
     * @throws IOException
     */
    public void closeStream() throws IOException {
        getOutput().close();
        getInput().close();
    }
    
    /**
     * Genera un ErrorJson genérico
     *
     * @return error en formato json
     */
    
    private String generateErrorJson() {
        List<JsonError> errors = new ArrayList<>();
        JsonError error = new JsonError();
        error.setCode("400");
        error.setError("BAD_REQUEST");
        error.setMessage("Error en la solicitud");
        errors.add(error);

        Gson gson = new Gson();
        String errorJson = gson.toJson(errors);

        return errorJson;
    }
    
    /**
     * Convierte el objeto Customer a json para que el servidor lo envie como
     * respuesta por el socket
     *
     * @param object
     * @return customer en formato json
     */
    protected String objectToJSON(Object object) {
        Gson gson = new Gson();
        String strObject = gson.toJson(object);
        return strObject;
    }
    
    
    /**
     * Envia la respuesta al cliente
     * @param response
     */
    protected void respond(String response){
         getOutput().println(response);
    }

    /**
     * @return the s
     */
    public Socket getSocket() {
        return getS();
    }

    /**
     * @param s the s to set
     */
    public void setSocket(Socket s) {
        this.setS(s);
    }

    private void createStreams() throws IOException {
        setInput(new Scanner(getS().getInputStream()));
        setOutput(new PrintStream(getS().getOutputStream()));   
    }
   
}
   
