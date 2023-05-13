/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.unicauca.openmarket.commons.infra;

import java.util.ArrayList;
import java.util.List;

/**

 */
public class Parameters {
    private List<Parameter> parameters;
    public Parameters() {
        parameters = new ArrayList<>();
    }
    
    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(String name, String value) {
        parameters.add(new Parameter(name, value));
    }

}
