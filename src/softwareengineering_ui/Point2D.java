
package softwareengineering_ui;

/**
 * Simple data structure for representing a 2D point.
 * Author: Michael D'Agostino
 * Date Created: 11/21/19
 */
public class Point2D {
    private double x;
    private double y;
    
    public Point2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public double getX()
    {
        return x;
    }
    
    public void setX(double x)
    {
        this.x = x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public void setY(double y)
    {
        this.y = y;
    }
}
