/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package co.unicauca.openmaket.client.command;

/**
 *
 * @author Jorge
 */
public interface Command {
    boolean execute();
    void undo();
}

