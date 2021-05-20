package softwareengineering_ui;

/**
 * Represents an attribute of a class.
 * Author: Michael D'Agostino
 * Date created: 11/21/19
 */
public class Attribute {
    private String name;
    private String accessSpecifier;
    private String dataType;
   
    public Attribute(String name, String accessSpecifier, String dataType)
    {
        this.name = name;
        this.accessSpecifier = accessSpecifier;
        this.dataType = dataType;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getAccessSpecifier()
    {
        return accessSpecifier;
    }
    
    public String getDataType()
    {
        return dataType;
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
        
        // add name and data type
        componentString += name + " : " + dataType;
        
        return componentString;
    }
}
