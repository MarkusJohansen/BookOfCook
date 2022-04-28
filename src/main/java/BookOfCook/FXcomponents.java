package BookOfCook;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.scene.layout.GridPane;

abstract class FXcomponents extends Validator{

    //*STYLING
    protected void styleNode(Node node, String styleClass, Double x, Double y){ //! BURDE IKKE ALLE DISSE VÆRE PROTECTED? SIDEN DETTE ER EN SUPERKLASSE?
        node.getStyleClass().clear();
        node.getStyleClass().add(styleClass);
        node.setLayoutX(x);
        node.setLayoutY(y);
    }

    protected void styleRegion(Region region, String styleClass, Double width, Double height){ //! BURDE IKKE ALLE DISSE VÆRE PROTECTED?
        region.getStyleClass().clear();
        region.getStyleClass().add(styleClass);
        region.setMaxWidth(width);
        region.setMaxWidth(height);
    }

    //*JAVAFX COMPONENTS
    protected void viewLabel(String content, Object parent, int row, int column){//shorthand method for creating labels in recipe viewmode //! BURDE IKKE ALLE DISSE VÆRE PROTECTED?
        Label label = new Label(content);
        styleNode(label, "recipe-view-text", 80.0, 10.0);
        ((GridPane)parent).add(label, column, row);
    }
    
    protected void viewList(int labelX, int labelY, int listX, int listY, String label, List<String> array, GridPane grid){//shorthand method for creating list and fill them with ingredients in recipe viewmode.  https://stackoverflow.com/questions/4581407/how-can-i-convert-arraylistobject-to-arrayliststring
        ListView<String> listView = new ListView<String>();
        viewLabel(label, grid, labelX, labelY);//add steps label to grid 
        for(String o : array){//shorthand method for)
            listView.getItems().add(o.toString());
        }
        listView.getStyleClass().add("recipe-view-list");
        grid.add(listView, listY, listX);
    }
    
    protected Button xButton(){   
        Button btn = new Button("X");
        btn.getStyleClass().clear();
        styleNode(btn, "standard-button", 10.0, 0.0);
        return btn;
    }
    
    protected Label listLabel(String content){
        Label label = new Label(content);
        styleNode(label, "list-label", 80.0, 10.0); 
        return label;
    }
    
    protected FileChooser csvFileChooser(String title){
        FileChooser fileChooser = new FileChooser();                //creates a filechooser
        fileChooser.setTitle(title);                      //sets the title of the filechooser window
        fileChooser.getExtensionFilters().addAll(                   //adds the extension filter for csv files to the filechooser, such that the user can only save the cookbook as a csv file
            new FileChooser.ExtensionFilter("CSV Files", "*.csv")   //the extension filter is set to csv files
        );  
        return fileChooser;     
    }
}
