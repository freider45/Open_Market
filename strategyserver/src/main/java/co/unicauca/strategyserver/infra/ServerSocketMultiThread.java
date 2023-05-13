/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.strategyserver.infra;
import co.unicauca.strategyserver.helpers.Utilities;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Julio Hurtado, Libardo Pantoja
 */

public class ServerSocketMultiThread{
    
/**
     * Server Socket, la orejita
     */
    private static ServerSocket ssock;
    /**
     * Socket por donde se hace la petición/respuesta
     */
    private static Socket socket;
    /**
     * Puerto por donde escucha el server socket
     */
    protected static int PORT=-1;
    
    /**
     * El hilo manejador de la peteicion del cliente
     */
    private ServerHandler handler;
    
    
    
    public ServerSocketMultiThread(int port){
        PORT = port;
    }
    
    
    /**
     * Arranca el servidor y hace la estructura completa
     * @param handler
     */
    
    public void setServerHandler(ServerHandler handler){
        this.handler = handler;
    }
    
    public void startServer() {
        openPort();
        while (true) {
            waitToClient();
            throwThread();
        }
    }
    /**
     * Abre el puerto
     */
    
    private void openPort(){
        try {
            if(PORT == -1)
                PORT = Integer.parseInt(Utilities.loadProperty("server.port"));
            ssock = new ServerSocket(PORT);
            Logger.getLogger("Server").log(Level.INFO, "Servidor iniciado, escuchando por el puerto {0}", PORT);
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error del server socket al abrir el puerto", ex);
        }
    } 
    
    private Socket waitToClient(){
        try {
            System.out.println("En servidor multihilo esta espera");
            socket = ssock.accept();
            Logger.getLogger("Socket").log(Level.INFO, "Socket conectado");
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Eror al abrir un socket", ex);
        }
        return socket;
    }
    
    /**
     * Lanza el hilo
     */
    
    private void throwThread() {
        try {   
            handler = (ServerHandler) handler.getClass().newInstance();
            handler.setSocket(socket);
            handler.start();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ServerSocketMultiThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
