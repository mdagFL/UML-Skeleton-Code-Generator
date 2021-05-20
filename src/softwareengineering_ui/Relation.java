
package softwareengineering_ui;

/**
 * Associative class describing a relationship between classes.
 * Author: Michael D'Agostino
 * Date Created: 11/22/19
 */

public class Relation {
    private String relationType;
    private Class relatedToClass;
    private String relationMultiplicity;
    
    public Relation(Class relatedToClass, String relationType, String relationMultiplicity)
    {
        this.relationType = relationType;
        this.relatedToClass = relatedToClass;
        this.relationMultiplicity = relationMultiplicity;
    }
    
    public String getRelationMultiplicity()
    {
        return this.relationMultiplicity;
    }
    
    public String getRelationType()
    {
        return relationType;
    }
    
    public Class getRelatedToClass()
    {
        return relatedToClass;
    }
    
}
