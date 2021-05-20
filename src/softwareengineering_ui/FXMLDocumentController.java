
package softwareengineering_ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.canvas.GraphicsContext;
import java.util.Formatter;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author cvasi
 */
public class FXMLDocumentController implements Initializable {

    
    // List of current classes
    private static List<Class> classes = new ArrayList<Class>();
    // reference to class being moved by the mouse
    private static Class draggedClass = null;
    public static void addClass(Class cls)
    {
        classes.add(cls);
    }
    public static void removeClass(String className)
    {
        for (int i = 0; i < classes.size(); i++)
            if (classes.get(i).getName().equals(className))
            {
                classes.remove(i);
                return;
            }
    }
    public static ArrayList<Class> getClassesList()
    {
        return (ArrayList)classes;
    }
    
    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu menuDiagram;

    @FXML
    private MenuItem menuItemNewDiagram;

    @FXML
    private MenuItem menuItemLoadDiagram;

    @FXML
    private Menu menuClass;

    @FXML
    private MenuItem menuItemAddClass;

    @FXML
    private MenuItem menuItemEditClass;

    @FXML
    private Menu menuOutput;

    @FXML
    private MenuItem menuItemGenCode;

    @FXML
    private MenuItem menuItemGenDiagram;
    
    @FXML
    private Canvas mainCanvas;


    @FXML
    void menuActionAddClass(ActionEvent event) throws IOException {
        //Load the AddClass FXML Document
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLAddClass.fxml"));
        Parent loadFXML = (Parent) fxmlLoader.load();
        
        //Display the FXML Document in new Window
        Stage stage = new Stage();
        stage.setScene(new Scene(loadFXML));  
        stage.setTitle("Add Class");
        stage.show();
    }

    @FXML
    void menuActionEditClass(ActionEvent event) throws IOException  {
        //Load the AddClass FXML Document
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLEditClass.fxml"));
        Parent loadFXML = (Parent) fxmlLoader.load();
        
        //Display the FMXL Document in new Window
        Stage stage = new Stage();
        stage.setScene(new Scene(loadFXML));  
        stage.setTitle("Edit Class");
        stage.show();
    }

    @FXML
    void menuActionGenCode(ActionEvent event) {
        Formatter formatter; // formatter for outputing files
        try
        {
            Files.createDirectory(Paths.get("GeneratedSourceCode"));
        }
        catch (Exception e)
        {

        }
        
        // generate source code for each class, output to file
        for (int i = 0; i < classes.size(); i++)
        {
            try
            {
                File outFile = new File("GeneratedSourceCode/" +classes.get(i).getName() + ".java");
                formatter = new Formatter(new FileOutputStream(outFile));
                formatter.format(classes.get(i).generateSource());
                formatter.close();
            }
            catch (Exception e)
            {
                
            }
        }
    }

    @FXML
    void menuActionLoadDiagram(ActionEvent event) {

    }

    @FXML
    void menuActionNewDiagram(ActionEvent event) {

    }

    @FXML
    void menuIActionGenDiagram(ActionEvent event) {

    }
    
    @FXML
    void refreshDiagram(ActionEvent event) {
        refreshDiagram();
    }
    
    
    @FXML
    void canvasMouseClick(MouseEvent event) {
    }
    
    @FXML
    void canvasMouseDown(MouseEvent event)
    {
        double x = event.getX();
        double y = event.getY();
        
        // check if mouse is over a class in the diagram
        for (int i = 0; i < classes.size(); i++)
        {
            if (x > classes.get(i).getPosition().getX() &&
                    x < classes.get(i).getPosition().getX() + classes.get(i).getSize().getX() &&
                    y > classes.get(i).getPosition().getY() &&
                    y < classes.get(i).getPosition().getY() + classes.get(i).getSize().getY())
            {
                draggedClass = classes.get(i);
                draggedClass.setPosition(new Point2D(x, y));
                break;
            }
                
        }
        
    }
    @FXML
    void canvasMouseDrag(MouseEvent event) {
    }

    @FXML
    void canvasMouseMove(MouseEvent event) {
    }
    
    @FXML
    void canvasMouseRelease(MouseEvent event) {
        draggedClass = null;
    }
    
    @FXML
    void canvasMouseDragged(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        
        // if a class is being dragged
        if (draggedClass != null)
        {
            // check that mouse is within bounds of canvas
            if (x > 5 && x < mainCanvas.widthProperty().getValue() - 5 &&
                    y > 5 && y < mainCanvas.heightProperty().getValue() - 5)
                draggedClass.setPosition(new Point2D(x, y));
            
            // redraw
            this.refreshDiagram();
        }
        
    }
    
    void refreshDiagram()
    {
        // get graphics context
        GraphicsContext gfx = mainCanvas.getGraphicsContext2D();
        
        // clear the canvas
        gfx.setFill(Paint.valueOf("#F0F0F0"));
        gfx.clearRect(0, 0, mainCanvas.widthProperty().doubleValue(),
                mainCanvas.heightProperty().doubleValue());
        
        // use nextPosition variable for initially placing the classes
        Point2D nextPosition = new Point2D(20,20);
        for (int i = 0; i < classes.size(); i++)
        {
            // if the class's position is the default value, use nextPosition
            if (classes.get(i).getPosition().getX() == -1000 && classes.get(i).getPosition().getY() == -1000)
            {
                classes.get(i).drawToCanvas(mainCanvas, nextPosition);
                nextPosition = new Point2D(nextPosition.getX() + classes.get(i).getSize().getX() + 40,
                    nextPosition.getY());
                if (nextPosition.getX() > 800)
                    nextPosition = new Point2D(20, nextPosition.getY() + 50);
            }
            else // otherwise use its position
            {
                classes.get(i).drawToCanvas(mainCanvas, classes.get(i).getPosition());
            }
        }
        
        // draw relations
        for (int i = 0; i < classes.size(); i++)
        {
            for (int j = 0; j < classes.get(i).getRelationsList().size(); j++)
            {
                // store values in local variables for readability
                String relationType = classes.get(i).getRelationsList().get(j).getRelationType();
                Class relatedClass = classes.get(i).getRelationsList().get(j).getRelatedToClass();
                
                // draw form the top of the class, divide space on the horizontal for each relation
                Point2D drawFrom = new Point2D(classes.get(i).getPosition().getX() +
                        ((classes.get(i).getSize().getX()/(classes.get(i).getRelationsList().size() + 1)) * (j + 1)),
                        classes.get(i).getPosition().getY());
                
                // draw to the bottom center of each class
                Point2D drawTo = new Point2D(relatedClass.getPosition().getX() + (relatedClass.getSize().getX() / 2.0),
                        relatedClass.getPosition().getY() + relatedClass.getSize().getY());
                
                // set stroke color to black
                gfx.setStroke(Paint.valueOf("#000000"));
                
                // pick relation type to draw
                if (relationType.equals("inheritance"))
                {
                    // line to just below the center of the class
                    gfx.moveTo(drawFrom.getX(), drawFrom.getY());
                    gfx.lineTo(drawTo.getX(), drawTo.getY() + 20);
                    gfx.stroke();
                    
                    // draw triangle for inheritance
                    gfx.beginPath();
                    gfx.moveTo(drawTo.getX() - 10, drawTo.getY() + 20);
                    gfx.lineTo(drawTo.getX() + 10, drawTo.getY() + 20);
                    gfx.lineTo(drawTo.getX(), drawTo.getY());
                    gfx.lineTo(drawTo.getX() - 10, drawTo.getY() + 20);
                    gfx.stroke();
                    gfx.closePath();
                }
                else // for aggregation and composition, draw same strokes
                {      
                    // draw diamond shape
                    gfx.beginPath();
                    gfx.moveTo(drawFrom.getX(), drawFrom.getY() - 30);
                    gfx.lineTo(drawFrom.getX() - 10, drawFrom.getY() - 15);
                    gfx.lineTo(drawFrom.getX(), drawFrom.getY());
                    gfx.lineTo(drawFrom.getX() + 10, drawFrom.getY() - 15);
                    gfx.lineTo(drawFrom.getX(), drawFrom.getY() - 30);
                    gfx.stroke();
                    
                    // apply a black fill for composition
                    if (relationType.equals("composition"))
                    {
                        gfx.setFill(Paint.valueOf("#000000"));
                        gfx.fill();
                    }
                    
                    // draw line to the related class
                    gfx.closePath();
                    gfx.moveTo(drawFrom.getX(), drawFrom.getY() - 30);
                    gfx.lineTo(drawTo.getX(), drawTo.getY());
                    gfx.stroke();
                }
                
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
