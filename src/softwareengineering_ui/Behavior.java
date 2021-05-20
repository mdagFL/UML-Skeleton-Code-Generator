
package softwareengineering_ui;

/**
 * Represents a behavior of a class.
 * Author: Michael D'Agostino
 * Date Created: 11/21/19
 */

import java.util.List;
import java.util.ArrayList;

public class Behavior {
    private String name;
    private String accessSpecifier;
    private String returnType;
    private List<Parameter> parameters;
    
    public Behavior(String name, String accessSpecifier, String returnType)
    {
        this.name = name;
        this.accessSpecifier = accessSpecifier.toLowerCase();
        this.returnType = returnType;
        parameters = new ArrayList<Parameter>();
    }
    
    public void addParameter(String paramName, String paramDataType)
    {
        this.parameters.add(new Parameter(paramName, paramDataType));
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getAccessSpecifier()
    {
        return accessSpecifier;
    }
    
    public String getReturnType()
    {
        return returnType;
    }
    
    public List<Parameter> getParameters()
    {
        return parameters;
    }
    
    public String getUMLComponentString()
    {
        String componentString = "";
        
        // determine and add access symbol
        if (accessSpecifier.equals("public"))
            componentString += "+";
        else if (accessSpecifier.equals("private"))
            componentString += "-";
        else if (accessSpecifier.equals("protected"))
            componentString += "#";
        
        // add name
        componentString += name;
        
        // add parameters
        componentString += "(";
        
        for (Parameter param : parameters)
            componentString += param.getDataType() + " " + param.getName() + ", ";
        
        if (parameters.size() > 0)
            componentString = componentString.substring(0, componentString.length() - 2); // remove trailing comma and space
        componentString+= ")";
        
        // add returnType
        componentString += " : " + returnType;
        
        return componentString;
    }
    
    
}
