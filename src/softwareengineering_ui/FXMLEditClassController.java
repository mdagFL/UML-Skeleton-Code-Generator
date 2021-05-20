package softwareengineering_ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class FXMLEditClassController implements Initializable {
@FXML
    private GridPane addGrid;

    @FXML
    private TextField addClassName;

    @FXML
    private GridPane addAttributeNameGrid;

    @FXML
    private ColumnConstraints addAttributeLabelCol;

    @FXML
    private ColumnConstraints addAttributeNameCol;

    @FXML
    private ColumnConstraints addAttributeTypeCol;

    @FXML
    private TextField addAttributeName;

    @FXML
    private TextField addAttributeType;

    @FXML
    private Button addAttributeButton;

    @FXML
    private GridPane addMethodGrid;

    @FXML
    private TextField addMethodName;

    @FXML
    private TextField addMethodParameter;

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

    
    int addAttCount = 0;
    
    //Declare TextField Arrays for Attribute
    TextField[] attributeName = new TextField[20];
    TextField[] attributeType = new TextField[20];
    
    
    
    @FXML
    void addAttributeButtonAction(ActionEvent event) {
        addAttributeRemoveButton.setVisible(true);
        
        TextField tName = new TextField();
        TextField tType = new TextField();
        
        tName.setPromptText("<Attribute Name>");
        tType.setPromptText("<Attribute Type>");
        
        //Add New TextField to array
        attributeName[addAttCount + 1] = tName;
        attributeType[addAttCount + 1] = tType;
        
        //Add New Attribute below
        addAttCount++;
        addAttributeNameGrid.add(tName, 0, addAttCount);
        addAttributeNameGrid.add(tType, 1, addAttCount);
        
         
    }

    @FXML
    void addAttributeRemoveButtonAction(ActionEvent event) {
        if(addAttCount != 0)
        {
            attributeName[addAttCount] = null;
            attributeType[addAttCount] = null;
            
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
        stage.close();
    }
    
    
    TextField[] methodName = new TextField[20];
    TextField[] methodParam = new TextField[20];
    
    int addMethCount = 0;
    @FXML
    void addMethodButtonAction(ActionEvent event) {
        addMethodRemoveButton.setVisible(true);
        
        TextField methName = new TextField();
        TextField methParam = new TextField();
        
        methName.setPromptText("<Method Name>");
        methParam.setPromptText("<Method Parameter>");
        
        //Add New TextField to array
        methodName[addMethCount + 1] = methName;
        methodParam[addMethCount + 1] = methParam;
        
        //Add New Method below
        addMethCount++;
        addMethodGrid.add(methName, 0, addMethCount);
        addMethodGrid.add(methParam, 1, addMethCount);
    }

    @FXML
    void addMethodRemoveButtonAction(ActionEvent event) {
        if(addMethCount != 0)
        {
            methodName[addMethCount] = null;
            methodParam[addMethCount] = null;
            
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
    
    @FXML
    void addRelationshipButtonAction(ActionEvent event) {
        addRelationshipRemoveButton.setVisible(true);
        
        TextField rClass = new TextField();
        MenuButton rName;
        
        MenuItem menuItem1 = new MenuItem("Inheritance"); 
        MenuItem menuItem2 = new MenuItem("Aggregation");
        MenuItem menuItem3 = new MenuItem("Composition");

        
        rName = new MenuButton("Select Relationship", null, menuItem1, menuItem2, menuItem3);
        
        rName.setText("Select Relationship");
        rClass.setPromptText("<Class Name>");
        
        menuItem1.setOnAction((ActionEvent event1) -> {
            rName.setText("Inheritance");
        });
        
        menuItem2.setOnAction((ActionEvent event1) -> {
            rName.setText("Aggregation");
        });
        
        menuItem3.setOnAction((ActionEvent event1) -> {
            rName.setText("Composition");
        });

        
        //Add New TextField to array
        relationshipName[addRelCount + 1] = rName;
        relationshipClass[addRelCount + 1] = rClass;
        
        //Add New Relationship below
        addRelCount++;
        addRelationshipGrid.add(rName, 0, addRelCount);
        addRelationshipGrid.add(rClass, 1, addRelCount);
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
        attributeType[0] = addAttributeName;
        
        //Adding first method textboxes to arrays
        methodName[0] = addMethodName;
        methodParam[0] = addMethodParameter;
        
        relationshipName[0] = addRelationshipMenu;
        relationshipClass[0] = addRelationshipClass;
        
        addRelationshipRemoveButton.setVisible(false);
        addMethodRemoveButton.setVisible(false);
        addAttributeRemoveButton.setVisible(false);
    }    
    
}