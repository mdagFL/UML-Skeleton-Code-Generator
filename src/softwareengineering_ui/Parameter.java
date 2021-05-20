
package softwareengineering_ui;

/**
 * Simple class representing a parameter of a function
 * Author: Michael D'Agostino
 * Date created: 11/21/19
 */

public class Parameter {
    private String name;
    private String dataType;
    
    public Parameter(String name, String dataType)
    {
        this.name = name;
        this.dataType = dataType;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public String getDataType()
    {
        return this.dataType;
    }
}


