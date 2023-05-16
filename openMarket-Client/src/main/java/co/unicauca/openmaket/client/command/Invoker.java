/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.openmaket.client.command;

import co.unicauca.openmarket.commons.observer.Observer;
import co.unicauca.openmarket.commons.observer.Subject;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Jorge
 */
public class Invoker implements Subject {

    private final Stack<Command> commandHistory;
    private final Stack<Command> undoneCommands;
    private List<Observer> observers = new ArrayList<>();

    public Invoker() {
        commandHistory = new Stack<>();
        undoneCommands = new Stack<>();
    }

    public boolean executeCommand(Command command) {
        if (command.execute()) {
            commandHistory.push(command);
            if (!undoneCommands.isEmpty()) {
                undoneCommands.pop();
            }
            notifyObservers();
            return true;
        }
        return false;
    }

    public void undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.pop();
            lastCommand.undo();
            undoneCommands.push(lastCommand);
            notifyObservers();
        }
    }

    public void redoLastCommand() {
        if (!undoneCommands.isEmpty()) {
            Command lastCommand = undoneCommands.pop();
            lastCommand.execute();
            commandHistory.push(lastCommand);
            notifyObservers();
        }
    }

    public boolean hasMoreUndoCommands() {
        return !commandHistory.isEmpty();
    }

    public boolean hasMoreRedoCommands() {
        return !undoneCommands.isEmpty();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

}
