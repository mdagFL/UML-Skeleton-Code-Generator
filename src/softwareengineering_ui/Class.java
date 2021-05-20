package softwareengineering_ui;

/**
 * Class representing a UML class.
 * Author: Michael D'Agostino
 * Date Created: 11/21/19
 */

import java.util.List;
import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
public class Class {
    
    // constants
    private static final double CHAR_HEIGHT = 18;
    private static final double CHAR_WIDTH = 8;
    
    // static members
    private static double scale = 1.0;
    private static int tabSpacing = 4;
      
    // instance members
    private String name;
    private boolean isAbstract;
    private List<Behavior> behaviors;
    private List<Attribute> attributes;
    private List<Relation> relations;
    // drawing
    private Point2D size;
    private Point2D position;
    

    public Class(String name, boolean isAbstract)
    {
        this.name = name;
        this.isAbstract = isAbstract;
        behaviors = new ArrayList<Behavior>();
        attributes = new ArrayList<Attribute>();
        relations = new ArrayList<Relation>();
        this.position = new Point2D(-1000, -1000);
        this.setSize();
    }
    
    public String getName()
    {
        return name;
    }
    
    public Point2D getSize()
    {
        return this.size;
    }
    
    public Point2D getPosition()
    {
        return this.position;
    }
    
    public void setPosition(Point2D position)
    {
        this.position = position;
    }
    
    public ArrayList<Relation> getRelationsList()
    {
        return (ArrayList)relations;
    }
    
    public void addBehavior(Behavior behavior)
    {
        this.behaviors.add(behavior);
        this.setSize();
    }
    
    public void addAttribute(Attribute attribute)
    {
        this.attributes.add(attribute);
        this.setSize();
    }
    
    public void addRelation(Class relatedClass, String relationType, String relationMultiplicity)
    {
        this.relations.add(new Relation(relatedClass, relationType, relationMultiplicity));
    }
   
    
    public void drawToCanvas(Canvas canvas, Point2D position)
    {
        GraphicsContext gfx = canvas.getGraphicsContext2D();
        this.position = position;
        
        
        // apply scaling
        Point2D scaledPosition = new Point2D(Class.scale * position.getX(), Class.scale * position.getY());
        Point2D scaledSize = new Point2D(Class.scale * size.getX(), Class.scale * size.getY());
        
        // set font
        gfx.setFont(Font.font("Courier New", FontWeight.LIGHT, FontPosture.REGULAR, 12));
        
        // draw rectangle
        gfx.moveTo(scaledPosition.getX(), scaledPosition.getY());
        gfx.beginPath();
        gfx.rect(scaledPosition.getX(), scaledPosition.getY(),
                scaledSize.getX(), scaledSize.getY());
        gfx.stroke();
        gfx.setFill(Paint.valueOf("#FFFFFF"));
        gfx.fill();
        gfx.closePath();
        
        // draw name of the class / abstract header
        int currentLine = 1;
        gfx.moveTo(scaledPosition.getX() + CHAR_WIDTH, scaledPosition.getY() + CHAR_HEIGHT*currentLine);
        if(isAbstract)
        {
            gfx.strokeText("<<abstract>>", scaledPosition.getX() + CHAR_WIDTH, scaledPosition.getY() + CHAR_HEIGHT*currentLine++);
        }
        gfx.strokeText(name, scaledPosition.getX() + CHAR_WIDTH, scaledPosition.getY() + CHAR_HEIGHT*currentLine++);
        
        // line separator
        gfx.moveTo(scaledPosition.getX(), scaledPosition.getY() + CHAR_HEIGHT*currentLine);
        gfx.strokeLine(scaledPosition.getX(), scaledPosition.getY() + CHAR_HEIGHT*currentLine,
            scaledPosition.getX() + scaledSize.getX(), scaledPosition.getY() + CHAR_HEIGHT*currentLine++);
        
        // attributes
        for (int i = 0; i < attributes.size(); i++)
        {
            gfx.strokeText(attributes.get(i).getUMLComponentString(), scaledPosition.getX() + CHAR_WIDTH,
                    scaledPosition.getY() + CHAR_HEIGHT*currentLine++);
        }
        
        // line separator
        gfx.moveTo(scaledPosition.getX(), scaledPosition.getY() + CHAR_HEIGHT*currentLine);
        gfx.strokeLine(scaledPosition.getX(), scaledPosition.getY() + CHAR_HEIGHT*currentLine,
            scaledPosition.getX() + scaledSize.getX(), scaledPosition.getY() + CHAR_HEIGHT*currentLine++);
        
        // behaviors
        for (int i = 0; i < behaviors.size(); i++)
        {
            gfx.strokeText(behaviors.get(i).getUMLComponentString(), scaledPosition.getX() + CHAR_WIDTH,
                    scaledPosition.getY() + CHAR_HEIGHT*currentLine++);
        }
    }
    
    // Sets the size of the class for drawing purposes
    // based on the contents it will draw
    private void setSize()
    {
        int heightInLines = 0;
        int widthInChars = 0;
        
        // set widthInChars to the maximum width component
        // set heightInLines to the total number of components 
        for (Behavior behavior : behaviors)
        {
            int componentStringLength = behavior.getUMLComponentString().length();
            if (componentStringLength > widthInChars)
                widthInChars = componentStringLength;
            heightInLines++;
        }
        for (Attribute attribute : attributes)
        {
            int componentStringLength = attribute.getUMLComponentString().length();
            if (componentStringLength > widthInChars)
                widthInChars = componentStringLength;
            heightInLines++;
        }
        // check class name for width
        if (name.length() > widthInChars)
            widthInChars = name.length();
        
        widthInChars += 2; // width padding
        heightInLines += 4; // height padding, line separators and class name
        if (isAbstract)
            heightInLines+= 1; // add 1 for abstract classes
        
        this.size = new Point2D(widthInChars * CHAR_WIDTH, heightInLines * CHAR_HEIGHT);
    }
    
    public String generateSource()
    {
        String source = "";
        String spacing = "";
        source += "public" +
            ((isAbstract)? (" abstract") : ("")) +
            " class " + this.name;
        for (int i = 0; i < relations.size(); i++)
        {
            if (relations.get(i).getRelationType().equals("inheritance"))
                source+= " extends " + relations.get(i).getRelatedToClass().getName();
        }
        source += "\n";
        source += spacing+"{" + "\n";
        
        // increase indent
        for (int i = 0; i < tabSpacing; i++)
            spacing += " ";
        
        // generate attributes
        for (int i = 0; i < attributes.size(); i++)
        {
            source+=spacing;
            // if public, add a private member, will add get and set methods
            if (attributes.get(i).getAccessSpecifier().equals("public"))
                source += "private";
            else
                source += attributes.get(i).getAccessSpecifier();
            source += " " + attributes.get(i).getDataType();
            source += " " + attributes.get(i).getName() + ";\n";
        }
        
       // add get and set methods for public members       
       for (int i = 0; i < attributes.size(); i++)
       {
          if (attributes.get(i).getAccessSpecifier().equals("public"))
          {
              // get method
              
              source += spacing+"public " + attributes.get(i).getDataType();
              
              // convert name to getName
              String camelCaseGetName = "get" + attributes.get(i).getName();
              camelCaseGetName = camelCaseGetName.replaceFirst(camelCaseGetName.substring(3, 4),
                      camelCaseGetName.substring(3,4).toUpperCase());
              
              source += " " + camelCaseGetName + "()\n";
              source += spacing+"{\n"+spacing+"\n"+spacing+"}\n";
              
              // set method
              String camelCaseSetName = camelCaseGetName.replaceFirst("g", "s");
              
              source += spacing+"public void";
              source += " " + camelCaseSetName + "(";
              source += attributes.get(i).getDataType();
              source += " " +attributes.get(i).getName() + ")\n";
              source += spacing+"{\n";
              
              // increase indent
              for (int j = 0; j < tabSpacing; j++)
                  spacing += " ";       
              
              // assignment statement
              source+= spacing+"this."+attributes.get(i).getName();
              source+=" = "+attributes.get(i).getName() + ";\n";
              
              // decrease indent
              for (int j = 0; j < tabSpacing; j++)
                  spacing = spacing.substring(0, spacing.length() - 1);
              source+=spacing+"}\n";
          }
       }
       
       source += "\n";
       
       // add behaviors
       for (int i = 0; i < behaviors.size(); i++)
       {
           source += spacing+behaviors.get(i).getAccessSpecifier();
           source += " " + behaviors.get(i).getReturnType();
           source += " " + behaviors.get(i).getName();
           source += "(";
           for (Parameter param : behaviors.get(i).getParameters())
           {
               source += param.getDataType();
               source += " " + param.getName();
               source += ", ";
           }
           // if there were parameters, remove comma and space
           if (behaviors.get(i).getParameters().size() > 0)
               source = source.substring(0, source.length()-2);
           
           source+=")\n";
           source+=spacing+"{\n"+spacing+"\n"+spacing+"}\n";
       }
        
        // decrease indent
        for (int i = 0; i < tabSpacing; i++)
            spacing = spacing.substring(0, spacing.length() - 1);
        source+= spacing+"}";
        
        
        return source;
    }
}
