package softwareengineering_ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;

/**
 * FXML Controller class
 *
 * @author cvasi
 */
public class FXMLAddClassController implements Initializable {


    
    @FXML
    private GridPane addGrid;

    @FXML
    private TextField addClassName;

    @FXML
    private GridPane addAttributeNameGrid;

    @FXML
    private ColumnConstraints addAttributeNameCol;

    @FXML
    private ColumnConstraints addAttributeTypeCol;

    @FXML
    private TextField addAttributeName;

    @FXML
    private TextField addAttributeType;

    @FXML
    private TextField addAttributeAccessSpec;

    @FXML
    private Button addAttributeButton;

    @FXML
    private GridPane addMethodGrid;

    @FXML
    private TextField addMethodName;

    @FXML
    private TextField addMethodParameter;

    @FXML
    private TextField addMethodAccessSpec;

    @FXML
    private TextField addMethodReturnType;

    @FXML
    private Button addMethodButton;

    @FXML
    private Button addRelationshipButton;

    @FXML
    private GridPane addRelationshipGrid;

    @FXML
    private MenuButton addRelationshipMenu;

    @FXML
    private MenuItem relationshipInheritance;

    @FXML
    private MenuItem relationshipAggregation;

    @FXML
    private MenuItem relationshipComposition;

    @FXML
    private TextField addRelationshipClass;

    @FXML
    private MenuButton addRelationshipMult;

    @FXML
    private MenuItem relationship11;

    @FXML
    private MenuItem relationship1Many;

    @FXML
    private MenuItem relationshipManyMany;

    @FXML
    private Button addAttributeRemoveButton;

    @FXML
    private Button addMethodRemoveButton;

    @FXML
    private Button addRelationshipRemoveButton;

    @FXML
    private Button addConfirm;

    @FXML
    private Button addCancel;

    @FXML
    private CheckBox addAbstractYes;

    @FXML
    private CheckBox addAbstractNo;

    @FXML
    void addAbstractNoAction(ActionEvent event) {
        addAbstractYes.setSelected(false);
    }

    @FXML
    void addAbstractYesAction(ActionEvent event) {
        addAbstractNo.setSelected(false);
    }
    
    @FXML
    void addAttributeAccessSpecAction(ActionEvent event) {

    }

    
    int addAttCount = 0;
    
    //Declare TextField Arrays for Attribute
    TextField[] attributeName = new TextField[20];
    TextField[] attributeType = new TextField[20];
    TextField[] attributeSpec = new TextField[20];
    
    
    
    @FXML
    void addAttributeButtonAction(ActionEvent event) {
        addAttributeRemoveButton.setVisible(true);
        
        TextField tName = new TextField();
        TextField tType = new TextField();
        TextField tSpec = new TextField();
        
        tName.setPromptText("<Attribute Name>");
        tType.setPromptText("<Attribute Type>");
        tSpec.setPromptText("<Access Specifiers>");
        
        //Add New TextField to array
        attributeName[addAttCount + 1] = tName;
        attributeType[addAttCount + 1] = tType;
        attributeSpec[addAttCount + 1] = tSpec;
        
        //Add New Attribute below
        addAttCount++;
        addAttributeNameGrid.add(tName, 0, addAttCount);
        addAttributeNameGrid.add(tType, 1, addAttCount);
        addAttributeNameGrid.add(tSpec, 2, addAttCount);
        
         
    }

    @FXML
    void addAttributeRemoveButtonAction(ActionEvent event) {
        if(addAttCount != 0)
        {
            attributeName[addAttCount] = null;
            attributeType[addAttCount] = null;
            attributeSpec[addAttCount] = null;
            
           addAttributeNameGrid.getChildren().remove(addAttributeNameGrid.getChildren().size() -1);
           addAttributeNameGrid.getChildren().remove(addAttributeNameGrid.getChildren().size() -1);
           addAttributeNameGrid.getChildren().remove(addAttributeNameGrid.getChildren().size() -1);
                    
                  
        }
        
        addAttCount--;
        
        if(addAttCount == 0)
            addAttributeRemoveButton.setVisible(false);
        
        
        
    }

    @FXML
    void addCancelAction(ActionEvent event) {
        
        //Closes Add Class
        Stage stage = (Stage) addConfirm.getScene().getWindow();
        stage.close();
    }

    @FXML
    void addConfirmAction(ActionEvent event) {
        
        //Closes Add Class
        Stage stage = (Stage) addConfirm.getScene().getWindow();
        
        // Assign input values to class object
        
       Class classToInput = new Class(addClassName.getText(), addAbstractYes.selectedProperty().getValue());
       
       // add attributes
       for (int i = 0; i < attributeName.length && attributeName[i] != null; i++)
       {
           // if the name, specifier, and type all contain text
           if ( !(attributeName[i].getText().equals("") || attributeType[i].getText().equals("") || attributeSpec[i].getText().equals("")) )
           {
               // add the attribute
               classToInput.addAttribute(new Attribute(attributeName[i].getText(), attributeSpec[i].getText(), attributeType[i].getText()));
           }
       }
       
       // add behaviors
       for (int i = 0; i < methodName.length && methodName[i] != null; i++)           
       {
           // if the name and specifier both contain text
           if ( !(methodName[i].getText().equals("") || methodSpec[i].getText().equals("")) )
           {
               // if returnType is blank, default to void
               String returnType = methodReturn[i].getText();
               if (returnType.equals(""))
                   returnType = "void";

               // store behavior in local variable, may add parameters
               Behavior behavior = new Behavior(methodName[i].getText(), methodSpec[i].getText(), returnType);

               // parse parameter field
               // store in local variable for manipulation
               String param = methodParam[i].getText();
               
               // add comma and space for simpler parsing logic
               if ( !param.endsWith(","))
                   param += ", ";
               else
                   param += " ";
               while (param.length() > 2)
               {
                   String paramDataType;
                   String paramName;
                   
                   // extract paramDataType and paramName from input
                   // expecting parameters to be comma-delimited
                   // and in the format "paramDataType paramName"
                   paramDataType = param.substring(0, param.indexOf(" "));
                   paramName = param.substring(param.indexOf(" ") + 1, param.indexOf(","));
                   param = param.substring(param.indexOf(",") + 2);
                   behavior.addParameter(paramName, paramDataType);
               }
               
               classToInput.addBehavior(behavior);           
           }
       }
       
       // add relations
       for (int i = 0; i < relationshipName.length && relationshipName[i] != null; i++)
       {
           // find related class by name
           Class checkClass;
           for (int j = 0; j < FXMLDocumentController.getClassesList().size(); j++)
           {
               checkClass = FXMLDocumentController.getClassesList().get(j);
               
               // if the class name matches, add the relationship
               if (checkClass.getName().equals(relationshipClass[i].getText()))
                   classToInput.addRelation(checkClass, relationshipName[i].getText().toLowerCase(), relationshipMult[i].getText());
           }
           
       }
       
       // add the class
       FXMLDocumentController.addClass(classToInput);
       
       // Close the window
       stage.close();
        
        
    }
    
    
    TextField[] methodName = new TextField[20];
    TextField[] methodParam = new TextField[20];
    TextField[] methodSpec = new TextField[20];
    TextField[] methodReturn = new TextField[20];
    
    int addMethCount = 0;
    @FXML
    void addMethodButtonAction(ActionEvent event) {
        addMethodRemoveButton.setVisible(true);
        
        TextField methName = new TextField();
        TextField methParam = new TextField();
        TextField methSpec = new TextField();
        TextField methReturn = new TextField();
        
        methName.setPromptText("<Method Name>");
        methParam.setPromptText("<Method Parameter>");
        methSpec.setPromptText("<Access Specifiers>");
        methReturn.setPromptText("<Return Type>");
        
        //Add New TextField to array
        methodName[addMethCount + 1] = methName;
        methodParam[addMethCount + 1] = methParam;
        methodSpec[addMethCount + 1] = methSpec;
        methodReturn[addMethCount + 1] = methReturn;
        
        //Add New Method below
        addMethCount++;
        addMethodGrid.add(methName, 0, addMethCount);
        addMethodGrid.add(methParam, 1, addMethCount);
        addMethodGrid.add(methSpec, 2, addMethCount);
        addMethodGrid.add(methReturn, 3, addMethCount);
    }

    @FXML
    void addMethodRemoveButtonAction(ActionEvent event) {
        if(addMethCount != 0)
        {
            methodName[addMethCount] = null;
            methodParam[addMethCount] = null;
            methodSpec[addMethCount] = null;
            methodReturn[addMethCount] = null;
            
           addMethodGrid.getChildren().remove(addMethodGrid.getChildren().size() -1);
           addMethodGrid.getChildren().remove(addMethodGrid.getChildren().size() -1);
           addMethodGrid.getChildren().remove(addMethodGrid.getChildren().size() -1);
           addMethodGrid.getChildren().remove(addMethodGrid.getChildren().size() -1);
                    
                  
        }
        
        addMethCount--;
        
        if(addMethCount == 0)
            addMethodRemoveButton.setVisible(false);

    }
    
    int addRelCount = 0;
    MenuButton[] relationshipName = new MenuButton[20];
    TextField[] relationshipClass = new TextField[20];
    MenuButton[] relationshipMult = new MenuButton[20];
    
    @FXML
    void addRelationshipButtonAction(ActionEvent event) {
        addRelationshipRemoveButton.setVisible(true);
        
        TextField rClass = new TextField();
        MenuButton rName;
        MenuButton rMult;
        
        MenuItem menuItem1 = new MenuItem("Inheritance"); 
        MenuItem menuItem2 = new MenuItem("Aggregation");
        MenuItem menuItem3 = new MenuItem("Composition");
        
        MenuItem menuItemMult1 = new MenuItem("1 .. 1");
        MenuItem menuItemMult2 = new MenuItem("1 .. *");
        MenuItem menuItemMult3 = new MenuItem("* .. *");

        
        rName = new MenuButton("Select Relationship", null, menuItem1, menuItem2, menuItem3);
        rMult = new MenuButton("Multiplicity", null, menuItemMult1, menuItemMult2, menuItemMult3);
        
        rName.setText("Select Relationship");
        rClass.setPromptText("<Class Name>");
        rMult.setText("Multiplicity");
        
        menuItem1.setOnAction((ActionEvent event1) -> {
            rName.setText("Inheritance");
        });
        
        menuItem2.setOnAction((ActionEvent event1) -> {
            rName.setText("Aggregation");
        });
        
        menuItem3.setOnAction((ActionEvent event1) -> {
            rName.setText("Composition");
        });
        
        menuItemMult1.setOnAction((ActionEvent event1) -> {
            rMult.setText("1 .. 1");
        });
        
        menuItemMult2.setOnAction((ActionEvent event1) -> {
            rMult.setText("1 .. *");
        });
        
        menuItemMult3.setOnAction((ActionEvent event1) -> {
            rMult.setText("* .. *");
        });
        
        //Add New TextField to array
        relationshipName[addRelCount + 1] = rName;
        relationshipClass[addRelCount + 1] = rClass;
        relationshipMult[addRelCount + 1] = rMult;
        
        //Add New Relationship below
        addRelCount++;
        addRelationshipGrid.add(rName, 0, addRelCount);
        addRelationshipGrid.add(rClass, 1, addRelCount);
        addRelationshipGrid.add(rMult, 2, addRelCount);
    }

    @FXML
    void addRelationshipMenuAction(ActionEvent event) {
        
    }

    @FXML
    void addRelationshipRemoveButtonAction(ActionEvent event) {
        if(addRelCount != 0)
        {
            relationshipName[addRelCount] = null;
            relationshipClass[addRelCount] = null;
            relationshipMult[addRelCount] = null;
            
           addRelationshipGrid.getChildren().remove(addRelationshipGrid.getChildren().size() -1);
           addRelationshipGrid.getChildren().remove(addRelationshipGrid.getChildren().size() -1);
           addRelationshipGrid.getChildren().remove(addRelationshipGrid.getChildren().size() -1);
                    
                  
        }
        
        addRelCount--;
        
        if(addRelCount == 0)
            addRelationshipRemoveButton.setVisible(false);
    }
    
    @FXML
    void relationshipAggregationAction(ActionEvent event) {
        addRelationshipMenu.setText(relationshipAggregation.getText());
    }

    @FXML
    void relationshipCompositionAction(ActionEvent event) {
        addRelationshipMenu.setText(relationshipComposition.getText());
    }

    @FXML
    void relationshipInheritanceAction(ActionEvent event) {
        addRelationshipMenu.setText(relationshipInheritance.getText());
    }
    
    @FXML
    void addRelationshipMultAction(ActionEvent event) {
        
    }
    
     @FXML
    void relationship11Action(ActionEvent event) {
        addRelationshipMult.setText(relationship11.getText());
    }

    @FXML
    void relationship1ManyAction(ActionEvent event) {
        addRelationshipMult.setText(relationship1Many.getText());
    }
    
    @FXML
    void relationshipManyManyAction(ActionEvent event) {
        addRelationshipMult.setText(relationshipManyMany.getText());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Set Gaps of main grid
        addGrid.setHgap(10);
        addGrid.setVgap(10);
        
        //Set Gaps of Attribute portion
        addAttributeNameGrid.setHgap(10);
        addAttributeNameGrid.setVgap(10);
        
        addMethodGrid.setHgap(10);
        addMethodGrid.setVgap(10);
        
        addRelationshipGrid.setHgap(10);
        addRelationshipGrid.setVgap(10);
        
        //Adding first attribute textboxes to arrays
        attributeName[0] = addAttributeName;
        attributeType[0] = addAttributeType;
        attributeSpec[0] = addAttributeAccessSpec;
        
        //Adding first method textboxes to arrays
        methodName[0] = addMethodName;
        methodParam[0] = addMethodParameter;
        methodSpec[0] = addMethodAccessSpec;
        methodReturn[0] = addMethodReturnType;
        
        relationshipName[0] = addRelationshipMenu;
        relationshipClass[0] = addRelationshipClass;
        relationshipMult[0] = addRelationshipMult;
        
        addRelationshipRemoveButton.setVisible(false);
        addMethodRemoveButton.setVisible(false);
        addAttributeRemoveButton.setVisible(false);
    }    
    
    
   
    
}
